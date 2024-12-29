package com.example.mercatusAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.ticket.Ticket;
import com.example.mercatusAPI.entitty.user.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{
        Optional<Ticket> findByUserAndAuctionAndIsActiveTrue(User user, Auction auction);

}
