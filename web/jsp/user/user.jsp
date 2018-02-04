<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>${user.username}</title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
<div class="user-container">
    <div class="user-content">
        <div class="user-information">
            <h2><fmt:message key="user.information.header"/></h2>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.username"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.username}</div>
                </div>
            </div>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.email"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.email}</div>
                </div>
            </div>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.name"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.name} ${user.surname}</div>
                </div>
            </div>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.birthday"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.birthday.dayOfMonth}.${user.birthday.month.value}.${user.birthday.year}</div>
                </div>
            </div>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.country"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.country.name}</div>
                </div>
            </div>
            <div class="user-information-item">
                <div class="user-information-left">
                    <strong><fmt:message key="user.information.rating"/> </strong>
                </div>
                <div class="user-information-right">
                    <div>${user.rating}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="user-reviews">
        <h2><fmt:message key="user.reviews.header"/></h2>
        <c:forEach var="review" items="${user.reviews}">
            <div class="review-text-item">
                <div class="review-title">
                    <strong>${review.title}</strong>
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
            <div>
                <a href="${pageContext.request.contextPath}/MainController?command=film&film_id=${review.filmId}">
                    <fmt:message key="user.reviews.film.link"/>
                </a>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/MainController?command=review_delete&filmId=${review.filmId}&userId=${review.user.id}">
                    <fmt:message key="user.reviews.delete"/>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
<div class="filter">
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
