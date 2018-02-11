package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class UserInformationCommand.
 */

public class UserInformationCommand implements Command {

    /**
     * The receiver.
     */
    private UserReceiver receiver;

    /**
     * Instantiates a new user information command.
     *
     * @param receiver the receiver
     */
    UserInformationCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    /* (non-Javadoc)
     * @see main.java.com.kustov.webproject.command.Command#execute(HttpServletRequest)
     */
    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        String option = request.getParameter("page");

        try {
            User thisUser = (User) request.getSession().getAttribute("user");
            if ("guest".equals(thisUser.getType().getTypeName())) {
                return new CommandPair(CommandPair.DispatchType.REDIRECT,
                        propertyManager.getProperty("path_page_default"));
            }
            if ("admin".equals(option)) {
                page = propertyManager.getProperty("path_page_admin_user");
                int id = Integer.parseInt(request.getParameter("user_id"));
                User user = receiver.findUserById(id);
                receiver.findReviewsForUser(user);
                request.setAttribute("neededUser", user);
            } else {
                page = propertyManager.getProperty("path_page_user");
                User user = (User) request.getSession().getAttribute("user");
                receiver.findReviewsForUser(user);
            }
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
