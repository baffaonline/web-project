package com.kustov.webproject.command;

import com.kustov.webproject.logic.*;

public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiver())),
    COUNTRY_SETUP(new CountrySetupCommand(new CountryReceiver())),
    SIGN_UP(new SignUpCommand(new UserReceiver())),
    LOGOUT(new LogoutCommand()),
    FILM_TOP(new FilmTopCommand(new FilmReceiver())),
    FILM(new FilmCommand(new FilmReceiver())),
    ACTOR(new ActorCommand(new ActorReceiver())),
    REVIEW(new ReviewCommand(new ReviewReceiver())),
    REVIEW_RATING(new ReviewRatingCommand(new ReviewReceiver())),
    LOCALIZATION(new LocalizationCommand()),
    USER_LIST(new UserListCommand(new UserReceiver())),
    USER_INFORMATION(new UserInformationCommand(new UserReceiver())),
    REVIEW_DELETE(new ReviewDeleteCommand(new ReviewReceiver())),
    BAN(new BanCommand(new UserReceiver()));

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
