package com.kustov.webproject.command;

import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * The Class EditFilmSetupCommand.
 */
public class EditFilmSetupCommand implements Command {

    /**
     * The receiver.
     */
    private FilmReceiver receiver;

    /**
     * Instantiates a new edits the film setup command.
     *
     * @param receiver the receiver
     */
    EditFilmSetupCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_admin_edit_film");

        String filmId = request.getParameter("filmId");
        User thisUser = (User) request.getSession().getAttribute("user");
        if (filmId == null || !"admin".equals(thisUser.getType().getTypeName())) {
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    propertyManager.getProperty("path_page_default"));
        }

        int id = Integer.parseInt(filmId);
        try {
            Film film = receiver.findFilmById(id);
            request.getSession().setAttribute("film", film);
            Pair<Film, List<Country>> filmListPair = receiver.findInformationForFilm();
            List<Genre> genres = filmListPair.getKey().getGenres();

            if ((genres != null) && (film.getGenres() != null)) {
                for (Genre genre : film.getGenres()) {
                    if (genres.contains(genre)) {
                        genres.remove(genre);
                    }
                }
            }

            List<Actor> actors = filmListPair.getKey().getActors();
            if ((actors != null) && film.getActors() != null) {
                for (Actor actor : film.getActors()) {
                    if (actors.contains(actor)) {
                        actors.remove(actor);
                    }
                }
            }

            request.getSession().setAttribute("genres", genres);
            request.getSession().setAttribute("actors", actors);
            request.getSession().setAttribute("countries", filmListPair.getValue());

            return new CommandPair(CommandPair.DispatchType.FORWARD, page);
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
    }
}
