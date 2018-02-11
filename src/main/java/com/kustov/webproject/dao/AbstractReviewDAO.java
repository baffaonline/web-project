package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.DAOException;

import java.util.List;


/**
 * The Class AbstractReviewDAO.
 */

public abstract class AbstractReviewDAO {
    /**
     * Insert.
     *
     * @param entity the entity
     * @return true, if successful
     * @throws DAOException the DAO exception
     */
    public abstract boolean insert(Review entity) throws DAOException;

    /**
     * Find reviews by film id.
     *
     * @param id the id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public abstract List<Review> findReviewsByFilmId(int id) throws DAOException;
}
