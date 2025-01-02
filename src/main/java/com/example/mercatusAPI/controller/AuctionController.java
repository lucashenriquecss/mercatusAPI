package com.example.mercatusAPI.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mercatusAPI.dto.auction.RegisterRoomDTO;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.infra.ApiResponsePadronize;
import com.example.mercatusAPI.service.AuctionService;

@RestController
@RequestMapping("/api/v1/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/{auctionId}/bid")
    public ResponseEntity<ApiResponsePadronize<String>> placeBid(@PathVariable String auctionId, @RequestBody BigDecimal bidAmount) {

        Auction auction = auctionService.findAuctionById(auctionId);
        auctionService.placeBid(null, auction, bidAmount, null); 

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponsePadronize<>(HttpStatus.OK, "Bid placed successfully", null));
    }
    
    @PostMapping("/create-room")
    public ResponseEntity<ApiResponsePadronize<String>> create(@RequestBody @Valid RegisterRoomDTO registrationData) {

        auctionService.createAuctions(registrationData);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponsePadronize<>(HttpStatus.OK, "Processado com sucesso", null));
    }

    @GetMapping("/open")
    public ResponseEntity<ApiResponsePadronize<List<Auction>>> getOpenAuctions() {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponsePadronize<>(HttpStatus.OK, "Processado com sucesso", auctionService.getOpenAuctions()));
    }
}
