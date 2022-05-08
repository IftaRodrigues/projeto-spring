package com.ifta.spring.service;

import com.ifta.spring.model.Aluno;
import com.ifta.spring.repository.AlunoRepository;
import com.ifta.spring.service.request.AlunoDtoIn;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {
    private AlunoRepository alunoRepository;
    private ModelMapper modelMapper;

    public ResponseEntity<?> cadastrar (AlunoDtoIn alunoDtoIn){
        Aluno aluno = alunoRepository.saveAndFlush(modelMapper.map(alunoDtoIn, Aluno.class));
        return ResponseEntity.accepted().body(aluno);
    }
}