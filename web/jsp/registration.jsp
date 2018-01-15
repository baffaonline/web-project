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
            <input type="hidden" name="command" value="registration"/>
            <div class="form-group">
                <label for="login" class="control-label">Login</label>
                <div>
                    <input type="text" name="login" class="form-control" id="login" placeholder="Enter login">
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="control-label">Password</label>
                <div>
                    <input type="password" name="password" class="form-control" id="password"
                           placeholder="Password">
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="control-label">Login</label>
                <div>
                    <input type="email" name="email" class="form-control" id="email" placeholder="Enter email">
                </div>
            </div>
            <div class="form-group">
                <label for="firstName" class="control-label">First name</label>
                <div>
                    <input type="text" name="firstName" class="form-control" id="firstName"
                           placeholder="First name">
                </div>
            </div>
            <div class="form-group">
                <label for="secondName" class="control-label">Second name</label>
                <div>
                    <input type="text" name="secondName" class="form-control" id="secondName"
                           placeholder="Second name">
                </div>
            </div>
            <div class="form-group">
                <label for="birthday" class="control-label">Birthday</label>
                <div>
                    <input type="date" name="birthday" class="form-control" id="birthday"
                           placeholder="Birthday">
                </div>
            </div>
            <div class="form-group">
                <label for="country" class="control-label">Country</label>
                <div>
                    <select id="country" class="form-control">
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
<%--<!--
	Author: W3layouts
	Author URL: http://w3layouts.com
	License: Creative Commons Attribution 3.0 Unported
	License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE html>
<html>
<head>
<title>Flat Sign Up Form Responsive Widget Template| Home :: W3layouts</title>
<!-- Meta tag Keywords -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Flat Sign Up Form Responsive Widget Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Meta tag Keywords -->
<!-- css files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all">
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" media="all">
<!-- //css files -->
<!-- online-fonts -->
<link href='//fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'><link href='//fonts.googleapis.com/css?family=Raleway+Dots' rel='stylesheet' type='text/css'>
</head>
<body>
<!--header-->
	<div class="header-w3l">
		<h1>Flat Sign Up Form</h1>
	</div>
<!--//header-->
<!---728x90--->
<!--main-->
<div class="main-agileits">
	<h2 class="sub-head">Sign Up</h2>
		<div class="sub-main">
			<form action="#" method="post">
				<input placeholder="First Name" name="Name" type="text" required="">
					<span class="icon1"><i class="fa fa-user" aria-hidden="true"></i></span><br>
				<input placeholder="Last Name" name="Name" type="text" required="">
					<span class="icon2"><i class="fa fa-user" aria-hidden="true"></i></span><br>
				<input placeholder="Phone Number" name="Number"type="text" required="">
					<span class="icon3"><i class="fa fa-phone" aria-hidden="true"></i></span><br>
				<input placeholder="Email" name="mail" type="text" required="">
					<span class="icon4"><i class="fa fa-envelope" aria-hidden="true"></i></span><br>
				<input  placeholder="Password" name="Password" type="password" required="">
					<span class="icon5"><i class="fa fa-unlock" aria-hidden="true"></i></span><br>
				<input  placeholder="Confirm Password" name="Password"type="password" required="">
					<span class="icon6"><i class="fa fa-unlock" aria-hidden="true"></i></span><br>

				<input type="submit" value="sign up">
			</form>
		</div>
		<div class="clear"></div>
</div>
<!--//main-->
<!---728x90--->
<!--footer-->
<div class="footer-w3">
	<p>&copy; 2016 Flat Sign Up Form. All rights reserved | Design by <a href="http://w3layouts.com">W3layouts</a></p>
</div>
<!--//footer-->
<!---728x90--->
</body>
</html>--%>