<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="page"/>
<fmt:setBundle basename="message" scope="session"/>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
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
        <form action="${pageContext.request.contextPath}/MainController" id="regForm" method="post" role="form">
            <c:if test="${!isWrongInput}">
                <input type="hidden" name="command" value="sign_up"/>
                <div class="form-group">
                    <label for="username" class="control-label"><fmt:message key="registration.label.username"/></label>
                    <div>
                        <input type="text" name="username" class="form-control" id="username"
                               pattern="[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я\d]+"
                               placeholder="<fmt:message key="registration.placeholder.username"/>"
                               required>
                    </div>
                    <div class="errorDiv">${errorUsername}</div>
                </div>
                <div class="form-group">
                    <label for="password" class="control-label"><fmt:message key="registration.label.password"/></label>
                    <div>
                        <input type="password" name="password" class="form-control" id="password"
                               pattern="[a-zA-Zа-яА-Я\d]+"
                               placeholder="<fmt:message key="authorization.placeholder.password"/>" required>
                    </div>
                    <div class="errorDiv">${errorPassword}</div>
                </div>
                <div class="form-group">
                    <label for="email" class="control-label"><fmt:message key="registration.label.email"/></label>
                    <div>
                        <input type="email" name="email" class="form-control" id="email"
                               placeholder="<fmt:message key="registration.placeholder.email"/>" required>
                    </div>
                    <div class="errorDiv">${errorEmail}</div>
                </div>
                <div class="form-group">
                    <label for="firstName" class="control-label"><fmt:message
                            key="registration.label.firstName"/></label>
                    <div>
                        <input type="text" name="firstName" class="form-control" id="firstName"
                               placeholder="<fmt:message key="registration.placeholder.firstName"/>"
                               pattern="[a-zA-Zа-яА-Я]{3,45}" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="secondName" class="control-label"><fmt:message
                            key="registration.label.secondName"/></label>
                    <div>
                        <input type="text" name="secondName" pattern="[a-zA-Zа-яА-Я]{3,45}"
                               class="form-control" id="secondName"
                               placeholder="<fmt:message key="registration.placeholder.secondName"/>" required>
                    </div>
                    <div class="errorDiv">${errorName}</div>
                </div>
                <div class="form-group">
                    <label for="birthday" class="control-label"><fmt:message key="registration.label.birthday"/></label>
                    <div>
                        <input type="date" name="birthday" class="form-control" id="birthday"
                               placeholder="<fmt:message key="registration.placeholder.birthday"/>"
                               required>
                    </div>
                    <div class="errorDiv">${errorBirthday}</div>
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
            </c:if>
            <c:if test="${isWrongInput}">
                <input type="hidden" name="command" value="sign_up"/>
                <div class="form-group">
                    <label for="wrongUsername" class="control-label"><fmt:message
                            key="registration.label.username"/></label>
                    <div>
                        <input type="text" name="username" class="form-control" id="wrongUsername"
                               pattern="[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я\d]+" value="${username}" required>
                    </div>
                    <div class="errorDiv">${errorUsername}</div>
                </div>
                <div class="form-group">
                    <label for="wrongPassword" class="control-label"><fmt:message
                            key="registration.label.password"/></label>
                    <div>
                        <input type="password" name="password" class="form-control" id="wrongPassword"
                               pattern="[a-zA-Zа-яА-Я\d]+" value="${password}" required>
                    </div>
                    <div class="errorDiv">${errorPassword}</div>
                </div>
                <div class="form-group">
                    <label for="wrongEmail" class="control-label"><fmt:message key="registration.label.email"/></label>
                    <div>
                        <input type="email" name="email" class="form-control" id="wrongEmail"
                               value="${email}" required>
                    </div>
                    <div class="errorDiv">${errorEmail}</div>
                </div>
                <div class="form-group">
                    <label for="wrongFirstName" class="control-label"><fmt:message
                            key="registration.label.firstName"/></label>
                    <div>
                        <input type="text" name="firstName" class="form-control" id="wrongFirstName"
                               value="${name}" pattern="[a-zA-Zа-яА-Я]{3,45}" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="wrongSecondName" class="control-label"><fmt:message
                            key="registration.label.secondName"/></label>
                    <div>
                        <input type="text" name="secondName" pattern="[a-zA-Zа-яА-Я]{3,45}"
                               class="form-control" id="wrongSecondName"
                               value="${surname}" required>
                    </div>
                    <div class="errorDiv">${errorName}</div>
                </div>
                <div class="form-group">
                    <label for="wrongBirthday" class="control-label"><fmt:message
                            key="registration.label.birthday"/></label>
                    <div>
                        <input type="date" name="birthday" class="form-control" id="wrongBirthday"
                               value="${releaseDate}" required>
                    </div>
                    <div class="errorDiv">${errorBirthday}</div>
                </div>
                <div class="form-group">
                    <label for="wrongCountry" class="control-label"><fmt:message
                            key="registration.label.country"/></label>
                    <div>
                        <select name="country" id="wrongCountry" class="form-control">
                            <c:forEach var="elem" items="${countries}">
                                <c:if test="${elem.id == country}">
                                    <option selected value="${elem.id}">${elem}</option>
                                </c:if>
                                <c:if test="${elem.id != country}">
                                    <option value="${elem.id}">${elem}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>
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
