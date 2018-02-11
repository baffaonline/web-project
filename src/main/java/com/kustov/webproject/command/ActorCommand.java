package com.kustov.webproject.command;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ActorReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class ActorCommand implements Command {

    private ActorReceiver receiver;

    ActorCommand(ActorReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String actorId = request.getParameter("actor_id");
        if (actorId == null) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    pageManager.getProperty("path_page_default"));
        }
        String actorPage = pageManager.getProperty("path_page_actor");
        try {
            int id = Integer.parseInt(actorId);
            Actor actor = receiver.findActorById(id);
            request.setAttribute("actor", actor);
            page = actorPage;
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
