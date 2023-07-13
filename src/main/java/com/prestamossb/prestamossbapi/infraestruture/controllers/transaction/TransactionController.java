package com.prestamossb.prestamossbapi.infraestruture.controllers.transaction;

import com.prestamossb.prestamossbapi.app.commant.transaction.CreateTransaction;
import com.prestamossb.prestamossbapi.app.commant.transaction.TransactionDelete;
import com.prestamossb.prestamossbapi.app.query.Transaction.TransactionFind;

import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.transaction.TransactionResponse;
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
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final CreateTransaction createTransaction;
    private final TransactionFind transactionFind;
    private final ValidateErrors validateErrors;
    private final TransactionDelete transactionDelete;
    @PostMapping
    public ResponseEntity<ResponseText> create(@Validated  @RequestBody TransactionRequest transactionRequest, BindingResult result){
        if(result.hasErrors()) {
            throw new BadRequestException(validateErrors.ValidFields(result));
        }
        createTransaction.create(transactionRequest);
        return new ResponseEntity<>(ResponseText.CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TransactionResponse>> findAll(@PathVariable UUID id){
       List<TransactionResponse> transactionResponseList = transactionFind.FindAll(id);
       return new ResponseEntity<>(transactionResponseList,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findAllUser(){
        List<TransactionResponse> transactionResponseList = transactionFind.FindAllByUserId();
        return new ResponseEntity<>(transactionResponseList,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<ResponseText>deleteTransaction(@PathVariable UUID id){
        transactionDelete.delete(id);
        return new ResponseEntity<>(ResponseText.DELETED,HttpStatus.OK);
    }
}
