package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
}
