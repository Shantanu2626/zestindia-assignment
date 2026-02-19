package com.zestindia.product.model;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String productName;
    private String createdBy;

    private LocalDateTime createdOn;
    private String modifiedBy;
    private LocalDateTime modifiedOn;

}
