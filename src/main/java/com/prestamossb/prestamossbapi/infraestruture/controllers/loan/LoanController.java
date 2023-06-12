package com.prestamossb.prestamossbapi.infraestruture.controllers.loan;

import com.prestamossb.prestamossbapi.app.commant.loan.CreateLoan;
import com.prestamossb.prestamossbapi.app.query.loan.LoanFind;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanRequest;
import com.prestamossb.prestamossbapi.infraestruture.Dto.loan.LoanResponse;
import com.prestamossb.prestamossbapi.infraestruture.controllers.ResponseText;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final CreateLoan createLoan;
    private final LoanFind loanFind;


    @PostMapping
    public ResponseEntity<ResponseText> create(@RequestBody LoanRequest loanRequest){
        createLoan.create(loanRequest);
        return new ResponseEntity<>(ResponseText.CREATED, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private  ResponseEntity<List<LoanResponse>> findAll(@PathVariable UUID id){
       List<LoanResponse> loanResponseList =  loanFind.findAll(id);
       return new ResponseEntity<>(loanResponseList,HttpStatus.OK);
    }

}
