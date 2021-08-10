package com.example.BasketService.models;

import com.example.BasketService.models.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class NotificationServiceMessageWrapper implements Serializable {
    ProductDTO message;
}
