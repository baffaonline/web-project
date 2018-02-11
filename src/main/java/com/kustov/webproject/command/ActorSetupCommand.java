package com.kustov.webproject.command;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.ActorReceiver;
import com.kustov.webproject.service.PropertyManager;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * The Class ActorSetupCommand.
 */
public class ActorSetupCommand implements Command {

    /**
     * The receiver.
     */
    private ActorReceiver receiver;

    /**
     * Instantiates a new actor setup command.
     *
     * @param receiver the receiver
     */
    ActorSetupCommand(ActorReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        User user = (User) request.getSession().getAttribute("user");
        if (!PageConstant.ADMIN_STRING.equals(user.getType().getTypeName())) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    propertyManager.getProperty("path_page_default"));
        }
        page = propertyManager.getProperty("path_page_admin_add_actor");
        try {
            Pair<List<Film>, List<Country>> filmListPair = receiver.findInformationForActor();

            request.getSession().setAttribute("films", filmListPair.getKey());
            request.getSession().setAttribute("countries", filmListPair.getValue());
            return new CommandPair(CommandPair.DispatchType.FORWARD, page);
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
    }
}
