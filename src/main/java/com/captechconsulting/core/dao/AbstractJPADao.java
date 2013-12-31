package com.captechconsulting.core.dao;

import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public class AbstractJPADao<T> implements Dao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;

    public AbstractJPADao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T findById(long id) throws DataAccessException {
        final T t = entityManager.find(clazz, id);
        if (t != null) {
            return t;
        }
        throw new EntityNotFoundException("No ["+clazz.getCanonicalName()+"] with id ["+id+"]");
    }

    @Override
    public List<T> findAll(int page, int size) throws DataAccessException {
        TypedQuery<T> query = entityManager.createQuery("FROM "+clazz.getName(), clazz);
        addPagination(query, page, size);
        return query.getResultList();
    }

    @Override
    public T create(@Valid T entity) throws DataAccessException {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public void create(Collection<T> entities) throws DataAccessException {
        for (T entity : entities) {
            create(entity);
        }
    }

    @Override
    public T update(@Valid T entity) throws DataAccessException {
        return getEntityManager().merge(entity);
    }

    @Override
    public void update(Collection<T> entities) throws DataAccessException {
        for (T entity : entities) {
            update(entity);
        }
    }

    @Override
    public void delete(T entity) throws DataAccessException {
        getEntityManager().remove(entity);
    }

    @Override
    public void delete(Collection<T> entities) throws DataAccessException {
        for (T entity : entities) {
            delete(entity);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    protected void addPagination(TypedQuery query, int page, int size) {
        if (size > 0) {
            int firstResult = page * size;
            query.setFirstResult(firstResult);
            query.setMaxResults(size);
        }
    }

}
