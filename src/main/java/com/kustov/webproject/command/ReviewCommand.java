package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class ReviewCommand implements Command {
    private ReviewReceiver receiver;

    ReviewCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String pageMain = pageManager.getProperty("path_page_default");
        String markString = request.getParameter("rating");
        String title = request.getParameter("title");
        String text = request.getParameter("reviewText");
        int filmId = Integer.parseInt(request.getParameter("filmId"));
        String thisPage = request.getContextPath() + "/jsp/MainController?command=film&film_id=" + filmId;
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
        return page;
    }
}
