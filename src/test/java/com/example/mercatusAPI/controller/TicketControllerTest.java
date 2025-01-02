package com.example.mercatusAPI.controller;

import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.infra.ApiResponsePadronize;
import com.example.mercatusAPI.service.AuctionService;
import com.example.mercatusAPI.service.TicketService;

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

public class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private AuctionService auctionService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TicketController ticketController;

    private User mockUser;
    private Auction mockAuction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId("123");
        mockUser.setEmail("testUser@gmail.com");

        mockAuction = new Auction();
        mockAuction.setId("456");
        mockAuction.setName("Leilão Teste");

        when(authentication.getPrincipal()).thenReturn(mockUser);

        when(auctionService.findAuctionById("456")).thenReturn(mockAuction);
    }

    @Test
    void buyTicket_ShouldReturnSuccessResponse() {
        String auctionId = "456";

        ResponseEntity<ApiResponsePadronize<String>> response = ticketController.buyTicket(authentication, auctionId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Processado com sucesso", response.getBody().getMessage());

        verify(ticketService, times(1)).buyTicket(mockUser, mockAuction);
        verify(auctionService, times(1)).findAuctionById(auctionId);
    }

    @Test
    void buyTicket_ShouldHandleException() {
        String auctionId = "999";
        when(auctionService.findAuctionById(auctionId)).thenThrow(new RuntimeException("Auction not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketController.buyTicket(authentication, auctionId);
        });

        assertEquals("Auction not found", exception.getMessage());
        verify(auctionService, times(1)).findAuctionById(auctionId);
    }
}
