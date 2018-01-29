package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;

public class FilmCommand implements Command{
    private FilmReceiver receiver;

    FilmCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String filmPage = pageManager.getProperty("path_page_film");
        try {
            int id = Integer.parseInt(request.getParameter("film_id"));
            Film film = receiver.findFilmById(id);
            request.setAttribute("film", film);
            page = filmPage;
        } catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return page;
    }
}
