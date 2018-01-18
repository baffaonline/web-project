package com.kustov.webproject.command;

import com.kustov.webproject.entity.CountriesMap;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.FilmRatingComparator;
import com.kustov.webproject.service.PropertyManager;
import com.mysql.cj.mysqlx.protobuf.MysqlxCrud;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class FilmCommand implements Command{
    private FilmReceiver receiver;

    FilmCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String filmsPage = pageManager.getProperty("path_page_films");
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
