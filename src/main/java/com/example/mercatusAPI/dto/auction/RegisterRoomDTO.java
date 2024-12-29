package com.example.mercatusAPI.dto.auction;

import java.time.LocalDateTime;

public record  RegisterRoomDTO(String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
    
}
