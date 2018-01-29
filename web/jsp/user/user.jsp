<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>${user.username}</title>
</head>
<body>
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
                           href="${pageContext.request.contextPath}/jsp/MainController?command=film_top">Films</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link navigation-bar-item"
                           href="#">${user.username}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link navigation-bar-item"
                           href="${pageContext.request.contextPath}/jsp/MainController?command=logout">Logout</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="mainContent">
    <div class="mainContent_container">
        <div class="user-content">
            <div class="user-information">
                <h2>Information about user</h2>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Username : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.username}</div>
                    </div>
                </div>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Email : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.email}</div>
                    </div>
                </div>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Name : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.name} ${user.surname}</div>
                    </div>
                </div>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Birthday : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.birthday.dayOfMonth}.${user.birthday.month.value}.${user.birthday.year}</div>
                    </div>
                </div>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Country : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.country.name}</div>
                    </div>
                </div>
                <div class="user-information-item">
                    <div class="user-information-left">
                        <strong>Rating : </strong>
                    </div>
                    <div class="user-information-right">
                        <div>${user.rating}</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="filter">

        </div>
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
