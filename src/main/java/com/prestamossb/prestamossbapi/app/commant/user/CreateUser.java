package com.prestamossb.prestamossbapi.app.commant.user;

import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.domain.user.UserRepository;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CreateUser {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    final String EMAIL_EXISTS = "Email exits";
    public void create(User user){
       User userDb =  userRepository.findByEmail(user.getEmail()).orElse(new User());

        if (Objects.equals(userDb.getEmail(), user.getEmail())){
            throw  new DuplicateResourceException(EMAIL_EXISTS);
        }
        String passwordEncode = passwordEncoder.encode(user.getPassword());
        userRepository.save(user);
    }
}
