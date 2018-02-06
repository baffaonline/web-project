<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
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
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="mainContent">
        <div class="mainContent_container">
            <div class="filmContent">
                <div>
                    ${warningEdit}
                </div>
                <div class="film-title-item">
                    <div class="film-title-image">
                        <c:if test="${film.posterPath != null}">
                            <img src="${pageContext.request.contextPath}/img/${film.posterPath}">
                        </c:if>
                        <c:if test="${film.posterPath == null}">
                            <img src="${pageContext.request.contextPath}/img/default.png">
                        </c:if>
                    </div>
                    <div class="film-title">
                        <strong>${film.title}(${film.releaseDate.year})</strong>
                    </div>
                </div>
                <hr>
                <div class="film-description">
                    <p>
                        <span class="description-span"><fmt:message key="film.content.description"/></span>
                        ${film.description}</p>
                </div>
                <hr>
                <div class="film-information">
                    <h2><fmt:message key="film.information.header"/></h2>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            <fmt:message key="film.information.country"/>
                        </div>
                        <div class="film-information-item-content">
                            <c:choose>
                                <c:when test="${film.country != null}">
                                    ${film.country.name}
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="film.information.country.warning"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            <fmt:message key="film.information.ageRestriction"/>
                        </div>
                        <div class="film-information-item-content">
                            ${film.ageRestriction}+
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            <fmt:message key="film.information.releaseDate"/>
                        </div>
                        <div class="film-information-item-content">
                            ${film.releaseDate}
                        </div>
                    </div>
                    <hr>
                    <div class="film-information-item">
                        <div class="film-information-item-name">
                            <fmt:message key="film.information.genres"/>
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
                                    <fmt:message key="film.information.genres.warning"/>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <c:if test="${film.actors != null}">
                    <div class="actor-squad">
                        <h2><fmt:message key="film.actorSquad.header"/></h2>
                        <c:forEach var="actor" items="${film.actors}">
                            <hr>
                            <div class="actor-squad-item">
                                <div class="actor-squad-image">
                                    <a href="${pageContext.request.contextPath}/MainController?command=actor&actor_id=${actor.id}">
                                        <c:if test="${actor.imagePath != null}">
                                            <img src="${pageContext.request.contextPath}/img/actors/${actor.imagePath}">
                                        </c:if>
                                        <c:if test="${actor.imagePath == null}">
                                            <img src="${pageContext.request.contextPath}/img/default.png">
                                        </c:if>
                                    </a>
                                </div>
                                <div class="actor-squad-name">
                                    <a href="${pageContext.request.contextPath}/MainController?command=actor&actor_id=${actor.id}">
                                        <div>${actor.name} ${actor.surname}</div>
                                    </a>
                                </div>
                                <div class="actor-squad-country">
                                    <c:choose>
                                        <c:when test="${actor.country.name != null}">
                                            <div>${actor.country.name}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div><fmt:message key="film.information.country.warning"/></div>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <hr>
                <div class="film-rating">
                    <fmt:message key="film.rating"/> ${film.rating}
                </div>
                <hr>
                <c:if test="${not empty film.reviews}">
                    <div class="film-reviews">
                        <h2><fmt:message key="film.review.header"/>
                            <span class="spoiler-span"><fmt:message key="film.review.spoiler"/></span></h2>
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
                                        <div>
                                            <fmt:message key="film.review.mark.first"/> ${review.userMark} <fmt:message
                                                key="film.review.mark.second"/> 10
                                        </div>
                                    </div>
                                    <div>
                                        <a href="${pageContext.request.contextPath}/MainController?command=review_delete&filmId=${review.filmId}&userId=${review.user.id}">
                                            <fmt:message key="film.review.delete"/>
                                        </a>
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
                                        <div><fmt:message key="film.review.user"/> ${review.user.username}</div>
                                    </div>
                                    <div class="review-text">
                                        <p>${review.text}</p>
                                    </div>
                                    <div class="review-mark">
                                        <div>
                                            <fmt:message key="film.review.mark.first"/> ${review.userMark} <fmt:message
                                                key="film.review.mark.second"/> 10
                                        </div>
                                    </div>
                                </div>
                                <div class="review-users-rating">
                                    <c:set var="good_rating" value="0"/>
                                    <c:set var="bad_rating" value="0"/>
                                    <c:forEach var="user_rate" items="${review.reviewUserRatings}">
                                        <c:if test="${user_rate.rating < 0}">
                                            <c:set var="bad_rating" value="${bad_rating + 1}"/>
                                        </c:if>
                                        <c:if test="${user_rate.rating > 0}">
                                            <c:set var="good_rating" value="${good_rating + 1}"/>
                                        </c:if>
                                        <c:if test="${user_rate.userId == user.id}">
                                            <c:set var="isUserRatedReview" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <fmt:message key="film.review.rating.first"/> ${good_rating} <fmt:message
                                        key="film.review.rating.second"/> <c:out value="${good_rating + bad_rating}"/>
                                </div>
                                <c:if test="${user.type.typeName != 'guest' and isUserRatedReview != 'true'}">
                                    <div class="review-user-rating">
                                        <span class="review-user-rating-span"><fmt:message
                                                key="film.review.rating.question"/></span>
                                        <div class="review-user-rating-bar">
                                            <form action="${pageContext.request.contextPath}/MainController"
                                                  method="post">
                                                <input type="hidden" name="command" value="review_rating">
                                                <input type="hidden" name="film_id" value="${film.id}">
                                                <input type="hidden" name="user_id" value="${review.user.id}">
                                                <button type="submit" name="rating" class="btn btn-success" value="Yes">
                                                    <fmt:message key="film.review.rating.yes"/>
                                                </button>
                                                <button type="submit" name="rating" class="btn btn-danger" value="No">
                                                    <fmt:message key="film.review.rating.no"/>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${isUserRatedReview == 'true'}">
                                    <div>
                                        <fmt:message key="film.review.rating.duplicate"/>
                                    </div>
                                    <c:set var="isUserRatedReview" value="false"/>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${isUserReviewed != 'true' and user.type.typeName != 'guest'}">
                    <form class="reviewForm" action="${pageContext.request.contextPath}/MainController"
                          method="post">
                        <input type="hidden" name="command" value="review"/>
                        <input type="hidden" name="filmId" value="${film.id}"/>
                        <div class="user-review">
                            <div class="user-review-title-panel">
                                <div class="user-review-image">
                                    <c:if test="${film.posterPath != null}">
                                        <img src="${pageContext.request.contextPath}/img/${film.posterPath}">
                                    </c:if>
                                    <c:if test="${film.posterPath == null}">
                                        <img src="${pageContext.request.contextPath}/img/default.png">
                                    </c:if>
                                </div>
                                <div class="user-review-title">
                                    <div>
                                        <strong>${film.title}(${film.releaseDate.year})</strong>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="user-review-rating-title">
                                <h3><fmt:message key="film.user.review.rating"/></h3>
                            </div>
                            <div class="user-review-rating-panel">
                                <fieldset class="user-review-rating">
                                    <input type="radio" id="star10" name="rating" value="10"/>
                                    <label class="rating-star fa fa-star" for="star10" title="10 stars"></label>
                                    <input type="radio" id="star9" name="rating" value="9" required/>
                                    <label class="rating-star fa fa-star" for="star9" title="9 stars"></label>
                                    <input type="radio" id="star8" name="rating" value="8" required/>
                                    <label class="rating-star fa fa-star" for="star8" title="8 stars"></label>
                                    <input type="radio" id="star7" name="rating" value="7" required/>
                                    <label class="rating-star fa fa-star" for="star7" title="7 stars"></label>
                                    <input type="radio" id="star6" name="rating" value="6" required/>
                                    <label class="rating-star fa fa-star" for="star6" title="6 stars"></label>
                                    <input type="radio" id="star5" name="rating" value="5" required/>
                                    <label class="rating-star fa fa-star" for="star5" title="5 stars"></label>
                                    <input type="radio" id="star4" name="rating" value="4" required/>
                                    <label class="rating-star fa fa-star" for="star4" title="4 stars"></label>
                                    <input type="radio" id="star3" name="rating" value="3" required/>
                                    <label class="rating-star fa fa-star" for="star3" title="3 stars"></label>
                                    <input type="radio" id="star2" name="rating" value="2" required/>
                                    <label class="rating-star fa fa-star" for="star2" title="2 stars"></label>
                                    <input type="radio" id="star1" name="rating" value="1" required/>
                                    <label class="rating-star fa fa-star" for="star1" title="1 star"></label>
                                </fieldset>
                            </div>
                            <div class="user-review-content">
                                <div class="user-review-content-title">
                                    <h3><fmt:message key="film.user.review.review"/></h3>
                                </div>
                                <input type="text" name="title" placeholder="<fmt:message
                                key="film.user.review.placeholder.title"/>"
                                       maxlength="80" class="form-control" required>
                                <textarea type="text" name="reviewText" placeholder="<fmt:message
                                key="film.user.review.placeholder.text"/>"
                                          maxlength="10000" class="form-control" required></textarea>
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message key="film.user.review.button"/>
                                </button>
                            </div>
                        </div>
                    </form>
                </c:if>
                <c:if test="${user.type.typeName == 'guest'}">
                    <div><a href="${pageContext.request.contextPath}/MainController?command=prepare_login">
                        <fmt:message key="film.guest.first"/></a>
                        <fmt:message key="film.guest.second"/> <a
                                href="${pageContext.request.contextPath}/MainController?command=country_setup">
                            <fmt:message key="film.guest.third"/></a> <fmt:message key="film.guest.fourth"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<c:import url="${pageContext.request.contextPath}/jsp/parts/footer.jsp"/>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>