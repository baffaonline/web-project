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

@WebFilter(urlPatterns = "/index.jsp")
public class InitialFilter implements Filter {
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
            locale = "en_EN";
            session.setAttribute("locale", locale);
            MessageManager.setLocale(new Locale(locale));
        }
        //index - lol
//        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
