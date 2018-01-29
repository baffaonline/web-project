<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="formDiv">
        <h2>Sign up</h2>
        <form action="${pageContext.request.contextPath}/jsp/MainController" id="regForm" method="post" role="form">
            <input type="hidden" name="command" value="sign_up"/>
            <div class="form-group">
                <label for="username" class="control-label">Username</label>
                <div>
                    <input type="text" name="username" class="form-control" id="username" placeholder="Enter username"
                           <%--pattern="^[a-zA-Z]\w+$" oninvalid="this.setCustomValidity('Login starts with letter, ' +--%>
                            <%--'contains only letters, numbers and _')"--%>
                           required>
                </div>
                <p>${errorUsername}</p>
            </div>
            <div class="form-group">
                <label for="password" class="control-label">Password</label>
                <div>
                    <input type="password" name="password" class="form-control" id="password"
                           placeholder="Password" required>
                </div>
                <p>${errorPassword}</p>
            </div>
            <div class="form-group">
                <label for="email" class="control-label">Email</label>
                <div>
                    <input type="email" name="email" class="form-control" id="email" placeholder="Enter email"
                    required>
                </div>
                <p>${errorEmail}</p>
            </div>
            <div class="form-group">
                <label for="firstName" class="control-label">First name</label>
                <div>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           placeholder="First name" required>
                </div>
            </div>
            <div class="form-group">
                <label for="secondName" class="control-label">Second name</label>
                <div>
                    <input type="text" name="secondName" class="form-control" id="secondName"
                           placeholder="First name" required>
                </div>
                <p>${errorName}</p>
            </div>
            <div class="form-group">
                <label for="birthday" class="control-label">Birthday</label>
                <div>
                    <input type="date" name="birthday" class="form-control" id="birthday"
                           placeholder="Birthday" pattern="\d{4}-\d{2}-\d{2}"
                           oninvalid="this.setCustomValidity('Format yyyy-mm-dd')" required>
                </div>
                <p>${errorBirthday}</p>
            </div>
            <div class="form-group">
                <label for="country" class="control-label">Country</label>
                <div>
                    <select name="country" id="country" class="form-control">
                        <c:forEach var="elem" items="${countries}">
                            <option value="${elem}">${elem}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group"></div>
            <button type="submit" class="align-content-center btn btn-primary">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
