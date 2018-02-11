package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.validator.LoginCommandValidator;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class LoginCommand.
 */

public class LoginCommand implements Command {

    /**
     * The receiver.
     */
    private UserReceiver receiver;

    /**
     * The Constant PARAM_LOGIN.
     */
    private final static String PARAM_LOGIN = "login";

    /**
     * The Constant PARAM_PASSWORD.
     */
    private final static String PARAM_PASSWORD = "password";

    /**
     * Instantiates a new login command.
     *
     * @param receiver the receiver
     */
    LoginCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    /* (non-Javadoc)
     * @see main.java.com.kustov.webproject.command.Command#execute(HttpServletRequest)
     */
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        PropertyManager pageManager = new PropertyManager("pages");
        String pageAuthorization = pageManager.getProperty("path_page_authorization");
        if (LoginCommandValidator.checkLoginAndPassword(login, password)) {
            try {
                User user = receiver.checkUser(login, password);
                if (user != null && !user.isBanned()) {
                    request.getSession(true).setAttribute("user", user);
                    page = (String) request.getSession().getAttribute("page");
                    request.getSession().removeAttribute("page");
                    return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
                } else if (user != null && user.isBanned()) {
                    request.setAttribute("errorInLoginOrPasswordMessage", "You are banned. " +
                            "Contact the administrator to get more information");
                    page = pageAuthorization;
                } else {
                    request.setAttribute("errorInLoginOrPasswordMessage", "Wrong login or password");
                    page = pageAuthorization;
                }
            } catch (ServiceException exc) {
                throw new CommandException(exc);
            }
        } else {
            request.setAttribute("errorInLoginOrPasswordMessage", "Login and password have to contain only letters, " +
                    "numbers and _");
            page = pageAuthorization;
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
