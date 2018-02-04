package com.kustov.webproject.filter;


import com.kustov.webproject.service.PropertyManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/jsp/*")
public class RedirectSecurityFilter implements Filter{
    private String indexPage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        PropertyManager propertyManager = new PropertyManager("pages");
        indexPage = propertyManager.getProperty("path_page_default");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //21-const
        String page = request.getRequestURI();
        PropertyManager propertyManager = new PropertyManager("pages");
        if (!propertyManager.getProperty("path_page_authorization").equals(page)
                && !propertyManager.getProperty("path_page_admin").equals(page)) {
            response.sendRedirect(request.getContextPath() + indexPage);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
