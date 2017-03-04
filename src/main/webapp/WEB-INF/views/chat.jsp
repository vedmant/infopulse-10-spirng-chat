<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@include file="header.jsp" %>

<div class="container" ng-app="webChat" ng-controller="webChatController" id="web_chat">

    <h2>Chat</h2>

    <div class="row">
        <div class="col-sm-8">

            <div class="panel panel-primary">
                <div class="panel-heading">Chat</div>
                <div class="panel-body chat-body">
                    <ul>
                        <li ng-repeat="message in messages">{{ message.name }}: {{ message.message }}</li>
                    </ul>
                    <div ng-if="! messages.length">No messages</div>
                </div>
            </div>

        </div>
        <div class="col-sm-4">

            <div class="panel panel-primary users-panel">
                <div class="panel-heading">Users</div>
                <div class="panel-body">
                    <ul>
                        <li ng-repeat="user in users" ng-click="selectUser(user)">{{ user }}</li>
                    </ul>
                    <div ng-if="! users.length">No users</div>
                </div>
            </div>

        </div>
    </div>

    <form class="send-form">
        <div class="form-group">
            <div class="input-group">
                <input type="text" class="form-control" id="form_message" ng-model="message">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" ng-click="sendMessage()">Send</button>
                </span>
            </div>
        </div>
    </form>

</div>

<script>
    var App = {};
    App.sockPath = '/sock'; //'${sessionScope.sockPath}';
</script>

<script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script>
<script src="/static/chat.js"></script>

<%@include file="footer.jsp" %>