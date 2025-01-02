package com.example.mercatusAPI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.mercatusAPI.entitty.auction.MessagesAuction;


@Repository
public interface MessagesAuctionRepository extends MongoRepository<MessagesAuction, String>{
    
}
