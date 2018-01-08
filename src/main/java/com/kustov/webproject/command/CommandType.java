package com.kustov.webproject.command;

import com.kustov.webproject.logic.UserReceiver;

public enum CommandType {
    LOGIN(new LoginCommand(new UserReceiver()));

    private Command command;

    CommandType(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
