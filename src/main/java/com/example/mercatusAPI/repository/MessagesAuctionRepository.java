package com.example.mercatusAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.mercatusAPI.entitty.auction.MessagesAuction;

@Repository
public interface MessagesAuctionRepository extends JpaRepository<MessagesAuction, String>{
    
}
