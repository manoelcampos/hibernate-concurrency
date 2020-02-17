package com.manoelcampos.server.dao;

import com.manoelcampos.server.model.Cadastro;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaDAO<T extends Cadastro> implements DAO<T> {
    private final EntityManager em;
    private final Class<T> classe;
    
    public JpaDAO(EntityManager em, Class<T> classe){
        this.em = em;
        this.classe = classe;
    }

    @Override
    public T findById(long id) {
        return em.find(classe, id);
    }

    @Override
    public boolean delete(T entity) {
        em.remove(entity);
        return true;
    }
    
    @Override
    public long save(T entity) {
        if(entity.getId() > 0)
            em.merge(entity);
        else em.persist(entity);
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
