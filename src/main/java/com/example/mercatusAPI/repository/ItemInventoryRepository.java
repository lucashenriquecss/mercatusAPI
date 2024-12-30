package com.example.mercatusAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.inventory.ItemInventory;

@Repository
public interface ItemInventoryRepository extends JpaRepository<ItemInventory, String> {
    
}
