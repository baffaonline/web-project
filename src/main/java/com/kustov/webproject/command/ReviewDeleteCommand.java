package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class ReviewDeleteCommand.
 */
public class ReviewDeleteCommand implements Command {

    /**
     * The receiver.
     */
    private ReviewReceiver receiver;

    /**
     * Instantiates a new review delete command.
     *
     * @param receiver the receiver
     */
    ReviewDeleteCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        String defaultPage = propertyManager.getProperty("path_page_default");
        page = request.getHeader("referer").substring(PageConstant.URI_START);
        if ("/".equals(page)) {
            page = defaultPage;
        }
        String filmIdString = request.getParameter("filmId");
        String userIdString = request.getParameter("userId");
        User thisUser = (User)request.getSession().getAttribute("user");
        if (filmIdString == null || userIdString == null ||
                PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    defaultPage);
        }
        try {
            int filmId = Integer.parseInt(filmIdString);
            int userId = Integer.parseInt(userIdString);
            if (!receiver.deleteReview(filmId, userId)) {
                MessageManager messageManager = new MessageManager();
                request.setAttribute("errorInDelete", messageManager.getString("command.review.delete.error"));
            }
            request.getSession().setAttribute("isUpdated", true);
            return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
    }
}
