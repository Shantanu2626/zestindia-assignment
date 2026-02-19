package com.zestindia.product.mapper;
import com.zestindia.product.dtos.ProductResponseDTO;
import com.zestindia.product.dtos.ProductUpdateDTO;
import com.zestindia.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public ProductResponseDTO responseMapper(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setCreatedBy(product.getCreatedBy());

        return productResponseDTO;
    }
}
