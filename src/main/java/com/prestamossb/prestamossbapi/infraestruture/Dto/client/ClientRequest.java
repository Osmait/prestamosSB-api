package com.prestamossb.prestamossbapi.infraestruture.Dto.client;

import com.prestamossb.prestamossbapi.domain.client.Client;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequest {

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

    @Digits(integer = 10, fraction = 0, message = "the field not is a phone")
    private String phone;

    private String address;

    public Client toClient(){
        Client client = new Client();
        client.setName(name);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setPhone(phone);
        client.setAddress(address);
        return client;
    }
}
