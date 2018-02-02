package com.kustov.webproject.command;

import com.kustov.webproject.entity.CountriesMap;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.exception.ServiceException;
import com.kustov.webproject.logic.CountryReceiver;
import com.kustov.webproject.service.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CountrySetupCommand implements Command {
    private CountryReceiver receiver;

    CountrySetupCommand(CountryReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        String registrationPage = pageManager.getProperty("path_page_registration");
        String adminPage = pageManager.getProperty("path_page_admin_add_film");
        try {
            List<Country> countries = receiver.findCountries();
            CountriesMap countriesMap = CountriesMap.getInstance();
            for (Country country : countries) {
                countriesMap.put(country.getName(), country);
            }
            request.getSession(true).setAttribute("countries", countries);
            String option = request.getParameter("page");
            if ("registration".equals(option)) {
                page = registrationPage;
            } else {
                page = adminPage;
            }
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return page;
    }
}
