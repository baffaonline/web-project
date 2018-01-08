<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <title>Time & Locale info</title>
</head>
<body>
<div id="page-body">
    <header>
        <nav id="header-nav" class="navbar navbar-expand-lg navbar-light">
            <div class="container">
                <div>
                    <a class="navbar-brand" href="#">MovieRating</a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/jsp/authorization.jsp">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/jsp/registration.jsp">Register</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <div>
        <p>Loolz</p>
    </div>
</div>
<footer class="panel-footer">
    <div class="container">
        <div class="row">
            <section id="hours" class="col-sm-4">
                <span>Hours:</span><br>
                Sun-Thurs: 11:15am - 10:00pm<br>
                Fri: 11:15am - 2:30pm<br>
                Saturday Closed
                <hr class="visible-xs">
            </section>
            <section id="address" class="col-sm-4">
                <span>Address:</span><br>
                7105 Reisterstown Road<br>
                Baltimore, MD 21215
                <p>* Delivery area within 3-4 miles, with minimum order of $20 plus $3 charge for all deliveries.</p>
                <hr class="visible-xs">
            </section>
            <section id="testimonials" class="col-sm-4">
                <p>"The best Chinese restaurant I've been to! And that's saying a lot, since I've been to many!"</p>
                <p>"Amazing food! Great service! Couldn't ask for more! I'll be back again and again!"</p>
            </section>
        </div>
        <div class="text-center">&copy; Copyright David Chu's China Bistro 2016</div>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>