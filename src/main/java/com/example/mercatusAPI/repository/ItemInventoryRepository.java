package com.example.mercatusAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mercatusAPI.entitty.inventory.Inventory;
import com.example.mercatusAPI.entitty.inventory.ItemInventory;
import com.example.mercatusAPI.entitty.item.Item;

@Repository
public interface ItemInventoryRepository extends JpaRepository<ItemInventory, String> {
        Optional<ItemInventory> findByInventoryAndItem(Item item, Inventory inventory);

}
