<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/fontawesome-free-5.0.6/web-fonts-with-css/css/fontawesome.min.css">
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
                            <c:choose>
                                <c:when test="${film.country != null}">
                                    ${film.country.name}
                                </c:when>
                                <c:otherwise>
                                    No information
                                </c:otherwise>
                            </c:choose>
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
                        <c:choose>
                            <c:when test="${film.genres != null}">
                                <div class="film-information-item-content">
                                    <c:forEach var="genre" items="${film.genres}">
                                        <span> ${genre.name}</span>
                                    </c:forEach>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div>
                                    No information about genres
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <c:if test="${film.actors != null}">
                    <div class="actor-squad">
                        <h2>Actor squad</h2>
                        <c:forEach var="actor" items="${film.actors}">
                            <hr>
                            <div class="actor-squad-item">
                                <div class="actor-squad-image">
                                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=actor&actor_id=${actor.id}">
                                        <img src="${pageContext.request.contextPath}/${actor.imagePath}">
                                    </a>
                                </div>
                                <div class="actor-squad-name">
                                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=actor&actor_id=${actor.id}">
                                        <div>${actor.name} ${actor.surname}</div>
                                    </a>
                                </div>
                                <div class="actor-squad-country">
                                    <c:choose>
                                        <c:when test="${actor.country.name != null}">
                                            <div>${actor.country.name}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>No information</div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <hr>
                <div class="film-rating">
                    Film rating is ${film.rating}
                </div>
                <hr>
                <c:if test="${not empty film.reviews}">
                    <div class="film-reviews">
                        <h2>User reviews</h2>
                        <c:forEach var="review" items="${film.reviews}">
                            <c:if test="${review.user.username == user.username}">
                                <c:set var="isUserReviewed" value="true" scope="page"/>
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
                                    <div class="user-review-edit">
                                        <a href="#">Edit review</a>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="review" items="${film.reviews}">
                            <c:if test="${review.user.username != user.username}">
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
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${isUserReviewed != 'true' and user.type.typeName != 'guest'}">
                    <form class="reviewForm" action="${pageContext.request.contextPath}/jsp/MainController"
                          method="post">
                        <input type="hidden" name="command" value="review"/>
                        <input type="hidden" name="filmId" value="${film.id}"/>
                        <div class="user-review">
                            <div class="user-review-title-panel">
                                <div class="user-review-image">
                                    <img src="${pageContext.request.contextPath}/${film.posterPath}"/>
                                </div>
                                <div class="user-review-title">
                                    <div>
                                        <strong>${film.title}(${film.releaseDate.year})</strong>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="user-review-rating-title">
                                <h3>Your rating</h3>
                            </div>
                            <div class="user-review-rating-panel">
                                <fieldset class="user-review-rating">
                                    <input type="radio" id="star10" name="rating" value="10"/>
                                    <label class="rating-star fa fa-star" for="star10" title="10 stars"></label>
                                    <input type="radio" id="star9" name="rating" value="9"/>
                                    <label class="rating-star fa fa-star" for="star9" title="9 stars"></label>
                                    <input type="radio" id="star8" name="rating" value="8"/>
                                    <label class="rating-star fa fa-star" for="star8" title="8 stars"></label>
                                    <input type="radio" id="star7" name="rating" value="7"/>
                                    <label class="rating-star fa fa-star" for="star7" title="7 stars"></label>
                                    <input type="radio" id="star6" name="rating" value="6"/>
                                    <label class="rating-star fa fa-star" for="star6" title="6 stars"></label>
                                    <input type="radio" id="star5" name="rating" value="5"/>
                                    <label class="rating-star fa fa-star" for="star5" title="5 stars"></label>
                                    <input type="radio" id="star4" name="rating" value="4"/>
                                    <label class="rating-star fa fa-star" for="star4" title="4 stars"></label>
                                    <input type="radio" id="star3" name="rating" value="3"/>
                                    <label class="rating-star fa fa-star" for="star3" title="3 stars"></label>
                                    <input type="radio" id="star2" name="rating" value="2"/>
                                    <label class="rating-star fa fa-star" for="star2" title="2 stars"></label>
                                    <input type="radio" id="star1" name="rating" value="1"/>
                                    <label class="rating-star fa fa-star" for="star1" title="1 star"></label>
                                </fieldset>
                            </div>
                            <div class="user-review-content">
                                <div class="user-review-content-title">
                                    <h3>Your review</h3>
                                </div>
                                <input type="text" name="title" placeholder="Write title to your review"
                                       maxlength="80" class="form-control" required>
                                <textarea type="text" name="reviewText" placeholder="Write your review here"
                                       maxlength="10000" class="form-control" required></textarea>
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </form>
                </c:if>
            </div>
            <div class="filter">
            </div>
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