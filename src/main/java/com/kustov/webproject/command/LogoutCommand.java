package com.kustov.webproject.command;

import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The Class LogoutCommand.
 */
public class LogoutCommand implements Command {

    /**
     * Instantiates a new logout command.
     */
    LogoutCommand() {
    }


    @Override
    public CommandPair execute(HttpServletRequest request) {
        PropertyManager propertyManager = new PropertyManager("pages");
        String mainPage = propertyManager.getProperty("path_page_default");
        HttpSession session = request.getSession(false);
        session.invalidate();
        return new CommandPair(CommandPair.DispatchType.REDIRECT, mainPage);
    }
}
