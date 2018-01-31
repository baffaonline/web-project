package com.kustov.webproject.logic;

import com.kustov.webproject.dao.ReviewDAO;
import com.kustov.webproject.entity.Review;
import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.exception.ServiceException;

public class ReviewReceiver {
    public boolean insertReview(Review review) throws ServiceException{
        ReviewDAO reviewDAO = new ReviewDAO();
        try{
            return reviewDAO.insert(review);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }

    public boolean insertReviewRating(int filmId, int userReviewId, int userId, int rating) throws ServiceException{
        ReviewDAO reviewDAO = new ReviewDAO();
        try {
            return reviewDAO.insertUserRating(filmId, userReviewId, userId, rating);
        }catch (DAOException exc){
            throw new ServiceException(exc);
        }
    }
}
