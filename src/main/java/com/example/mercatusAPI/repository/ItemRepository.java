package com.example.mercatusAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    
}
