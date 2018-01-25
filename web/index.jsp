<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>MovieRating</title>
</head>
<body>
<div id="page-body">
    <header>
        <nav id="header-nav" class="navbar navbar-expand-sm navbar-light">
            <div class="container">
                <div class="navigation-bar-container">
                    <a class="navbar-brand navigation-bar-item"
                       href="${pageContext.request.contextPath}/index.jsp">MovieRating</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link navigation-bar-item"
                               href="MainController?command=film_top">Films</a>
                        </li>
                        <c:choose>
                            <c:when test="${user.type.typeName == 'guest'}">
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="${pageContext.request.contextPath}/jsp/authorization.jsp">Login</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="MainController?command=registration_setup">Register</a>
                                </li>
                            </c:when>
                            <c:when test="${user.type.typeName == 'user'}">
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="#">${user.username}</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="MainController?command=logout">Logout</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="#">${user.username}(admin)</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link navigation-bar-item"
                                       href="MainController?command=logout">Logout</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <div class="mainContent">
        <div class="container">
            <div class="filmPanel">
                <div class="filmImage imgDimension1">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&id=2">
                        <img src="${pageContext.request.contextPath}/img/spider-man.jpg">
                    </a>
                </div>
                <div class="filmImage imgDimension2">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&id=1">
                        <img src="${pageContext.request.contextPath}/img/thor3.jpg">
                    </a>
                </div>
                <div class="filmImage lastImage imgDimension1">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&id=7">
                        <img src="${pageContext.request.contextPath}/img/avenger_infinity_war.jpg">
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div>
        <p>${error}</p>
    </div>
</div>
<footer class="panel-footer">
    <div class="container">
        <div class="text-center">&copy; Copyright Kustov Ivan 2018</div>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>