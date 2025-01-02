package com.example.mercatusAPI.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.example.mercatusAPI.dto.auction.RegisterRoomDTO;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.auction.MessagesAuction;
import com.example.mercatusAPI.entitty.auction.MessagesType;
import com.example.mercatusAPI.repository.AuctionRepository;
import com.example.mercatusAPI.repository.MessagesAuctionRepository;

@Service
public class AuctionService {
    
    private final AuctionRepository auctionRepository;
    private final MessagesAuctionRepository messagesAuctionRepository;

    public AuctionService(AuctionRepository auctionRepository, MessagesAuctionRepository messagesAuctionRepository) {
        this.auctionRepository = auctionRepository;
        this.messagesAuctionRepository = messagesAuctionRepository;
    }

    public Auction findAuctionById(String id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Auction not found with id: " + id));
    }
    

    public List<Auction> getOpenAuctions() {
        return auctionRepository.findOpenAuctions();
    }

    public void createAuctions(RegisterRoomDTO registrationData) {
        Auction createRoom = Auction.builder()
        .name(registrationData.name())
        .description(registrationData.description())
        .startTime(registrationData.startTime())
        .endTime(registrationData.endTime())
        .ticketValue(registrationData.ticketValue())
        .build();

       auctionRepository.save(createRoom);
    }

    public void placeBid(WebSocketSession session, Auction auction, BigDecimal bidAmount, String message) {
        MessagesAuction bid = new MessagesAuction();
        bid.setAmount(bidAmount);
        bid.setAuctionId(auction.getId());
        bid.setContent(message);
        bid.setType(MessagesType.BID);
        bid.setUserId(null);//TODO: BUSCAR O USER ID NO SOCKET
        messagesAuctionRepository.save(bid);

        System.out.println(message);
    }

    public void sendMessageToAll(WebSocketSession session, Auction auction, String message) {
        MessagesAuction messageCommon = new MessagesAuction();
        messageCommon.setAmount(null);
        messageCommon.setAuctionId(auction.getId());
        messageCommon.setContent(message);
        messageCommon.setType(MessagesType.MESSAGE);
        messageCommon.setUserId(null);//TODO: BUSCAR O USER ID NO SOCKET
        messagesAuctionRepository.save(messageCommon);
        System.out.println(message);
    }
}

