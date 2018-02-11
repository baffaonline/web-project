package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.dao.UserDAO;
import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;


/**
 * The Class ReviewReceiver.
 */
public class ReviewReceiver {

    /**
     * Insert review.
     *
     * @param review the review
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertReview(Review review) throws ServiceException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.insert(review);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Insert review rating.
     *
     * @param filmId       the film id
     * @param userReviewId the user review id
     * @param userId       the user id
     * @param rating       the rating
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean insertReviewRating(int filmId, int userReviewId, int userId, int rating) throws ServiceException {
        ReviewDAO reviewDAO = new ReviewDAO();
        UserDAO userDAO = new UserDAO();
        try {
            return reviewDAO.insertUserRating(filmId, userReviewId, userId, rating)
                    && userDAO.updateRating(userReviewId, rating);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }

    /**
     * Delete review.
     *
     * @param filmId the film id
     * @param userId the user id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean deleteReview(int filmId, int userId) throws ServiceException {
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.deleteReview(filmId, userId);
        } catch (DAOException exc) {
            throw new ServiceException(exc);
        }
    }
}
