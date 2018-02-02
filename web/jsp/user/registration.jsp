<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title><fmt:message key="registration.title"/></title>
</head>
<body>
<c:import url="${pageContext.request.contextPath}/jsp/parts/header.jsp">
    <c:param name="user" value="${user}"/>
</c:import>
<div class="container">
    <div class="formDiv">
        <h2><fmt:message key="registration.sign_up.header"/></h2>
        <form action="${pageContext.request.contextPath}/jsp/MainController" id="regForm" method="post" role="form">
            <input type="hidden" name="command" value="sign_up"/>
            <div class="form-group">
                <label for="username" class="control-label"><fmt:message key="registration.label.username"/></label>
                <div>
                    <input type="text" name="username" class="form-control" id="username"
                           placeholder="<fmt:message key="registration.placeholder.username"/>"
                           required>
                </div>
                <p>${errorUsername}</p>
            </div>
            <div class="form-group">
                <label for="password" class="control-label"><fmt:message key="registration.label.password"/></label>
                <div>
                    <input type="password" name="password" class="form-control" id="password"
                           placeholder="<fmt:message key="authorization.placeholder.password"/>" required>
                </div>
                <p>${errorPassword}</p>
            </div>
            <div class="form-group">
                <label for="email" class="control-label"><fmt:message key="registration.label.email"/></label>
                <div>
                    <input type="email" name="email" class="form-control" id="email"
                           placeholder="<fmt:message key="registration.placeholder.email"/>" required>
                </div>
                <p>${errorEmail}</p>
            </div>
            <div class="form-group">
                <label for="firstName" class="control-label"><fmt:message key="registration.label.firstName"/></label>
                <div>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           placeholder="<fmt:message key="registration.placeholder.firstName"/>"
                           pattern="[a-zA-Zа-яА-Я]{3,45}" required>
                </div>
            </div>
            <div class="form-group">
                <label for="secondName" class="control-label"><fmt:message key="registration.label.secondName"/></label>
                <div>
                    <input type="text" name="secondName" pattern="[a-zA-Zа-яА-Я]{3,45}"
                           class="form-control" id="secondName"
                           placeholder="<fmt:message key="registration.placeholder.secondName"/>" required>
                </div>
                <p>${errorName}</p>
            </div>
            <div class="form-group">
                <label for="birthday" class="control-label"><fmt:message key="registration.label.birthday"/></label>
                <div>
                    <input type="date" name="birthday" class="form-control" id="birthday"
                           placeholder="<fmt:message key="registration.placeholder.birthday"/>"
                           oninvalid="this.setCustomValidity('Format yyyy-mm-dd')" required>
                </div>
                <p>${errorBirthday}</p>
            </div>
            <div class="form-group">
                <label for="country" class="control-label"><fmt:message key="registration.label.country"/></label>
                <div>
                    <select name="country" id="country" class="form-control">
                        <c:forEach var="elem" items="${countries}">
                            <option value="${elem}">${elem}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group"></div>
            <button type="submit" class="align-content-center btn btn-primary">
                <fmt:message key="registration.button.submit"/>
            </button>
        </form>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
