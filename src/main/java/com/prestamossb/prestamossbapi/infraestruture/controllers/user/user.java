package com.prestamossb.prestamossbapi.infraestruture.controllers.user;

import com.prestamossb.prestamossbapi.app.commant.user.CreateUser;
import com.prestamossb.prestamossbapi.app.query.user.FindUser;
import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class user {



    private  final FindUser findUser;
    private final CreateUser createUser;



    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable UUID id){
        User user =  findUser.findbyId(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseText> create(@RequestBody User user){
        createUser.create(user);
        return new ResponseEntity<>(ResponseText.CREATED,HttpStatus.CREATED);

    }
}
