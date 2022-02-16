package com.ifta.spring.service;

import com.ifta.spring.data.UserDetailsImpl;
import com.ifta.spring.model.Usuario;
import com.ifta.spring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optUser= repository.findByUsername(username);
        if (optUser.isEmpty()){
            throw new UsernameNotFoundException("Usuário "+username+" não foi encontrado");
        }
        return new UserDetailsImpl(optUser);
    }
}
