package com.ifta.spring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifta.spring.data.UserDetailsImpl;
import com.ifta.spring.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public final AuthenticationManager authenticationManager;
    //tempo de expiração do token é de 30 minutos
    public static final int TOKEN_EXPIRATION=1800000;
    //senha de geracao de token, esta deve ser colocada na configuracao do projeto ou nao deixar exposta
    public static final String TOKEN_PASSWORD= "676de61a-29c1-42c5-8cbb-54f586d331c7";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Usuario usuario= new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    //lista para tratar permissoes
                    new ArrayList<>()

            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar o usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserDetailsImpl userDetails= (UserDetailsImpl) authResult.getPrincipal();
        String token= JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC256(TOKEN_PASSWORD));

        response.getWriter().write(token);
        response.getWriter().flush();

    }
}
