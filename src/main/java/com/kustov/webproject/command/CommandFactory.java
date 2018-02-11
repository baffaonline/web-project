package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


/**
 * A factory for creating Command objects.
 */
public class CommandFactory {

    /**
     * The Constant LOGGER.
     */
    private final static Logger LOGGER = LogManager.getLogger();

    /**
     * Define command.
     *
     * @param commandName the command name
     * @return the optional
     * @throws CommandException the command exception
     */
    public static Optional<Command> defineCommand(String commandName) throws CommandException {
        Optional<Command> command = Optional.empty();
        try {
            if (commandName == null) {
                return command;
            } else {
                CommandType type = CommandType.valueOf(commandName.toUpperCase());
                command = Optional.of(type.getCommand());
            }
        } catch (IllegalArgumentException exc) {
            LOGGER.log(Level.ERROR, exc.getMessage());
            throw new CommandException(exc);
        }
        return command;
    }
}
