package com.prestamossb.prestamossbapi.app.query.user;

import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.domain.user.UserRepository;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUser {


    private final UserRepository userRepository;

    public User findbyId(UUID id){
         return userRepository.findById(id).orElseThrow(()-> new NotFoundException("Error Find by id"));
    }

    public User findByEmail(String email){
        return  userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("No fond by email"+ email));
    }
}
