package com.prestamossb.prestamossbapi.infraestruture.controllers.loan;

import com.prestamossb.prestamossbapi.app.commant.loan.CreateLoan;
import com.prestamossb.prestamossbapi.app.commant.loan.LoanDelete;
import com.prestamossb.prestamossbapi.app.query.loan.LoanFind;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanResponse;
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
@RequestMapping("api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final CreateLoan createLoan;
    private final LoanFind loanFind;
    private final ValidateErrors validateErrors;
    private final LoanDelete loanDelete;


    @PostMapping
    public ResponseEntity<ResponseText> create(@Validated  @RequestBody LoanRequest loanRequest, BindingResult result){

        if(result.hasErrors()) {

            throw new BadRequestException(validateErrors.ValidFields(result));
        }

        createLoan.create(loanRequest);
        return new ResponseEntity<>(ResponseText.CREATED, HttpStatus.CREATED);
    }
    @GetMapping
    private  ResponseEntity<List<LoanResponse>> findAllByUserId(){
        List<LoanResponse> loanResponseList = loanFind.findAllUserId();
        return  new ResponseEntity<>(loanResponseList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private  ResponseEntity<List<LoanResponse>>findAll(@PathVariable UUID id){
       List<LoanResponse> loanResponseList =  loanFind.findAll(id);
       return new ResponseEntity<>(loanResponseList,HttpStatus.OK);
    }
    @GetMapping("/payment-day")
    private  ResponseEntity<List<LoanResponse>>findAllByDate(){
        List<LoanResponse> loanResponseList =  loanFind.findAllByDate();
        return new ResponseEntity<>(loanResponseList,HttpStatus.OK);
    }

    @GetMapping("/balance/{id}")
    private  ResponseEntity<LoanResponse>findBalance(@PathVariable UUID id){
        LoanResponse loanResponse =  loanFind.findBalance(id);
        return new ResponseEntity<>(loanResponse,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    private ResponseEntity<ResponseText> deleteLoan(@PathVariable UUID id){
       loanDelete.delete(id);
       return new ResponseEntity<>(ResponseText.DELETED,HttpStatus.OK);
    }
}
