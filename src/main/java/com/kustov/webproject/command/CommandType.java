package com.kustov.webproject.command;

import com.kustov.webproject.logic.*;

public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiver())),
    REGISTRATION_SETUP(new RegistrationSetupCommand(new CountryReceiver())),
    SIGN_UP(new SignUpCommand(new UserReceiver())),
    LOGOUT(new LogoutCommand()),
    FILM_TOP(new FilmTopCommand(new FilmReceiver())),
    FILM(new FilmCommand(new FilmReceiver())),
    ACTOR(new ActorCommand(new ActorReceiver())),
    REVIEW(new ReviewCommand(new ReviewReceiver()));

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
