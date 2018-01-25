package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.FilmRatingComparator;
import com.kustov.webproject.service.PropertyManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FilmTopCommand implements Command{
    private final static Logger LOGGER = LogManager.getLogger();
    private FilmReceiver receiver;

    FilmTopCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String filmsPage = pageManager.getProperty("path_page_filmTop");
        try {
            List<Film> films = receiver.findFilms();
            films.sort(new FilmRatingComparator().reversed());
            request.getSession().setAttribute("films", films);
            LOGGER.log(Level.INFO, "Ok");
            page = filmsPage;
        } catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return page;
    }
}
