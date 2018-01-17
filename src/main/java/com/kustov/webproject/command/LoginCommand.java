package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.validator.LoginCommandValidator;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private UserReceiver receiver;
    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";

    LoginCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        PropertyManager pageManager = new PropertyManager("pages");
        String pageMain = pageManager.getProperty("path_page_main");
        String pageAuthorization = pageManager.getProperty("path_page_authorization");
        if (LoginCommandValidator.checkLoginAndPassword(login, password)) {
            try {
                User user = receiver.checkUser(login, password);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    page = pageMain;
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
        return page;
    }
}
