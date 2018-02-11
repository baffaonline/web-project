package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Entity;
import com.kustov.webproject.exception.DAOException;

import java.util.List;


/**
 * The Class AbstractEntityDAO.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public abstract class AbstractEntityDAO<K, T extends Entity> implements AbstractDAO<K, T> {

    public abstract List<T> findAll() throws DAOException;

    public abstract K insert(T entity) throws DAOException;

    public abstract T findById(K id) throws DAOException;
}
