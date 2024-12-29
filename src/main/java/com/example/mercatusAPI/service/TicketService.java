package com.example.mercatusAPI.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mercatusAPI.dto.ticket.BuyTicketRoomDTO;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.ticket.Ticket;
import com.example.mercatusAPI.entitty.transaction.Transaction;
import com.example.mercatusAPI.entitty.transaction.TransactionType;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.exception.ticket.TicketAlreadyExistsException;
import com.example.mercatusAPI.exception.user.InsufficientBalanceException;
import com.example.mercatusAPI.repository.TicketRepository;
import com.example.mercatusAPI.repository.TransactionRepository;
import com.example.mercatusAPI.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository,TransactionRepository transactionRepository,UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

   
    @Transactional
    public void buyTicket(User user,Auction auction){
        Optional<Ticket> existingTicket = ticketRepository.findByUserAndAuctionAndIsActiveTrue(user, auction);

        if (existingTicket.isPresent()) {
            throw new TicketAlreadyExistsException("Ticket já comprado para este leilão.");
        }

        if (user.getBalance().compareTo(auction.getTicketValue()) < 0) {//TODO: AJUSTAR RETORNOS
            throw new TicketAlreadyExistsException("Saldo insuficiente para comprar o ticket.");
        }

        
        Transaction transaction = transactionRepository.save(
            Transaction.builder()
            .type(TransactionType.BUY_TICKET)
            .description("Buy ticket")
            .amount(auction.getTicketValue())
            .oldBalanceUser(user.getBalance())
            .newBalanceUser(user.getBalance().subtract(auction.getTicketValue()))
            .createdAt(LocalDateTime.now())
            .auction(auction)
            .user(user)
            .build()
        );

        user.setBalance(user.getBalance().subtract(auction.getTicketValue()));

        userRepository.save(user);

        ticketRepository.save(
            Ticket.builder()
            .isActive(true)
            .amount(auction.getTicketValue())
            .auction(auction)
            .user(user)
            .transaction(transaction)
            .build()
        );
    }
}
