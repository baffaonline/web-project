<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>-
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <link href="https://fonts.googleapis.com/css?family=Slabo+27px" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Authorization page</title>
</head>
<body>
<h3>Enter your login</h3>
<form action="${pageContext.request.contextPath}/jsp/MainController" method="post">
    <input type="hidden" name="command" value="login"/>
    <div class="form-group">
        <label for="exampleInputLogin">Login</label>
        <input type="text" name="login" class="form-control" id="exampleInputLogin" aria-describedby="loginHelp"
               placeholder="Enter login" required>
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password" required>
    </div>
    <div>
        <p id="errorParagraph">${errorInLoginOrPasswordMessage}</p>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>
