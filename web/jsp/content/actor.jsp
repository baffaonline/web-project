<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>${actor.name} ${actor.surname}</title>
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
                    <c:choose>
                        <c:when test="${user.type.typeName == 'user'}">
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="${pageContext.request.contextPath}/jsp/user/user.jsp">${user.username}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="${pageContext.request.contextPath}/jsp/MainController?command=logout">Logout</a>
                            </li>
                        </c:when>
                        <c:when test="${user.type.typeName == 'admin'}">
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="#">${user.username}(admin)</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="${pageContext.request.contextPath}/jsp/MainController?command=logout">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="${pageContext.request.contextPath}/jsp/user/authorization.jsp">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link navigation-bar-item"
                                   href="${pageContext.request.contextPath}/jsp/MainController?command=registration_setup">Register</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>
</header>
<div class="mainContent">
    <div class="mainContent_container">
        <div class="actor-content">
            <div class="actor-title-item">
                <div class="actor-title-image">
                    <img src="${pageContext.request.contextPath}/${actor.imagePath}">
                </div>
                <div class="actor-title">
                    <strong>${actor.name} ${actor.surname}</strong>
                </div>
            </div>
            <hr>
            <div class="actor-country">
                <c:choose>
                    <c:when test="${actor.county != null}">
                        <p>
                            <span class="actor-country-span">Country : </span>
                                ${actor.country.name}
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <span class="actor-country-span">Country : </span>
                            no information
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
            <hr>
            <div class="actor-films-panel">
                <h2>Played in films</h2>
                <c:choose>
                    <c:when test="${actor.films != null}">
                        <c:forEach var="film" items="${actor.films}">
                            <hr>
                            <div class="actor-films-item">
                                <div class="actor-films-image">
                                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&film_id=${film.id}">
                                        <img src="${pageContext.request.contextPath}/${film.posterPath}">
                                    </a>
                                </div>
                                <div class="actor-film-name">
                                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&film_id=${film.id}">
                                        <div>${film.title}</div>
                                    </a>
                                </div>
                                <div class="actor-film-country">
                                    <c:choose>
                                        <c:when test="${film.country != null}">
                                            <div>${film.country.name}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>No information about country</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="actor-film-year">
                                    <div>${film.releaseDate.year}</div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div>No information</div>
                    </c:otherwise>
                </c:choose>
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
