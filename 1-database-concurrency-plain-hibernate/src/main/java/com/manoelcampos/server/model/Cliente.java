package com.manoelcampos.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.build.ToStringPlugin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
@ToString
@Getter
@Setter
public class Cliente implements Cadastro, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long versao;

    private String nome;
    private String cpf;
    private char sexo;
    private String endereco;
    private String telefone;

}
