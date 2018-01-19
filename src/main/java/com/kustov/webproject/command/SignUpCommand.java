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
import java.util.Date;

public class SignUpCommand implements Command{
    private UserReceiver receiver;

    SignUpCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String pageMain = pageManager.getProperty("path_page_main");
        String thisPage = pageManager.getProperty("path_page_registration");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("firstName");
        String surname = request.getParameter("secondName");
        String birthday = request.getParameter("birthday");
        String countryName = request.getParameter("country");
        if (isAllValid(request, username, password, email, name, surname, birthday)){
            CountriesMap countriesMap = CountriesMap.getInstance();
            Country country = countriesMap.getValue(countryName);
            User user = new User(0, username, password, email, name, surname, getDateFromString(birthday),
                    country, 0, false, UserType.USER);//ID-????
            try {
                receiver.addUser(user);
                request.getSession().setAttribute("user", user);
            }catch (ServiceException exc){
                throw new CommandException();
            }
            page = pageMain;
        }else {
            page = thisPage;
        }
        return page;
    }

    private Date getDateFromString(String date){
        Date answer = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            answer = format.parse(date);
        }catch (ParseException ignored){
        }
        return answer;
    }

    private boolean isAllValid(HttpServletRequest request, String username, String password, String email, String name,
                               String surname, String birthday){
        SignUpValidator validator = new SignUpValidator();
        boolean isValid = true;
        if (!validator.checkUsername(username)) {
            request.setAttribute("errorUsername", "Username contains only letters, numbers and _ and starts with letter");
            isValid = false;
        }
        if (!validator.checkPassword(password)){
            request.setAttribute("errorPassword", "Password contains only letters, numbers and _");
            isValid = false;
        }
        if (!validator.checkEmail(email)){
            request.setAttribute("errorEmail", "Wrong email");
            isValid = false;
        }
        if (!validator.checkNameAndSurname(name, surname)){
            request.setAttribute("errorName", "Wrong name or surname");
            isValid = false;
        }
        if (!validator.checkDate(birthday)){
            request.setAttribute("errorBirthday", "Date needed to be in format yyyy-mm-dd");
            isValid = false;
        }
        return isValid;
    }
}
