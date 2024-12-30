package com.example.mercatusAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.auction.Auction;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, String> {
      
    @Query("SELECT auction FROM Auction auction WHERE auction.startTime > CURRENT_TIMESTAMP")
    List<Auction> findOpenAuctions();
}
