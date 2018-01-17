<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
    <div>Successful authorization</div>
    <div>Hi, ${user.username}</div>
    <a href="${pageContext.request.contextPath}/index.jsp">Return</a>
</body>
</html>
