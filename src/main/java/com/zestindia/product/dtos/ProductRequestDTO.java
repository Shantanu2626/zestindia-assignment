package com.zestindia.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    @Size(max = 255, message = "Product name must be less than 255 characters")
    private String productName;

    @NotBlank(message = "Created by must not be blank")
    @Size(max = 100, message = "Created by must be less than 100 characters")
    private String createdBy;

    @NotEmpty(message = "At least one item is required")
    private List<ItemResponseDTO> items = new ArrayList<>();
}
