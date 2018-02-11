package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ActorDAO;
import com.kustov.webproject.dao.CountryDAO;
import com.kustov.webproject.dao.FilmDAO;
import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Country;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import javafx.util.Pair;

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
            if (films != null) {
                films.sort(Comparator.comparing(Film::getReleaseDate).reversed());
                actor.setFilms(films);
            }
            return actor;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Actor addActor(String name, String surname, int countryId, String imagePath, int filmsId[])
            throws ServiceException{
        ActorDAO actorDAO = new ActorDAO();
        CountryDAO countryDAO = new CountryDAO();
        FilmDAO filmDAO = new FilmDAO();
        try{
            Country country = countryDAO.findById(countryId);
            Actor actor = new Actor(0, name, surname, imagePath, country, null);
            actor.setId(actorDAO.insert(actor));
            if (filmsId != null && actorDAO.insertFilmsToActor(actor.getId(), filmsId)){
                actor.setFilms(filmDAO.findFilmsByActorId(actor.getId()));
            }
            return actor;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public Pair<List<Film>, List<Country>> findInformationForActor() throws ServiceException{
        CountryDAO countryDAO = new CountryDAO();
        FilmDAO filmDAO = new FilmDAO();
        try{
            List<Film> films = filmDAO.findAll();
            List<Country> countries = countryDAO.findAll();
            return new Pair<>(films, countries);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
