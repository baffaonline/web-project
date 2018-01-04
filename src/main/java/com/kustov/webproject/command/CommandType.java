package com.kustov.webproject.command;

import com.kustov.tagexample.logic.UserReceiver;

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
