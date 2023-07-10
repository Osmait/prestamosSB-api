package com.prestamossb.prestamossbapi.app.Auth;

import com.prestamossb.prestamossbapi.domain.user.User;
import com.prestamossb.prestamossbapi.infraestruture.config.JWT.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtService;




    public AuthResponse login(AuthRequest request){


        Authentication authentication =   authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                request.email(),
                request.password()
        ));

        User user = (User)authentication.getPrincipal();
        String token = jwtService.issueToken(user.getEmail(),user.getAuthorities().toString());

        return new AuthResponse(token);


    }


    public  User getIdCurrentLoggedUser(){
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()){
            return  null;
        }
        return ((User) auth.getPrincipal());
    }
}
