package com.kustov.webproject.command;

import com.kustov.webproject.logic.CountryReceiver;
import com.kustov.webproject.logic.UserReceiver;

public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiver())),
    REGISTRATION_SETUP(new RegistrationSetupCommand(new CountryReceiver()));

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
