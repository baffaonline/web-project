package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class DeleteFilmCommand.
 */

public class DeleteFilmCommand implements Command {

    /**
     * The receiver.
     */
    private FilmReceiver receiver;

    /**
     * Instantiates a new delete film command.
     *
     * @param receiver the receiver
     */
    DeleteFilmCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String filmTopPage = propertyManager.getProperty("path_page_film_top_command")
                + PageConstant.ADMIN_STRING;
        String indexPage = propertyManager.getProperty("path_page_default");
        String filmId = request.getParameter("filmId");
        if (filmId == null) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT, indexPage);
        }
        int id = Integer.parseInt(filmId);
        try {
            if (receiver.deleteFilm(id)) {
                return new CommandPair(CommandPair.DispatchType.REDIRECT, filmTopPage);
            } else {
                return new CommandPair(CommandPair.DispatchType.REDIRECT, indexPage);
            }
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
    }
}
