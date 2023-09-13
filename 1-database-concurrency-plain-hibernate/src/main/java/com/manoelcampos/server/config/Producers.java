package com.manoelcampos.server.config;

import com.manoelcampos.server.dao.DAO;
import com.manoelcampos.server.dao.JpaDAO;
import com.manoelcampos.server.model.Cadastro;
import java.lang.reflect.ParameterizedType;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class Producers {
    @PersistenceContext
    EntityManager em;
    
    @Produces
    public <T extends Cadastro> DAO<T> getDao(InjectionPoint ip){
        ParameterizedType t = (ParameterizedType) ip.getType();
        Class<T> classe = (Class<T>) t.getActualTypeArguments()[0];
        return new JpaDAO<>(em, classe);
    }        
}
