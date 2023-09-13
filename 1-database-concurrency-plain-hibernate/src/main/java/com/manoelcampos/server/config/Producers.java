package com.manoelcampos.server.config;

import com.manoelcampos.server.dao.DAO;
import com.manoelcampos.server.dao.JpaDAO;
import com.manoelcampos.server.model.Cadastro;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Producers {
    @Produces
    @PersistenceContext
    EntityManager em;
    
    @Produces
    public <T extends Cadastro> DAO<T> getDao(InjectionPoint ip){
        ParameterizedType t = (ParameterizedType) ip.getType();
        Class<T> classe = (Class<T>) t.getActualTypeArguments()[0];
        return new JpaDAO<>(em, classe);
    }        
}
