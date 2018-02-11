package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;

import javax.servlet.http.HttpServletRequest;


/**
 * The Interface Command.
 */
public interface Command {

    /**
     * Execute.
     *
     * @param request the request
     * @return the command pair
     * @throws CommandException the command exception
     */
    CommandPair execute(HttpServletRequest request) throws CommandException;
}
