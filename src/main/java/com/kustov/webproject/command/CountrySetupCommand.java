package com.kustov.webproject.command;

import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.User;
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
    public CommandPair execute(HttpServletRequest request) throws CommandException {
        String page;
        PropertyManager pageManager = new PropertyManager("pages");
        User thisUser = (User)request.getSession().getAttribute("user");
        if (!PageConstant.GUEST_STRING.equals(thisUser.getType().getTypeName())){
            return new CommandPair(CommandPair.DispatchType.REDIRECT,
                    pageManager.getProperty("path_page_default"));
        }
        String registrationPage = pageManager.getProperty("path_page_registration");
        try {
            List<Country> countries = receiver.findCountries();
            request.getSession(true).setAttribute("countries", countries);
            request.setAttribute("isWrongInput", false);
            page = registrationPage;
        } catch (ServiceException exc) {
            throw new CommandException(exc);
        }
        return new CommandPair(CommandPair.DispatchType.FORWARD, page);
    }
}
