package com.kustov.tagexample.command;

import com.kustov.tagexample.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request) throws CommandException;
}
