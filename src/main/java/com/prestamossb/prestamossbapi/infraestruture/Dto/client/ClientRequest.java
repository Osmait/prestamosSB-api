package com.prestamossb.prestamossbapi.infraestruture.Dto.client;

import com.prestamossb.prestamossbapi.domain.client.Client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientRequest {


    private UUID id;

    @NotNull(message = "Name is require")
    @Size(min = 1,max = 50,message = "Name is require")
    private String name;

    @NotNull(message = "LastName is require")
    @Size(min = 1,max = 50, message ="LastName is require" )
    private String lastName;

    @Email(message = "the field not is a email")
    @NotNull(message = "Email is require")
    @Size(min = 1,max = 50,message = "Email is require")
    private String email;


    private String phone;

    private String address;

    public Client toClient(){
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);
        return client;
    }
}
