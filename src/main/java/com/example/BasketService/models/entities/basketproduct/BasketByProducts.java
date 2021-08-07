package com.example.BasketService.models.entities.basketproduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BasketByProducts {
    @Id
    private Long productId;

    @Id
    @Column
    private Long userId;

}
