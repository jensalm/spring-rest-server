package com.captechconsulting.core.dao;

import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

public interface Dao<T> {

    T findById(long id) throws DataAccessException;

    List<T> findAll(int page, int size) throws DataAccessException;

    T create(T t) throws DataAccessException;

    void create(Collection<T> t) throws DataAccessException;

    T update(T t) throws DataAccessException;

    void update(Collection<T> t) throws DataAccessException;

    void delete(T t) throws DataAccessException;

    void delete(Collection<T> t) throws DataAccessException;

}