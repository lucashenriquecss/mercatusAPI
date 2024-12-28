package com.example.mercatusAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

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
        return auctionRepository.findById(id).orElseThrow(() -> new RuntimeException("Auction not found"));
    }

    public List<Auction> getOpenAuctions() {
        return auctionRepository.findAll();
    }

    public void placeBid(WebSocketSession session, Auction auction, double bidAmount) {
        Bid bid = new Bid();
        bid.setAmount(bidAmount);
        bid.setAuction(auction);
        bidRepository.save(bid);

        sendMessageToAll(session, auction, "New bid: " + bidAmount);
    }

    public void sendMessageToAll(WebSocketSession session, Auction auction, String message) {
        // Lógica para enviar a mensagem para todos os usuários conectados na sala
    }
}

