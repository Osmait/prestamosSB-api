package com.prestamossb.prestamossbapi.infraestruture.controllers.user;

import com.prestamossb.prestamossbapi.app.commant.user.CreateUser;
import com.prestamossb.prestamossbapi.app.query.user.FindUser;
import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.infraestruture.Dto.user.UserRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.user.UserResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
//@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class user {

    private  final FindUser findUser;
    private final CreateUser createUser;

    @GetMapping("/api/v1/public/user/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id){
        UserResponse user =  findUser.findById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/api/v1/public/user")
    public ResponseEntity<ResponseText> create(@RequestBody UserRequest user){
        createUser.create(user);
        return new ResponseEntity<>(ResponseText.CREATED,HttpStatus.CREATED);
    }
    @GetMapping("api/v1/profile")
    public ResponseEntity<User> getUserById(){
        return new ResponseEntity<>(findUser.findProfile(),HttpStatus.OK);
    }
}
