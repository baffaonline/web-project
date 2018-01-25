package com.kustov.webproject.command;

import com.kustov.webproject.logic.CountryReceiver;
import com.kustov.webproject.logic.FilmReceiver;
import com.kustov.webproject.logic.UserReceiver;

public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiver())),
    REGISTRATION_SETUP(new RegistrationSetupCommand(new CountryReceiver())),
    SIGN_UP(new SignUpCommand(new UserReceiver())),
    FILM_TOP(new FilmTopCommand(new FilmReceiver())),
    FILM(new FilmCommand(new FilmReceiver())),
    LOGOUT(new LogoutCommand());

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
