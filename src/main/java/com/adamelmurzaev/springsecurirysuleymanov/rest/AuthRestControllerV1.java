package com.adamelmurzaev.springsecurirysuleymanov.rest;

import com.adamelmurzaev.springsecurirysuleymanov.model.User;
import com.adamelmurzaev.springsecurirysuleymanov.repository.UserRepository;
import com.adamelmurzaev.springsecurirysuleymanov.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthRestControllerV1(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getEmail()
            , authenticationRequestDTO.getPassword()));
            User user = userRepository.findByEmail(authenticationRequestDTO.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User doesnt exist")
            );
            String token = jwtTokenProvider.createToken(authenticationRequestDTO.getEmail(), user.getRole().name());
            Map<Object, Object> map = new HashMap<>();
            map.put("email", authenticationRequestDTO.getEmail());
            map.put("token", token);
            return ResponseEntity.ok(map);
        }catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler handler = new SecurityContextLogoutHandler();
        handler.logout(request, response,  null);
    }
}
