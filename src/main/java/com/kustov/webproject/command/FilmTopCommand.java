package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.FilmRatingComparator;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FilmTopCommand implements Command{
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
            page = filmsPage;
        } catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return page;
    }
}
