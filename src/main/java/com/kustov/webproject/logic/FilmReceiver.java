package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ActorDAO;
import com.kustov.webproject.dao.FilmDAO;
import com.kustov.webproject.dao.GenreDAO;
import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.entity.Actor;
import com.kustov.webproject.entity.Film;
import com.kustov.webproject.entity.Genre;
import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

import java.util.List;

public class FilmReceiver{
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
            Film film = dao.findById(id);
            if (film == null){
                return null;
            }
            ActorDAO actorDAO = new ActorDAO();
            List<Actor> actors = actorDAO.findActorsByFilmId(film.getId());
            film.setActors(actors);
            GenreDAO genreDAO = new GenreDAO();
            List<Genre> genres = genreDAO.findGenresByFilmId(film.getId());
            film.setGenres(genres);
            ReviewDAO reviewDAO = new ReviewDAO();
            List<Review> reviews = reviewDAO.findReviewsByFilmId(film.getId());
            film.setReviews(reviews);
            return film;
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
