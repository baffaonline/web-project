package com.kustov.webproject.command;

import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {

    @Override
    public CommandPair execute(HttpServletRequest request) {
        PropertyManager propertyManager = new PropertyManager("pages");
        return new CommandPair(CommandPair.DispatchType.REDIRECT, propertyManager.getProperty("path_page_empty"));
    }
}
