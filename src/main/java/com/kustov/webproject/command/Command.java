package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * The Interface Command.
 */
public interface Command {

    CommandPair execute(HttpServletRequest request) throws CommandException;
}
