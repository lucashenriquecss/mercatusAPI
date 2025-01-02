package com.example.mercatusAPI.controller;

import com.example.mercatusAPI.dto.transaction.ItemPurchaseDTO;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.infra.ApiResponsePadronize;
import com.example.mercatusAPI.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TransactionController transactionController;

    private User mockUser;
    private ItemPurchaseDTO mockItemPurchaseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId("123");
        mockUser.setEmail("testUser@gmail.com");

        mockItemPurchaseDTO = new ItemPurchaseDTO(5); 

        when(authentication.getPrincipal()).thenReturn(mockUser);
    }

    @Test
    void itemPurchase_ShouldReturnSuccessResponse() {
        String itemId = "item123";

        ResponseEntity<ApiResponsePadronize<String>> response = transactionController.itemPurchase(authentication, itemId, mockItemPurchaseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Processado com sucesso", response.getBody().getMessage());

        verify(transactionService, times(1)).itemPurchase(mockUser, itemId, mockItemPurchaseDTO.quantity());
    }

    @Test
    void itemPurchase_ShouldHandleException() {
        String itemId = "item123";
        doThrow(new RuntimeException("Item not available")).when(transactionService)
            .itemPurchase(mockUser, itemId, mockItemPurchaseDTO.quantity());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionController.itemPurchase(authentication, itemId, mockItemPurchaseDTO);
        });

        assertEquals("Item not available", exception.getMessage());
        verify(transactionService, times(1)).itemPurchase(mockUser, itemId, mockItemPurchaseDTO.quantity());
    }
}
