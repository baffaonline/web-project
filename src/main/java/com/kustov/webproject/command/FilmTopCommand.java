package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.PropertyManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

/**
 * The Class FilmTopCommand.
 */

public class FilmTopCommand implements Command {

    /**
     * The Constant LOGGER.
     */
    private final static Logger LOGGER = LogManager.getLogger();

    /**
     * The receiver.
     */
    private FilmReceiver receiver;

    /**
     * Instantiates a new film top command.
     *
     * @param receiver the receiver
     */
    FilmTopCommand(FilmReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String filmsPage = pageManager.getProperty("path_page_filmTop");
        String adminFilmsEditPage = pageManager.getProperty("path_page_admin_edit_films");
        String adminFilmsDeletePage = pageManager.getProperty("path_page_admin_delete_films");
        try {
            List<Film> films = receiver.findFilms();
            films.sort(Comparator.comparing(Film::getRating).reversed());
            request.setAttribute("films", films);
            LOGGER.log(Level.INFO, "Ok");
            String option = request.getParameter("option");
            switch (option) {
                case PageConstant.EDIT:
                    page = adminFilmsEditPage;
                    break;
                case PageConstant.DELETE:
                    page = adminFilmsDeletePage;
                    break;
                default:
                    page = filmsPage;
            }
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
