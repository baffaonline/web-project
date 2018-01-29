package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ActorDAO;
import com.kustov.webproject.dao.FilmDAO;
import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.Comparator;
import java.util.List;

public class ActorReceiver {
    public Actor findActorById(int id) throws ServiceException{
        ActorDAO actorDAO = new ActorDAO();
        try{
            Actor actor = actorDAO.findById(id);
            if (actor == null){
                return null;
            }
            FilmDAO filmDAO = new FilmDAO();
            List<Film> films = filmDAO.findFilmsByActorId(id);
            films.sort(Comparator.comparing(Film::getReleaseDate).reversed());
            actor.setFilms(films);
            return actor;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
