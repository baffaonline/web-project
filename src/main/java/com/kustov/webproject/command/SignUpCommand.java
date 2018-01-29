package com.kustov.webproject.command;

import com.kustov.webproject.entity.CountriesMap;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.User;
import com.kustov.webproject.entity.UserType;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.UserReceiver;
import com.kustov.webproject.service.PropertyManager;
import com.kustov.webproject.validator.SignUpValidator;

import javax.servlet.http.HttpServletRequest;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SignUpCommand implements Command {
    private UserReceiver receiver;

    SignUpCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String pageMain = pageManager.getProperty("path_page_default");
        String thisPage = pageManager.getProperty("path_page_registration");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("firstName");
        String surname = request.getParameter("secondName");
        String birthday = request.getParameter("birthday");
        String countryName = request.getParameter("country");
        if (isAllValid(request, username, password, email, name, surname, birthday)) {
            try {
                if (!isNotDuplicatePasswordOrEmail(request, username, email)) {
                    return thisPage;
                }
                CountriesMap countriesMap = CountriesMap.getInstance();
                Country country = countriesMap.getValue(countryName);
                User user = new User(0, username, password, email, name, surname, getDateFromString(birthday),
                        country, 0, false, UserType.USER);
                int id = receiver.addUser(user);
                user.setId(id);
                request.getSession(true).setAttribute("user", user);
            } catch (ServiceException exc) {
                throw new CommandException();
            }
            page = pageMain;
        } else {
            page = thisPage;
        }
        return page;
    }

    private LocalDate getDateFromString(String date) {
        Date dateFromString;
        LocalDate localDate = LocalDate.MIN;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateFromString = format.parse(date);
            localDate = dateFromString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ignored) {
        }
        return localDate;
    }

    private boolean isNotDuplicatePasswordOrEmail(HttpServletRequest request,
                                                  String username, String email) throws ServiceException {
        boolean isNotDuplicate = true;
        if (receiver.findUserByUsername(username) != null) {
            isNotDuplicate = false;
            request.setAttribute("errorUsername", "This username is already taken");
        }
        if (receiver.findUserByEmail(email) != null) {
            isNotDuplicate = false;
            request.setAttribute("errorEmail", "This email is already taken");
        }
        return isNotDuplicate;
    }

    private boolean isAllValid(HttpServletRequest request, String username, String password, String email, String name,
                               String surname, String birthday) {
        SignUpValidator validator = new SignUpValidator();
        boolean isValid = true;
        if (!validator.checkUsername(username)) {
            request.setAttribute("errorUsername", "Username contains only letters, numbers and _ and starts with letter");
            isValid = false;
        }
        if (!validator.checkPassword(password)) {
            request.setAttribute("errorPassword", "Password contains only letters, numbers and _");
            isValid = false;
        }
        if (!validator.checkEmail(email)) {
            request.setAttribute("errorEmail", "Wrong email");
            isValid = false;
        }
        if (!validator.checkNameAndSurname(name, surname)) {
            request.setAttribute("errorName", "Wrong name or surname");
            isValid = false;
        }
        if (!validator.checkDate(birthday)) {
            request.setAttribute("errorBirthday", "Date needed to be in format yyyy-mm-dd");
            isValid = false;
        }
        return isValid;
    }
}
