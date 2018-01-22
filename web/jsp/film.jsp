<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>${film.title}</title>
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
                        <li class="nav-item">
                            <a class="nav-link navigation-bar-item"
                               href="${pageContext.request.contextPath}/jsp/authorization.jsp">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link navigation-bar-item"
                               href="MainController?command=registration_setup">Register</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <div class="mainContent">
        <div class="mainContent_container">
            <div class="filmContent">
                <div class="film-title-item">
                    <div class="film-title-image">
                        <img src="${pageContext.request.contextPath}/${film.posterPath}">
                    </div>
                    <div class="film-title">
                        <strong>${film.title}(${film.releaseDate.year})</strong>
                    </div>
                </div>
                <hr>
                <div class="film-description">
                    <p>
                        <span class="description-span">Description.</span>
                        ${film.description}</p>
                </div>
                <hr>
                <div class="film-information">
                    <h2>Information about film</h2>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            Country:
                        </div>
                        <div class="film-information-item-content">
                            <c:if test="${film.country != null}">
                                ${film.country.name}
                            </c:if>
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            Age restriction:
                        </div>
                        <div class="film-information-item-content">
                            ${film.ageRestriction}+
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            Date of release:
                        </div>
                        <div class="film-information-item-content">
                            ${film.releaseDate}
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            Genres:
                        </div>
                        <div class="film-information-item-content">
                            <c:forEach var="genre" items="${film.genres}">
                                <span> ${genre.name}</span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="actor-squad">
                    <h2>Actor squad</h2>
                    <c:forEach var="actor" items="${film.actors}">
                        <hr>
                        <div class="actor-squad-item">
                            <div class="actor-squad-image">
                                <a href="#">
                                    <img src="${pageContext.request.contextPath}${actor.imagePath}">
                                </a>
                            </div>
                            <div class="actor-squad-name">
                                <a href="#">
                                    <div>${actor.name} ${actor.surname}</div>
                                </a>
                            </div>
                            <div class="actor-squad-country">
                                <div>${actor.country.name}</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <hr>
                <div class="film-rating">
                    Film rating is ${film.rating}
                </div>
                <hr>
                <c:if test="${not empty film.reviews}">
                    <div class="film-reviews">
                        <h2>User reviews</h2>
                        <c:forEach var="review" items="${film.reviews}">
                            <div class="review-text-item">
                                <div class="review-title">
                                    <strong>${review.title}</strong>
                                </div>
                                <div class="review-owner">
                                    <div>by ${review.user.username}</div>
                                </div>
                                <div class="review-text">
                                    <p>${review.text}</p>
                                </div>
                                <div class="review-mark">
                                    <div>My mark is ${review.userMark} from 10</div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="filter">
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