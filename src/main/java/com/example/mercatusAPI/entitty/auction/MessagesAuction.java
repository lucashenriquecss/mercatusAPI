package com.example.mercatusAPI.entitty.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.mercatusAPI.entitty.BaseEntity;
import com.mongodb.lang.Nullable;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data  
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") 
@Document(collection = "messages_auctions")
public class MessagesAuction extends BaseEntity {
    @Id
    private String id;
    private MessagesType type;
    
    @Nullable
    @Field("amount")
    private BigDecimal amount;

    private String content;
    private String auctionId;
    private String userId;
    private String replyTo;

}
