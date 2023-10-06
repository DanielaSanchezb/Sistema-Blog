package com.sistema.blog.controller;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegisterDTO;
import com.sistema.blog.entities.Rol;
import com.sistema.blog.entities.UserBlog;
import com.sistema.blog.repository.RolRepository;
import com.sistema.blog.repository.UserRepository;
import com.sistema.blog.security.JWTAuthResonseDTO;
import com.sistema.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // obtenemos el token del jwtTokenProvider
        String token =jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResonseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO){
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("username already exists", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("user email already exists", HttpStatus.BAD_REQUEST);
        }

        UserBlog userBlog = new UserBlog();
        userBlog.setName(registerDTO.getName());
        userBlog.setUsername(registerDTO.getUsername());
        userBlog.setEmail(registerDTO.getEmail());
        userBlog.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Rol roles = rolRepository.findByName("ROLE_ADMIN").get();
        userBlog.setRoles(Collections.singleton(roles));

        userRepository.save(userBlog);
        return new ResponseEntity<>("registered user successfully", HttpStatus.OK);
    }
}
