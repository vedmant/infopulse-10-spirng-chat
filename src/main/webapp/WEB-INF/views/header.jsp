<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Chat</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <link rel="stylesheet" href="/static/style.css">
</head>
<body style="padding-top: 70px;">

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Spring Chat</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/">Home</a></li>
                <li><a href="${s:mvcUrl('getChat').build()}">Chat</a></li>
                <c:if test="${sessionScope.user == null}">
                    <li><a href="${s:mvcUrl('getLogin').build()}">Login</a></li>
                    <li><a href="${s:mvcUrl('getRegister').build()}">Register</a></li>
                </c:if>
                <c:if test="${sessionScope.user != null}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Hello ${sessionScope.user.login} <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="${s:mvcUrl('getLogout').build()}">Logout</a></li>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<c:if test="${requestScope.error != null}">
<div class="container">
    <p class="alert alert-danger">${requestScope.error}</p>
</div>
</c:if>

<c:if test="${requestScope.success != null}">
<div class="container">
    <p class="alert alert-success">${requestScope.success}</p>
</div>
</c:if>
