package com.kustov.webproject.command;

import com.kustov.webproject.entity.Review;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class ReviewCommand.
 */
public class ReviewCommand implements Command {

    /**
     * The receiver.
     */
    private ReviewReceiver receiver;

    /**
     * Instantiates a new review command.
     *
     * @param receiver the receiver
     */
    ReviewCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");

        String filmPage = pageManager.getProperty("path_page_film_command");

        String pageMain = pageManager.getProperty("path_page_default");
        String markString = request.getParameter("rating");
        User thisUser = (User)request.getSession().getAttribute("user");
        if (markString == null || PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, pageMain);
        }
        String title = request.getParameter("title");
        String text = request.getParameter("reviewText");
        int filmId = Integer.parseInt(request.getParameter("filmId"));

        String thisPage = request.getContextPath() + filmPage + filmId;

        User user = (User) request.getSession().getAttribute("user");
        try {
            int mark = Integer.parseInt(markString);
            Review review = new Review(filmId, user, text, title, mark, null);
            if (receiver.insertReview(review)) {
                page = thisPage;
            } else {
                page = pageMain;
            }
        } catch (ServiceException exc) {
            throw new CommandException();
        }
        return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
    }
}
