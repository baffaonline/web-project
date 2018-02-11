package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;


/**
 * The Class FilmCommand.
 */
public class FilmCommand implements Command {

    /**
     * The receiver.
     */
    private FilmReceiver receiver;

    /**
     * Instantiates a new film command.
     *
     * @param receiver the receiver
     */
    FilmCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String filmPage = pageManager.getProperty("path_page_film");
        String filmId = request.getParameter("film_id");
        if (filmId == null) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    pageManager.getProperty("path_page_default"));
        }
        try {
            int id = Integer.parseInt(filmId);
            Film film = receiver.findFilmById(id);
            request.setAttribute("film", film);
            page = filmPage;
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
