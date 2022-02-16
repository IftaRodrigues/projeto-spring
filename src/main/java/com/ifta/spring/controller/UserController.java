package com.ifta.spring.controller;

import com.ifta.spring.model.Usuario;
import com.ifta.spring.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/listAll")
    public ResponseEntity<List<Usuario>> listAll(){
        return ResponseEntity.ok(repository.findAll());
    }
}
