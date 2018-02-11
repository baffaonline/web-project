package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class ReviewRatingCommand.
 */
public class ReviewRatingCommand implements Command {

    /**
     * The receiver.
     */
    private ReviewReceiver receiver;

    /**
     * Instantiates a new review rating command.
     *
     * @param receiver the receiver
     */
    ReviewRatingCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;

        PropertyManager propertyManager = new PropertyManager("pages");
        String mainPage = propertyManager.getProperty("path_page_default");

        User thisUser = (User)request.getSession().getAttribute("user");
        String filmIdString = request.getParameter("film_id");
        String userReviewIdString = request.getParameter("user_id");
        String ratingString = request.getParameter("rating");
        if (filmIdString == null || userReviewIdString == null || ratingString == null ||
                PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT, mainPage);
        }

        int filmId = Integer.parseInt(filmIdString);
        String thisPage = propertyManager.getProperty("path_page_film_command") + filmId;

        int userReviewId = Integer.parseInt(userReviewIdString);
        int userId = ((User) request.getSession().getAttribute("user")).getId();

        int rating = "Yes".equals(ratingString) ? 1 : -1;
        try {
            page = receiver.insertReviewRating(filmId, userReviewId, userId, rating) ? thisPage : mainPage;
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
    }
}
