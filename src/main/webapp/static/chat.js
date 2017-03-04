/**
 * WebSockets
 */

var socket = new SockJS(App.sockPath);

var state = {
    listInterval: null,
};

socket.onopen = function () {
    console.log('Opened');
    registerUser();
    state.listInterval = setInterval(sendList, 2000);
    sendList();
};

socket.onclose = function (e) {
    clearInterval(state.listInterval);
    if (e.wasClean) {
        console.log('Closed clean')
    } else {
        console.error('Closed with error')
    }
};

socket.onerror = function (e) {
    console.log(e);
};

function sendList () {
    socket.send(JSON.stringify({list: true}));
}

function registerUser () {
    socket.send(JSON.stringify({sessionId: getCookie('JSESSIONID')}));
}

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

/**
 * Angular controller
 */
var app = angular.module('webChat', []);

app.controller('webChatController', function ($scope) {

    $scope.users = [];
    $scope.messsages = [];
    $scope.message = '';

    // document.body.addEventListener('users-list', function (e) {
    //     console.log('users list', e)
    //     $scope.users = e.detail;
    // }, false);
    //
    // document.body.addEventListener('new-message', function (e) {
    //     $scope.messages.push(e.detail);
    // }, false);

    // $scope.applyUsers = function (users) {
    //     $scope.users = users;
    // };

    $scope.selectUser = function (user) {
        $scope.message = user + ': ';
    };

    $scope.sendMessage = function () {
        console.log('sendMessage');
        var arrayMesssage = $scope.message.split(':');
        var message = {};
        message[arrayMesssage[0]] = arrayMesssage[1];
        socket.send(JSON.stringify(message));
        $scope.message = '';
    };

    socket.onmessage = function (e) {
        try {
            var message = JSON.parse(e.data);
        } catch (e) {
            console.error('JSON parse error', e.data);
            return;
        }

        console.log(message);

        if (typeof message.list !== 'undefined') {
            $scope.users = message.list;
            $scope.$apply();
            return;
        }

        if (typeof message.auth !== 'undefined' && message.auth === 'no') {
            console.log('Authorization failed');
            return;
        }

        if (typeof message.auth !== 'undefined' && message.auth === 'yes') {
            console.log('Authorization successful');
            return;
        }

        if (typeof message.name !== 'undefined') {
            $scope.messages.push(message);
        }
    };

});
