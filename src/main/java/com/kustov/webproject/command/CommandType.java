package com.kustov.webproject.command;

import com.kustov.webproject.logic.*;


/**
 * The Enum CommandType.
 */
public enum CommandType {

    /**
     * The prepare login.
     */
    PREPARE_LOGIN(new PrepareLoginCommand()),

    /**
     * The login.
     */
    LOGIN(new LoginCommand(new UserReceiver())),

    /**
     * The country setup.
     */
    COUNTRY_SETUP(new CountrySetupCommand(new CountryReceiver())),

    /**
     * The sign up.
     */
    SIGN_UP(new SignUpCommand(new UserReceiver())),

    /**
     * The logout.
     */
    LOGOUT(new LogoutCommand()),

    /**
     * The film top.
     */
    FILM_TOP(new FilmTopCommand(new FilmReceiver())),

    /**
     * The film.
     */
    FILM(new FilmCommand(new FilmReceiver())),

    /**
     * The actor.
     */
    ACTOR(new ActorCommand(new ActorReceiver())),

    /**
     * The review.
     */
    REVIEW(new ReviewCommand(new ReviewReceiver())),

    /**
     * The review rating.
     */
    REVIEW_RATING(new ReviewRatingCommand(new ReviewReceiver())),

    /**
     * The localization.
     */
    LOCALIZATION(new LocalizationCommand()),

    /**
     * The user list.
     */
    USER_LIST(new UserListCommand(new UserReceiver())),

    /**
     * The user information.
     */
    USER_INFORMATION(new UserInformationCommand(new UserReceiver())),

    /**
     * The review delete.
     */
    REVIEW_DELETE(new ReviewDeleteCommand(new ReviewReceiver())),

    /**
     * The ban.
     */
    BAN(new BanCommand(new UserReceiver())),

    /**
     * The film setup.
     */
    FILM_SETUP(new FilmSetupCommand(new FilmReceiver())),

    /**
     * The add film.
     */
    ADD_FILM(new AddFilmCommand(new FilmReceiver())),

    /**
     * The edit film setup.
     */
    EDIT_FILM_SETUP(new EditFilmSetupCommand(new FilmReceiver())),

    /**
     * The edit film.
     */
    EDIT_FILM(new FilmEditCommand(new FilmReceiver())),

    /**
     * The actor setup.
     */
    ACTOR_SETUP(new ActorSetupCommand(new ActorReceiver())),

    /**
     * The add actor.
     */
    ADD_ACTOR(new AddActorCommand(new ActorReceiver())),

    /**
     * The delete film.
     */
    DELETE_FILM(new DeleteFilmCommand(new FilmReceiver()));

    /**
     * The command.
     */
    private Command command;

    /**
     * Instantiates a new command type.
     *
     * @param command the command
     */
    CommandType(Command command) {
        this.command = command;
    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
