package com.kustov.webproject.command;

import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class BanCommand implements Command{
    private UserReceiver receiver;

    BanCommand(UserReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        int id = Integer.parseInt(request.getParameter("userId"));
        page = propertyManager.getProperty("path_page_admin_user_command") + id;
        try{
            User user = receiver.findUserById(id);
            if (!receiver.updateBan(id, !user.isBanned())){
                MessageManager messageManager = new MessageManager();
                request.setAttribute("errorBan", messageManager.getString("command.user.ban.error"));
            }
            return new CommandPair(CommandPair.DispatchType.REDIRECT, page);
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
    }
}
