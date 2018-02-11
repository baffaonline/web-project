package com.kustov.webproject.logic;

import com.kustov.webproject.dao.*;
import com.kustov.webproject.entity.*;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.List;


/**
 * The Class FilmReceiver.
 */
public class FilmReceiver {

    /**
     * Find films.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Film> findFilms() throws ServiceException {
        FilmDAO dao = new FilmDAO();
        try {
            return dao.findAll();
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find film by id.
     *
     * @param id the id
     * @return the film
     * @throws ServiceException the service exception
     */
    public Film findFilmById(int id) throws ServiceException {
        FilmDAO dao = new FilmDAO();
        try {
            Film film = dao.findById(id);
            if (film == null) {
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
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Adds the film.
     *
     * @param filmTitle      the film title
     * @param filePath       the file path
     * @param description    the description
     * @param date           the date
     * @param countryId      the country id
     * @param ageRestriction the age restriction
     * @param genresId       the genres id
     * @param actorsId       the actors id
     * @return the film
     * @throws ServiceException the service exception
     */
    public Film addFilm(String filmTitle, String filePath, String description, LocalDate date, int countryId,
                        int ageRestriction, int genresId[], int actorsId[]) throws ServiceException {
        FilmDAO filmDAO = new FilmDAO();
        CountryDAO countryDAO = new CountryDAO();
        try {
            Country country = countryDAO.findById(countryId);
            Film film = new Film(0, filmTitle, country, description, ageRestriction, date, filePath,
                    0, null, null, null);
            int id = filmDAO.insert(film);
            film.setId(id);
            if (genresId != null && filmDAO.insertGenresToFilm(id, genresId)) {
                GenreDAO genreDAO = new GenreDAO();
                film.setGenres(genreDAO.findGenresByFilmId(id));
            }
            if (actorsId != null && filmDAO.insertActorsToFilm(id, actorsId)) {
                ActorDAO actorDAO = new ActorDAO();
                film.setActors(actorDAO.findActorsByFilmId(id));
            }
            return film;
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Edits the film.
     *
     * @param filmId         the film id
     * @param filmTitle      the film title
     * @param filePath       the file path
     * @param description    the description
     * @param date           the date
     * @param countryId      the country id
     * @param ageRestriction the age restriction
     * @param oldGenresId    the old genres id
     * @param oldActorsId    the old actors id
     * @param newGenresId    the new genres id
     * @param newActorsId    the new actors id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean editFilm(int filmId, String filmTitle, String filePath, String description, LocalDate date, int countryId,
                            int ageRestriction, int oldGenresId[], int oldActorsId[], int newGenresId[],
                            int newActorsId[]) throws ServiceException {
        FilmDAO filmDAO = new FilmDAO();
        CountryDAO countryDAO = new CountryDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            Country country = countryDAO.findById(countryId);
            Film oldFilm = findFilmById(filmId);
            Film film;
            if (!filePath.isEmpty()) {
                film = new Film(filmId, filmTitle, country, description, ageRestriction, date, filePath, 0,
                        null, null, null);
            } else {
                film = new Film(filmId, filmTitle, country, description, ageRestriction, date,
                        oldFilm.getPosterPath(), 0, null, null, null);
            }
            filmDAO.updateFilm(filmId, filmTitle, countryId, description, ageRestriction, date, film.getPosterPath());
            if (oldGenresId != null) {
                filmDAO.deleteGenresFromFilm(filmId, oldGenresId);
            }
            if (oldActorsId != null) {
                filmDAO.deleteActorsFromFilm(filmId, oldActorsId);
            }
            if (newGenresId != null && filmDAO.insertGenresToFilm(filmId, newGenresId)) {
                GenreDAO genreDAO = new GenreDAO();
                film.setGenres(genreDAO.findGenresByFilmId(filmId));
            }
            if (newActorsId != null && filmDAO.insertActorsToFilm(filmId, newActorsId)) {
                ActorDAO actorDAO = new ActorDAO();
                film.setActors(actorDAO.findActorsByFilmId(filmId));
            }
            film.setReviews(reviewDAO.findReviewsByFilmId(filmId));
            return !film.equals(oldFilm);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Find information for film.
     *
     * @return the pair
     * @throws ServiceException the service exception
     */
    public Pair<Film, List<Country>> findInformationForFilm() throws ServiceException {
        GenreDAO genreDAO = new GenreDAO();
        ActorDAO actorDAO = new ActorDAO();
        CountryDAO countryDAO = new CountryDAO();
        try {
            Film film = new Film();
            film.setGenres(genreDAO.findAll());
            film.setActors(actorDAO.findAll());
            List<Country> countries = countryDAO.findAll();
            return new Pair<>(film, countries);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Delete film.
     *
     * @param filmId the film id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean deleteFilm(int filmId) throws ServiceException {
        FilmDAO filmDAO = new FilmDAO();
        try {
            return filmDAO.deleteFilm(filmId);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }
}
