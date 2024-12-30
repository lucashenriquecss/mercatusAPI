package com.example.mercatusAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import com.example.mercatusAPI.service.AuctionService;
import com.example.mercatusAPI.service.TicketService;

import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.user.User;


@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final AuctionService auctionService;

    public TicketController(TicketService ticketService,AuctionService auctionService){
        this.ticketService = ticketService;
        this.auctionService = auctionService;
    }


    @PostMapping("/{auctionId}/buy")
    public ResponseEntity<String> buyTicket(Authentication authentication, @PathVariable String auctionId){
        
        User user = (User) authentication.getPrincipal();
        Auction auction = auctionService.findAuctionById(auctionId);

        ticketService.buyTicket(user, auction);  
        
        return ResponseEntity.ok("Compra efetuada com sucesso.");
       
    }
    
}
