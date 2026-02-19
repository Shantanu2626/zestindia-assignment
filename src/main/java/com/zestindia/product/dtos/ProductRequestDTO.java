package com.zestindia.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private Long id;
    private String productName;
    private String createdBy;
}
