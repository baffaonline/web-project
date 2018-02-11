package com.kustov.webproject.command;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ActorReceiver;
import com.kustov.webproject.service.ArrayConverter;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.validator.SignUpValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;

public class AddActorCommand implements Command{

    private ActorReceiver receiver;

    AddActorCommand(ActorReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String actorPage = propertyManager.getProperty("path_page_actor_command");
        String addActorPage = propertyManager.getProperty("path_page_admin_add_actor");

        String actorName = request.getParameter("name");
        if (actorName == null){
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    propertyManager.getProperty("path_page_default"));
        }
        String actorSurname = request.getParameter("surname");
        int countryId = Integer.parseInt(request.getParameter("country"));
        String films[] = request.getParameterValues("films[]");

        try {
            if (isAllValid(request, actorName, actorSurname)) {
                Part filePart = request.getPart("actorImage");
                String filePath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                int filmsId[];
                filmsId = ArrayConverter.makeIntArrayFromString(films);

                Actor actor = receiver.addActor(actorName, actorSurname, countryId, filePath, filmsId);

                request.getSession().removeAttribute("countries");
                request.getSession().removeAttribute("films");

                return new CommandPair(CommandPair.DispatchType.REDIRECT,actorPage + actor.getId());
            }
            else {
                return new CommandPair(CommandPair.DispatchType.FORWARD, addActorPage);
            }
        }catch (ServletException | IOException | ServiceException exc){
            throw new CommandException(exc);
        }
    }

    private boolean isAllValid(HttpServletRequest request, String name, String surname){
        SignUpValidator validator = new SignUpValidator();
        MessageManager messageManager = new MessageManager();

        if (!validator.checkNameAndSurname(name, surname)) {
            request.setAttribute("errorNameOrSurname", messageManager.getString("command.add.actor.name.error"));
            return false;
        }
        return true;
    }
}
