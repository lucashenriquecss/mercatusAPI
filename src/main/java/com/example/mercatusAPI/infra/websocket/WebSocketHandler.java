package com.example.mercatusAPI.infra.websocket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.repository.UserRepository;
import com.example.mercatusAPI.service.AuctionService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final AuctionService auctionService;
    private final UserRepository userRepository;

    private final Map<String, WebSocketSession> activeSessions = new HashMap<>();

    public WebSocketHandler(AuctionService auctionService, UserRepository userRepository) {
        this.auctionService = auctionService;
        this.userRepository =  userRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        activeSessions.put(session.getId(), session);

        System.out.println("Connection established: " + session.getId());
    }

     @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Error occurred: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        activeSessions.remove(session.getId());

        System.out.println("Connection closed: " + session.getId());
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (session.isOpen()) {
            System.out.println("Mensagem recebida de " + session.getId() + ": " + message.getPayload());

            String regex = ".*/auction/([a-fA-F0-9\\-]+)/join.*";
            String  uri = session.getUri().toString();

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(uri);

            if(matcher.find()){
                String auctionId = matcher.group(1);
                String email = "";

                Auction auction = auctionService.findAuctionById(auctionId);

                User user = (User) userRepository.findByEmail(email);
        
                if (message.getPayload().startsWith("bid:")) {
                    BigDecimal bidAmount =  new BigDecimal(message.getPayload().substring(4));
                    auctionService.placeBid(session, auction, bidAmount, message.getPayload(), user);
                } else {
                    auctionService.sendMessageToAll(session, auction, message.getPayload(), user);
                }
            }
        }else {
            System.out.println("A sessão WebSocket não está ativa.");
        }
    }
}

