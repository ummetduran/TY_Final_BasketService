package com.example.BasketService.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.io.Serializable;
@Data
@NoArgsConstructor
public class UserInfoForStockMessage implements Serializable {
    List<Long> userIdList;
    private Long productId;
    private String messageType;

}
