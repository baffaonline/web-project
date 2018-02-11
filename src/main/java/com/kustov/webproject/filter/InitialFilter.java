package com.kustov.webproject.filter;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.service.MessageManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;


/**
 * The Class InitialFilter.
 */
@WebFilter(urlPatterns = "/index.jsp")
public class InitialFilter implements Filter {
    
    /**
     * Do filter.
     *
     * @param servletRequest the servlet request
     * @param servletResponse the servlet response
     * @param filterChain the filter chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user == null){
            user = new User();
            session.setAttribute("user", user);
            session.setAttribute("isUpdated", false);
        }
        String locale = (String)session.getAttribute("locale");
        if (locale == null){
            locale = "en";
            session.setAttribute("locale", locale);
            MessageManager.setLocale(new Locale(locale));
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {

    }
}
