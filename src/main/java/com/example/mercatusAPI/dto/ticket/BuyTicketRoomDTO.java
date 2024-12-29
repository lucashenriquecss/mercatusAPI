package com.example.mercatusAPI.dto.ticket;

import java.math.BigDecimal;

public record BuyTicketRoomDTO(boolean isActive, BigDecimal amount) {
    
}
