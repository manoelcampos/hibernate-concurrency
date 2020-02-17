package com.manoelcampos.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Cliente extends PanacheEntity implements Serializable {
    public String nome;
    public String cpf;
    public char sexo;
    public String endereco;
    public String telefone;

    @Version
    public long versao;

    public static boolean update(Cliente cliente) {
        Cliente updated = cliente.merge();
        updated.flush();
        return true;
    }
}
