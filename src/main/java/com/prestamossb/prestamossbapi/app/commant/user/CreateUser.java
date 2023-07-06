package com.prestamossb.prestamossbapi.app.commant.user;

import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.domain.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUser {



    private final UserRepository userRepository;


    public void create(User user){
        userRepository.save(user);
    }
}
