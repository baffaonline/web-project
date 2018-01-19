package com.kustov.webproject.logic;

import com.kustov.webproject.dao.FilmDAO;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.List;

public class FilmReceiver extends DefaultReceiver{
    public List<Film> findFilms() throws ServiceException{
        FilmDAO dao = new FilmDAO();
        try{
            return dao.findAll();
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Film findFilmById(int id) throws ServiceException{
        FilmDAO dao = new FilmDAO();
        try{
            return dao.findById(id);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
