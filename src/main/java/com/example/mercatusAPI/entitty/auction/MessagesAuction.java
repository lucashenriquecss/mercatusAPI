package com.example.mercatusAPI.entitty.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.mercatusAPI.entitty.BaseEntity;
import com.example.mercatusAPI.entitty.inventory.Inventory;
import com.example.mercatusAPI.entitty.user.User;
import com.mongodb.lang.Nullable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Table(name = "messages_auctions")
@Entity(name = "messages_auctions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class MessagesAuction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private MessagesType type;
    
    @Column( precision = 19, scale = 2, nullable = true, columnDefinition = "DECIMAL(19,2) DEFAULT 0.00")
    private BigDecimal amount;
    
    @Column()
    private String content;

    @Column()
    private String replyTo;

    @ManyToOne
    @JoinColumn(name= "auction_id", nullable = true)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = true)
    private User user;

}
