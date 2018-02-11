package com.kustov.webproject.dao;

import com.kustov.webproject.exception.DAOException;

import java.util.List;


/**
 * The Interface AbstractDAO.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */

public interface AbstractDAO<K, T> {

    /**
     * Find all.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<T> findAll() throws DAOException;

    /**
     * Insert.
     *
     * @param entity the entity
     * @return the k
     * @throws DAOException the DAO exception
     */
    K insert(T entity) throws DAOException;

    /**
     * Find by id.
     *
     * @param id the id
     * @return the t
     * @throws DAOException the DAO exception
     */
    T findById(K id) throws DAOException;
}
