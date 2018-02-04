package com.kustov.webproject.command;

import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.FilmReceiver;
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
import java.util.Locale;

public class AddFilmCommand implements Command{
    private FilmReceiver receiver;

    AddFilmCommand(FilmReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        PropertyManager propertyManager = new PropertyManager("pages");
        String filmPage = propertyManager.getProperty("path_page_film_command");
        String addFilmPage = propertyManager.getProperty("path_page_admin_add_film");

        String filmTitle = request.getParameter("filmTitle");
        String description = request.getParameter("filmDescription");
        String dateString = request.getParameter("filmDate");
        String country = request.getParameter("country");
        String ageRestrictionString = request.getParameter("ageRestriction");
        String genres[] = request.getParameterValues("genres[]");
        String actors[] = request.getParameterValues("actors[]");
        try {
            if (isAllValid(request, dateString, ageRestrictionString)) {
                Part filePart = request.getPart("filmImage");
                String filePath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                LocalDate localDate = StringDateFormatter.getDateFromString(dateString);
                int ageRestriction = Integer.parseInt(ageRestrictionString);
                int countryId = Integer.parseInt(country);
                int genresId[], actorsId[];
                genresId = makeIntArrayFromString(genres);
                actorsId = makeIntArrayFromString(actors);
                Film film = receiver.addFilm(filmTitle, filePath, description, localDate, countryId, ageRestriction,
                        genresId, actorsId);
                request.getSession().removeAttribute("countries");
                request.getSession().removeAttribute("genres");
                request.getSession().removeAttribute("actors");
                return new CommandPair(CommandPair.DispatchType.REDIRECT,filmPage + film.getId());
            }
            else {
                return new CommandPair(CommandPair.DispatchType.FORWARD, addFilmPage);
            }
        }catch (ServletException | IOException | ServiceException exc){
            throw new CommandException(exc);
        }
    }
    
    private boolean isAllValid(HttpServletRequest request, String dateString, String ageRestrictionString){
        AddFilmValidator validator = new AddFilmValidator();
        boolean isValid = true;
        MessageManager messageManager = new MessageManager();
            MessageManager.setLocale(new Locale((String)request.getSession().getAttribute("locale")));
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

    private int[] makeIntArrayFromString(String array[]){
        int intArray[];
        if (array != null) {
            intArray = new int[array.length];
            for (int i = 0; i < intArray.length; i++){
                intArray[i] = Integer.parseInt(array[i]);
            }
        }
        else {
            intArray = null;
        }
        return intArray;
    }
}
