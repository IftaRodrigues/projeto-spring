package com.ifta.spring.service.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class AlunoDtoIn {
    @NotBlank
    private String nome;
    @NotBlank
    @CPF
    private String cpf;
    private String dataNascimento;
}
