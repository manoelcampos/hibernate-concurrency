package com.manoelcampos.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import java.io.Serializable;
import jakarta.persistence.*;

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
        final var found = (Cliente)Cliente.findById(cliente.id);
        found.nome = cliente.nome;
        found.cpf = cliente.cpf;
        found.sexo = cliente.sexo;
        found.endereco = cliente.endereco;
        found.telefone = cliente.telefone;
        found.versao = cliente.versao;
        found.persistAndFlush();
        return true;
    }
}
