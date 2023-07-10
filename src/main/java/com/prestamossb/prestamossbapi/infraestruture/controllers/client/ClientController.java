package com.prestamossb.prestamossbapi.infraestruture.controllers.client;

import com.prestamossb.prestamossbapi.app.commant.client.ClientDelete;
import com.prestamossb.prestamossbapi.app.commant.client.CreateClient;
import com.prestamossb.prestamossbapi.app.query.client.ClientFind;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import com.prestamossb.prestamossbapi.infraestruture.controllers.exceptionControllers.exceptions.BadRequestException;
import com.prestamossb.prestamossbapi.infraestruture.utils.ValidateErrors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClient createClient;
    private final ClientFind clientFind;
    private final ValidateErrors validateErrors;
    private final ClientDelete clientDelete;

    @PostMapping
    public ResponseEntity<ResponseText> create(@Validated @RequestBody ClientRequest clientRequest, BindingResult result) {
        if(result.hasErrors()) {

            throw new BadRequestException(validateErrors.ValidFields(result));
        }

        createClient.create(clientRequest);
        return new ResponseEntity<>(ResponseText.CREATED,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>>findAll(){
        List<ClientResponse>  clientResponseList = clientFind.findAll();

        return new ResponseEntity<>(clientResponseList,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<HttpStatus>deleteClient(@PathVariable UUID id){
       clientDelete.delete(id);
       return new ResponseEntity<>(HttpStatus.OK);
    }


}
