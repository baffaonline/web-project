<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>${actor.name} ${actor.surname}</title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
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
                    <c:when test="${actor.country != null}">
                        <p>
                            <span class="actor-country-span"><fmt:message key="actor.information.country"/> </span>
                                ${actor.country.name}
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>
                            <span class="actor-country-span"><fmt:message key="actor.information.country"/> </span>
                            <fmt:message key="actor.information.country.warning"/>
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
            <hr>
            <div class="actor-films-panel">
                <h2><fmt:message key="actor.films.header"/></h2>
                <c:choose>
                    <c:when test="${actor.films != null}">
                        <c:forEach var="film" items="${actor.films}">
                            <hr>
                            <div class="actor-films-item">
                                <div class="actor-films-image">
                                    <a href="${pageContext.request.contextPath}/MainController?command=film&film_id=${film.id}">
                                        <img src="${pageContext.request.contextPath}/img/${film.posterPath}">
                                    </a>
                                </div>
                                <div class="actor-film-name">
                                    <a href="${pageContext.request.contextPath}/MainController?command=film&film_id=${film.id}">
                                        <div>${film.title}</div>
                                    </a>
                                </div>
                                <div class="actor-film-country">
                                    <c:choose>
                                        <c:when test="${film.country != null}">
                                            <div>${film.country.name}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div><fmt:message key="actor.films.country.warning"/></div>
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
                        <div><fmt:message key="actor.information.country.warning"/></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="filter">
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
