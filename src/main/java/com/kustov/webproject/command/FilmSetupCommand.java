package com.kustov.webproject.command;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;
import javafx.util.Pair;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FilmSetupCommand implements Command{
    private FilmReceiver receiver;

    FilmSetupCommand(FilmReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager propertyManager = new PropertyManager("pages");
        page = propertyManager.getProperty("path_page_admin_add_film");
        try{
            Pair<Film, List<Country>> filmListPair = receiver.findInformationForFilm();
            Film film = filmListPair.getKey();
            request.getSession().setAttribute("countries", filmListPair.getValue());
            request.getSession().setAttribute("genres", film.getGenres());
            request.getSession().setAttribute("actors", film.getActors());
            return new CommandPair(CommandPair.DispatchType.FORWARD, page);
        }catch (ServiceException exc){
            throw new CommandException(exc);
        }
    }
}
