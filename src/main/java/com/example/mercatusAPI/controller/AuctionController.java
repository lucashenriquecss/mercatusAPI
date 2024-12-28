package com.example.mercatusAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercatusAPI.dto.auth.RegisterDTO;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.service.AuctionService;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<String> placeBid(@PathVariable String auctionId, @RequestBody double bidAmount) {
        Auction auction = auctionService.findAuctionById(auctionId);
        auctionService.placeBid(null, auction, bidAmount); 

        return ResponseEntity.ok("Bid placed successfully");
    }
    @GetMapping("/create-room")
    public ResponseEntity create() {
        auctionService.createAuctions();
        return ResponseEntity.ok("Bid placed successfully");
    }

    @GetMapping("/open")
    public List<Auction> getOpenAuctions() {
        return auctionService.getOpenAuctions();
    }
}
