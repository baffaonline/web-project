package com.kustov.webproject.dao;

import com.kustov.webproject.entity.Entity;
import com.kustov.webproject.exception.DAOException;

import java.util.List;

public abstract class AbstractFindDAO<T> {
    abstract List<T> findAll() throws DAOException;
}
