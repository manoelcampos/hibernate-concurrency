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
        Cliente existente = Cliente.findById(cliente.id);
        if(existente != null){
            existente.nome = cliente.nome;
            existente.cpf = cliente.cpf;
            existente.sexo = cliente.sexo;
            existente.endereco = cliente.endereco;
            existente.telefone = cliente.telefone;
            existente.persist();
            return true;
        }

        return false;
    }
}
