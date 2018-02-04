package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class ReviewRatingCommand implements Command{
    private ReviewReceiver receiver;

    ReviewRatingCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        String mainPage = propertyManager.getProperty("path_page_default");
        int filmId = Integer.parseInt(request.getParameter("film_id"));
        String thisPage = "/MainController?command=film&film_id=" + filmId;
        int userReviewId = Integer.parseInt(request.getParameter("user_id"));
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        String ratingString = request.getParameter("rating");
        int rating;
        if ("Yes".equals(ratingString)){
            rating = 1;
        }
        else {
            rating = -1;
        }
        try{
            if (receiver.insertReviewRating(filmId, userReviewId, userId, rating)){
                page = thisPage;
            }
            else{
                page = mainPage;
            }
        } catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
    }
}
