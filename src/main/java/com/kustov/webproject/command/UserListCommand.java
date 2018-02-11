package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class UserListCommand implements Command{

    private UserReceiver receiver;

    UserListCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        page = propertyManager.getProperty("path_page_userList");
        try{
            List<User> users = receiver.findAllUsers();
            users.sort(Comparator.comparing(User::getUsername));
            request.setAttribute("users", users);
            return new CommandPair(CommandPair.DispatchType.FORWARD, page);
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
    }
}
