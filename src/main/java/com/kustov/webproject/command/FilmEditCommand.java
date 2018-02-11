package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.service.ArrayConverter;
import com.kustov.webproject.service.MessageManager;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.service.StringDateFormatter;
import com.kustov.webproject.validator.AddFilmValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FilmEditCommand implements Command{

    private FilmReceiver receiver;

    FilmEditCommand(FilmReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String filmPage = propertyManager.getProperty("path_page_film_command");
        String editFilmPage = propertyManager.getProperty("path_page_admin_edit_film");

        Film film = (Film)request.getSession().getAttribute("film");
        User user = (User)request.getSession().getAttribute("user");
        if (film == null || !"admin".equals(user.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    propertyManager.getProperty("path_page_default"));
        }
        int filmId = film.getId();
        String filmTitle = request.getParameter("filmTitle");
        String description = request.getParameter("filmDescription");
        String dateString = request.getParameter("filmDate");
        String country = request.getParameter("country");
        String ageRestrictionString = request.getParameter("ageRestriction");
        String oldGenres[] = request.getParameterValues("oldGenres[]");
        String oldActors[] = request.getParameterValues("oldActors[]");
        String newGenres[] = request.getParameterValues("newGenres[]");
        String newActors[] = request.getParameterValues("newActors[]");

        try {
            if (isAllValid(request, dateString, ageRestrictionString)) {

                Part filePart = request.getPart("filmImage");
                String filePath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                LocalDate localDate = StringDateFormatter.getDateFromString(dateString);
                int ageRestriction = Integer.parseInt(ageRestrictionString);
                int countryId = Integer.parseInt(country);
                int oldGenresId[], oldActorsId[], newGenresId[], newActorsId[];
                oldGenresId = ArrayConverter.makeIntArrayFromString(oldGenres);
                oldActorsId = ArrayConverter.makeIntArrayFromString(oldActors);
                newGenresId = ArrayConverter.makeIntArrayFromString(newGenres);
                newActorsId = ArrayConverter.makeIntArrayFromString(newActors);

                if (!receiver.editFilm(filmId, filmTitle, filePath, description, localDate, countryId,
                        ageRestriction, oldGenresId, oldActorsId, newGenresId, newActorsId)) {
                    MessageManager messageManager = new MessageManager();
                    request.setAttribute("warningEdit", messageManager.getString("command.edit.film.warning"));
                }

                request.getSession().removeAttribute("countries");
                request.getSession().removeAttribute("genres");
                request.getSession().removeAttribute("actors");
                request.getSession().removeAttribute("film");

                return new CommandPair(CommandPair.DispatchType.REDIRECT, filmPage + filmId);
            }
            return new CommandPair(CommandPair.DispatchType.FORWARD, editFilmPage);
        }catch (ServletException | IOException | ServiceException exc){
            throw new CommandException(exc);
        }
    }

    private boolean isAllValid(HttpServletRequest request, String dateString, String ageRestrictionString){
        AddFilmValidator validator = new AddFilmValidator();
        boolean isValid = true;
        MessageManager messageManager = new MessageManager();
        if (!validator.checkDate(dateString)) {
            request.setAttribute("errorDate", messageManager.getString("command.add.film.date.error"));
            isValid = false;
        }
        if (!validator.checkAgeRestriction(ageRestrictionString)){
            request.setAttribute("errorAgeRestriction",
                    messageManager.getString("command.add.film.ageRestriction.error"));
            isValid = false;
        }
        return isValid;
    }
}
