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
        <form method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}/MainController?command=add_film">
            <div class="add-film-part">
                <label class="col-form-label" for="title"><fmt:message key="admin.add.film.title"/></label>
                <input type="text" name="filmTitle" id="title" class="input-group form-control" placeholder="<fmt:message
            key="admin.add.film.title.input"/>" value="${film.title}" required>
            </div>
            <div class="add-film-part">
                <label class="col-form-label" for="image"><fmt:message key="admin.add.film.image"/></label>
                <input type="file" accept="image/*" class="input-group form-control" name="filmImage" id="image"
                       placeholder="<fmt:message key="admin.add.film.image.input"/>" value="" required>
            </div>
            <div class="add-film-part">
                <label class="col-form-label" for="description"><fmt:message key="admin.add.film.description"/></label>
                <textarea class="form-control" name="filmDescription" id="description"
                          placeholder="<fmt:message key="admin.add.film.description.textarea"/>" required></textarea>
            </div>
            <div class="add-film-part">
                <label class="col-form-label" for="date"><fmt:message key="admin.add.film.date"/></label>
                <input type="text" class="input-group form-control" name="filmDate" id="date"
                       placeholder="<fmt:message key="admin.add.film.date.input"/>" required>
            </div>
            <div class="add-film-part">
                <label for="country" class="col-form-label"><fmt:message key="registration.label.country"/></label>
                <select name="country" id="country" class="form-control">
                    <c:forEach var="elem" items="${countries}">
                        <option value="${elem.id}">${elem}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="add-film-part">
                <label for="age" class="col-form-label"><fmt:message key="admin.add.film.age"/></label>
                <input type="text" class="input-group form-control" id="age" name="ageRestriction"
                       placeholder="<fmt:message key="admin.add.film.age.input"/>">
            </div>
            <div class="add-film-part">
                <label for="genres" class="col-form-label"><fmt:message key="admin.add.film.genres"/></label>
                <select class="form-control" name="genres[]" id="genres" multiple>
                    <c:forEach var="elem" items="${genres}">
                        <option value="${elem.id}">${elem}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="add-film-part">
                <label for="actors" class="col-form-label"><fmt:message key="admin.add.film.actors"/></label>
                <select class="form-control" name="actors[]" id="actors" multiple>
                    <c:forEach var="elem" items="${actors}">
                        <option value="${elem.id}">${elem}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn btn-primary add-button" type="submit"><fmt:message key="admin.add.film.button"/></button>
        </form>
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
