package com.kustov.webproject.filter;


import com.kustov.webproject.service.PropertyManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * The Class RedirectSecurityFilter.
 */

@WebFilter(urlPatterns = "/jsp/*")
public class RedirectSecurityFilter implements Filter {

    /**
     * The index page.
     */
    private String indexPage;

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     */
    @Override
    public void init(FilterConfig filterConfig) {
        PropertyManager propertyManager = new PropertyManager("pages");
        indexPage = propertyManager.getProperty("path_page_default");
    }

    /**
     * Do filter.
     *
     * @param servletRequest  the servlet request
     * @param servletResponse the servlet response
     * @param filterChain     the filter chain
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String page = request.getRequestURI();
        PropertyManager propertyManager = new PropertyManager("pages");
        if (!propertyManager.getProperty("path_page_admin").equals(page)) {
            response.sendRedirect(request.getContextPath() + indexPage);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {

    }
}
