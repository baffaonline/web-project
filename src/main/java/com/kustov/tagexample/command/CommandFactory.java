package com.kustov.tagexample.command;

import com.kustov.tagexample.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandFactory {
    private final static Logger LOGGER = LogManager.getLogger();

    public static Optional<Command> defineCommand(String commandName) throws CommandException{
        Optional<Command> command = Optional.empty();
        try {
            if (commandName == null) {
                return command;
            } else {
                CommandType type = CommandType.valueOf(commandName.toUpperCase());
                command = Optional.of(type.getCommand());
            }
        }catch (IllegalArgumentException exc){
            LOGGER.log(Level.ERROR, exc.getMessage());
            throw new CommandException(exc);
        }
        return command;
    }
}
