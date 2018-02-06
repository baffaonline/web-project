package com.kustov.webproject.command;

import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.entity.Genre;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EditFilmSetupCommand implements Command {
    private FilmReceiver receiver;

    EditFilmSetupCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String page = propertyManager.getProperty("path_page_admin_edit_film");
        int id = Integer.parseInt(request.getParameter("filmId"));
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
            if ((actors != null) && film.getActors() != null){
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
