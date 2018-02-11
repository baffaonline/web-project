package com.kustov.webproject.dao;

import com.kustov.webproject.exception.DAOException;
import com.kustov.webproject.pool.DBConnectionPool;
import com.kustov.webproject.pool.ProxyConnection;

import java.sql.Statement;
import java.util.List;

public interface AbstractDAO<K, T> {

    List<T> findAll() throws DAOException;

    K insert(T entity) throws DAOException;

    T findById(K id) throws DAOException;

    void close(Statement statement, DBConnectionPool connectionPool,
                      ProxyConnection connection);
}
