package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;
import com.kustov.webproject.logic.FilmReceiver;

import javax.servlet.http.HttpServletRequest;

public class AddFilmCommand implements Command{
    private FilmReceiver receiver;

    AddFilmCommand(FilmReceiver receiver){
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
