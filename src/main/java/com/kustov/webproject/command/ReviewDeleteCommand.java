package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ReviewReceiver;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class ReviewDeleteCommand implements Command {
    private ReviewReceiver receiver;

    ReviewDeleteCommand(ReviewReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        page = request.getHeader("referer").substring(21);
        if ("/".equals(page)) {
            PropertyManager propertyManager = new PropertyManager("pages");
            page = propertyManager.getProperty("path_page_default");
        }
        try{
            int filmId = Integer.parseInt(request.getParameter("filmId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            if (receiver.deleteReview(filmId, userId)){
                return page;
            }
            else{
                MessageManager messageManager = new MessageManager();
                request.setAttribute("errorInDelete", messageManager.getString("command.review.delete.error"));
                return page;
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
    }
}
