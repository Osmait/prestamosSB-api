package com.prestamossb.prestamossbapi.app.query.user;

import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.domain.user.UserRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.user.UserResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUser {


    private final UserRepository userRepository;

    public UserResponse findById(UUID id){
         User user =  userRepository.findById(id).orElseThrow(()-> new NotFoundException("Error Find by id"));
         return new UserResponse(
                 user.getId(),
                 user.getName(),
                 user.getLastName(),
                 user.getEmail(),
                 user.getPhone(),
                 user.getAddress(),
                 user.getCreateAt(),
                 user.getUpdateAt()
                 );
    }

    public User findByEmail(String email){
        return  userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("No fond by email"+ email));
    }
}
