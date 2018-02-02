<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>MovieRating</title>
</head>
<body>
<div id="page-body">
    <c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
        <c:param name="user" value="${user}"/>
    </c:import>
    <div class="mainContent">
        <div class="container">
            <div class="filmPanel">
                <div class="filmImage imgDimension1">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&film_id=2">
                        <img src="${pageContext.request.contextPath}/img/spider-man.jpg">
                    </a>
                </div>
                <div class="filmImage imgDimension2">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&film_id=1">
                        <img src="${pageContext.request.contextPath}/img/thor3.jpg">
                    </a>
                </div>
                <div class="filmImage lastImage imgDimension1">
                    <a href="${pageContext.request.contextPath}/jsp/MainController?command=film&film_id=7">
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