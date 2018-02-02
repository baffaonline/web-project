package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


public class UserInformationCommand implements Command{
    private UserReceiver receiver;

    UserInformationCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        String option = request.getParameter("page");
        try{
            if ("admin".equals(option)){
                page = propertyManager.getProperty("path_page_admin_user");
                int id = Integer.parseInt(request.getParameter("user_id"));
                User user = receiver.findUserById(id);
                receiver.findReviewsForUser(user);
                request.setAttribute("neededUser", user);
            }
            else{
                page = propertyManager.getProperty("path_page_user");
                User user = (User)request.getSession().getAttribute("user");
                receiver.findReviewsForUser(user);
            }
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return page;
    }
}
