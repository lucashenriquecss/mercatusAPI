package com.example.mercatusAPI.entitty.item;


import java.util.List;


import com.example.mercatusAPI.entitty.inventory.Inventory;
import com.example.mercatusAPI.entitty.transaction.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Table(name = "items")
@Entity(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column()
    private String name;

    @Column()
    private String description;

    @Column()
    private String quantity;

    @Column()
    private double amount;

    @Column()
    private String status;

    @Column()
    private List<String> images;

    @Column(name="is_auctioned")
    private boolean isAuctioned;


    @OneToMany(mappedBy="item")
    private List<Transaction> transactions;
    
    @ManyToOne
    @JoinColumn(name= "inventory_id")
    private Inventory inventory;


}


