package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{
    LogoutCommand() {
    }

    @Override
    public String execute(HttpServletRequest request){
        PropertyManager propertyManager = new PropertyManager("pages");
        String mainPage = propertyManager.getProperty("path_page_default");
        request.getSession().removeAttribute("user");
        request.getSession(true).setAttribute("user", new User());
        return mainPage;
    }
}
