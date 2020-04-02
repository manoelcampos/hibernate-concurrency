package com.manoelcampos.server.dao;

import com.manoelcampos.server.model.Cadastro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaDAO<T extends Cadastro> implements DAO<T> {
    private final EntityManager em;
    private final Class<T> classe;
    private final static Logger LOGGER= LoggerFactory.getLogger(JpaDAO.class);
    public JpaDAO(EntityManager em, Class<T> classe){
        this.em = em;
        this.classe = classe;
    }

    @Override
    public T findById(long id) {
        LOGGER.info("findById id:{}",id);

        final T entity = em.find(classe, id);
        LOGGER.info("entity {}",entity);
        return entity;
    }

    @Override
    public boolean delete(T entity) {
        em.remove(entity);
        return true;
    }

    @Override
    public long save(T entity) {
        LOGGER.info("SAVE entity {}",entity);

        if(entity.getId() > 0){
            T m = em.merge(entity);
            LOGGER.info("entity {}",entity);
            LOGGER.info("merged {}",m);

        }
        else {
            em.persist(entity);
            LOGGER.info("entity {}",entity);
        }
        em.flush();

        return entity.getId();
    }

    @Override
    public List<T> all() {
        TypedQuery<T> query = em.createQuery("select o from " + classe.getSimpleName() + " o", classe);
        return query.getResultList();
    }

    @Override
    public T findByField(String fieldName, Object value) {
        final String jpql = " select o from " + classe.getSimpleName() + " o " +
                            " where o." + fieldName + " = :" + fieldName;
        TypedQuery<T> query = em.createQuery(jpql, classe);
        query.setParameter(fieldName, value);
        return query.getSingleResult();
    }

    @Override
    public TypedQuery<T> createQuery(String jpql) {
        return em.createQuery(jpql, classe);
    }
}
