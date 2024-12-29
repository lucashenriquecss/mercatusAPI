package com.example.mercatusAPI.entitty.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.mercatusAPI.entitty.bid.Bid;
import com.example.mercatusAPI.entitty.inventory.Inventory;
import com.example.mercatusAPI.entitty.ticket.Ticket;
import com.example.mercatusAPI.entitty.transaction.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "auctions")
@Entity(name = "auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column()
    private String name;

    @Column()
    private String description;

    @Column(nullable= true)
    private String status;

    @Column(name="ticket_value", precision = 19, scale = 2, nullable = false, columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal ticketValue;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids;
    
    @OneToMany(mappedBy = "auction")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "auction")
    private List<Ticket> tickets;

    @OneToOne()
    @JoinColumn(name= "inventory_id", referencedColumnName = "id")
    private Inventory inventory;
  

}

