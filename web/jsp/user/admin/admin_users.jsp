<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title><fmt:message key="admin.title"/></title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
<div class="admin-content">
    <div class="container">
        <h2><fmt:message key="admin.users.header"/></h2>
        <div class="users-list">
            <c:forEach var="tempUser" items="${users}">
                <c:if test="${tempUser.username != user.username}">
                    <div class="users-list-item">
                        <div class="users-list-username">
                            <a href="${pageContext.request.contextPath}/MainController?command=user_information&page=admin&user_id=${tempUser.id}">
                                    ${tempUser.username}
                            </a>
                        </div>
                        <div class="users-list-rating">
                            <div><fmt:message key="admin.users.user.rating"/> ${tempUser.rating}</div>
                        </div>
                        <div class="users-list-ban">
                            <c:if test="${tempUser.isBanned()}">
                                <fmt:message key="admin.users.user.ban"/>
                            </c:if>
                            <c:if test="${!tempUser.isBanned()}">
                                <fmt:message key="admin.users.user.not.ban"/>
                            </c:if>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
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
