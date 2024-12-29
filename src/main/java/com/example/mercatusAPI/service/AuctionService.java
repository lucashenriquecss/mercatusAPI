package com.example.mercatusAPI.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.example.mercatusAPI.dto.auction.RegisterRoomDTO;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.bid.Bid;
import com.example.mercatusAPI.repository.AuctionRepository;
import com.example.mercatusAPI.repository.BidRepository;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public Auction findAuctionById(String id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + id));
    }
    

    public List<Auction> getOpenAuctions() {
        return auctionRepository.findAll();
    }

    public void createAuctions(RegisterRoomDTO registrationData) {
        Auction createRoom = Auction.builder()
        .name(registrationData.name())
        .description(registrationData.description())
        .startTime(registrationData.startTime())
        .endTime(registrationData.endTime())
        .ticketValue(registrationData.ticketValue())
        .status(registrationData.status())
        .build();

       auctionRepository.save(createRoom);
    }

    public void placeBid(WebSocketSession session, Auction auction, BigDecimal bidAmount) {
        Bid bid = new Bid();
        bid.setAmount(bidAmount);
        bid.setAuction(auction);
        bidRepository.save(bid);

        sendMessageToAll(session, auction, "New bid: " + bidAmount);
    }

    public void sendMessageToAll(WebSocketSession session, Auction auction, String message) {
        System.out.println(message);
    }
}

