package com.example.mercatusAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercatusAPI.dto.transaction.ItemPurchaseDTO;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.infra.ApiResponsePadronize;
import com.example.mercatusAPI.service.TransactionService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/{itemId}/buy")
    public ResponseEntity<ApiResponsePadronize<String>> itemPurchase(Authentication authentication, @PathVariable String itemId, @RequestBody @Valid  ItemPurchaseDTO data){
        
        User user = (User) authentication.getPrincipal();

        transactionService.itemPurchase(user, itemId, data.quantity());  
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponsePadronize<>(HttpStatus.OK, "Processado com sucesso"));
       
    }
}
