package com.kustov.webproject.command;

import com.kustov.webproject.dao.CountryDAO;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.CountryReceiver;
import com.kustov.webproject.logic.UserReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationSetupCommand implements Command {
    private CountryReceiver receiver;

    public RegistrationSetupCommand(CountryReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        ResourceBundle resourceBundle = ResourceBundle.getBundle("pages");
        String registrationPage = resourceBundle.getString("path_page_registration");
        try {
            List<String> countries = receiver.findCountries();
            request.getSession().setAttribute("countries", countries);
            page = registrationPage;
        } catch (ServiceException exc){
            throw new CommandException(exc);
        }
        return page;
    }

    public CountryReceiver getReceiver() {
        return receiver;
    }
}
