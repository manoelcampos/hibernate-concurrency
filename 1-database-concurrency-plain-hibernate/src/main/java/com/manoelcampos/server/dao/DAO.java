package com.manoelcampos.server.dao;

import com.manoelcampos.server.model.Cadastro;
import javax.persistence.TypedQuery;
import java.util.List;


public interface DAO<T extends Cadastro> {
    TypedQuery<T> createQuery(String jpql);
    T findById(long id);
    List<T> all();
    T findByField(String fieldName, Object value);
    boolean delete(T entity);
    long save(T entity);
}
