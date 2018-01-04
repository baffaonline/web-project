package com.kustov.tagexample.dao;

import com.kustov.tagexample.entity.Entity;
import com.kustov.tagexample.exception.DAOException;

import java.util.List;

public abstract class AbstractDAO<K, T extends Entity> {
    public abstract List<T> findAll() throws DAOException;
}
