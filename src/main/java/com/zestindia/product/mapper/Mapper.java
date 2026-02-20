package com.zestindia.product.mapper;
import com.zestindia.product.dtos.ItemResponseDTO;
import com.zestindia.product.dtos.ProductResponseDTO;
import com.zestindia.product.model.Item;
import com.zestindia.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public ProductResponseDTO responseMapper(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setProductName(product.getProductName());
        productResponseDTO.setCreatedBy(product.getCreatedBy());

        List<ItemResponseDTO> itemsList = new ArrayList<>();

        for(Item item : product.getItems()){
         ItemResponseDTO itemRequestDTO = new ItemResponseDTO();
         itemRequestDTO.setQuantity(item.getQuantity());
         itemsList.add(itemRequestDTO);
        }
        productResponseDTO.setItems(itemsList);
        return productResponseDTO;
    }
}
