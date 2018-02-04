<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<header>
    <nav id="header-nav" class="navbar navbar-expand-sm navbar-light">
        <div class="container">
            <div class="navigation-bar-container">
                <a class="navbar-brand navigation-bar-item"
                   href="${pageContext.request.contextPath}/index.jsp">MovieRating</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link navigation-bar-item"
                           href="${pageContext.request.contextPath}/MainController?command=film_top">
                            <fmt:message key="header.films"/>
                        </a>
                    </li>
                    <ctg:user-list user="${user}" contextPath="${pageContext.request.contextPath}"/>
                    <li class="nav-item">
                        <a class="nav-link navigation-bar-item"
                           href="${pageContext.request.contextPath}/MainController?command=localization&newLocale=ru_RU">
                            <fmt:message key="header.russian"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link navigation-bar-item"
                           href="${pageContext.request.contextPath}/MainController?command=localization&newLocale=en_EN">
                            English
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>