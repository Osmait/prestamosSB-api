package com.prestamossb.prestamossbapi.infraestruture.controllers.transaction;

import com.prestamossb.prestamossbapi.app.commant.transaction.CreateTransaction;
import com.prestamossb.prestamossbapi.app.query.Transaction.TransactionFind;
import com.prestamossb.prestamossbapi.domain.transaction.TransactionRepository;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransaction createTransaction;

    private final TransactionFind transactionFind;

    public ResponseEntity<ResponseText> create(@RequestBody TransactionRequest transactionRequest){
        createTransaction.create(transactionRequest);
        return new ResponseEntity<>(ResponseText.CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionResponse>> findAll(@PathVariable UUID id){
       List<TransactionResponse> transactionResponseList = transactionFind.FindAll(id);
       return new ResponseEntity<>(transactionResponseList,HttpStatus.OK);
    }
}
