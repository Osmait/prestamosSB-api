package com.prestamossb.prestamossbapi.infraestruture.controllers.client;

import com.prestamossb.prestamossbapi.app.commant.client.CreateClient;
import com.prestamossb.prestamossbapi.app.query.client.ClientFind;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.client.ClientResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final CreateClient createClient;
    private final ClientFind clientFind;


    @PostMapping
    public ResponseEntity<ResponseText> create(@RequestBody ClientRequest clientRequest) {
        createClient.create(clientRequest);
        return new ResponseEntity<>(ResponseText.CREATED,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>>findAll(){
        List<ClientResponse>  clientResponseList = clientFind.findAll();

        return new ResponseEntity<>(clientResponseList,HttpStatus.OK);

    }


}
