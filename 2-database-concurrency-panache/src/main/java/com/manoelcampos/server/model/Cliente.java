package com.manoelcampos.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

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
        Cliente existing = Cliente.findById(cliente.id);
        if(existing != null){
            existing.nome = cliente.nome;
            existing.cpf = cliente.cpf;
            existing.sexo = cliente.sexo;
            existing.endereco = cliente.endereco;
            existing.telefone = cliente.telefone;
            existing.versao = cliente.versao;
            existing.persist();
            return true;
        }

        return false;
    }
}
