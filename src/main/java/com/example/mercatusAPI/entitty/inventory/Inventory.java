package com.example.mercatusAPI.entitty.inventory;


import java.util.List;

import com.example.mercatusAPI.entitty.BaseEntity;
import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Table(name = "inventories")
@Entity(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Inventory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "inventory", cascade= CascadeType.ALL)
    private List<ItemInventory> items;

    @OneToOne(mappedBy="inventory")
    private User user;

    @OneToOne(mappedBy="inventory")
    private Auction auction;

}


