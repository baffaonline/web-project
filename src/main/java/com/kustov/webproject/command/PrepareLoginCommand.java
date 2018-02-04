package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class PrepareLoginCommand implements Command{
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_authorization");
        String lastPage = request.getHeader("referer").substring(21);
        if ("/".equals(lastPage)) {
            lastPage = propertyManager.getProperty("path_page_default");
        }
        request.getSession().setAttribute("page", lastPage);
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
