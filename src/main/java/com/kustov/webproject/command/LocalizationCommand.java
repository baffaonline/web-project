package com.kustov.webproject.command;

import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocalizationCommand implements Command{
    @Override
    public CommandPair execute(HttpServletRequest request) {
        String page;
        page = request.getHeader("referer").substring(21);
        if ("/".equals(page)){
            PropertyManager propertyManager = new PropertyManager("pages");
            page = propertyManager.getProperty("path_page_default");
        }
        String locale = request.getParameter("newLocale");
        MessageManager.setLocale(new Locale(locale));
        request.getSession().setAttribute("locale", locale);
        return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
    }
}
