<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Authorization page</title>
</head>
<body>
    <h3>Enter your login</h3>
    <form action="MainController" method="post">
        <input type="hidden" name="command" value="login"/>
        <label>
            <input type="text" name="login" value=""/>
            <br/>
        </label>
        <h3>Enter your password</h3>
        <label>
            <input type="password" name="password" value=""/>
            <br/>
        </label>
        <label>
            <input type="submit" name="submit"/>
            <br/>
        </label>
    </form>
    <label>
    </label>
</body>
</html>
