package com.zestindia.product.service;
import com.zestindia.product.dtos.ItemResponseDTO;
import com.zestindia.product.dtos.ProductRequestDTO;
import com.zestindia.product.dtos.ProductResponseDTO;
import com.zestindia.product.dtos.ProductUpdateDTO;
import com.zestindia.product.exception.ProductNotFound;
import com.zestindia.product.mapper.Mapper;
import com.zestindia.product.model.Item;
import com.zestindia.product.model.Product;
import com.zestindia.product.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    //Injecting repository dependency
    private final ProductRepository productRepository;

    //Mapper for productResponse
    private final Mapper mapper;

    public ProductService(ProductRepository productRepository, Mapper mapper){
        this.productRepository = productRepository;
        this.mapper = mapper;
    }


    //Adding product in database
    public ProductResponseDTO addProduct(ProductRequestDTO productdto){
        Product product = new Product();
        product.setId(productdto.getId());
        product.setProductName(productdto.getProductName());
        product.setCreatedBy(productdto.getCreatedBy());
        product.setCreatedOn(new Date());


        if (productdto.getItems() != null) {
            List<Item> itemList = new ArrayList<>();

            for (ItemResponseDTO itemDto : productdto.getItems()) {
                Item item = new Item();
                item.setQuantity(itemDto.getQuantity());
                item.setProduct(product);
                itemList.add(item);
            }
            product.setItems(itemList);
        }

        try {
            productRepository.save(product);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        return mapper.responseMapper(product);
    }

    //Get all products
    public List<Product> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products;
    }

    //Get product by id
    public ProductResponseDTO getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow( () -> new ProductNotFound("Product not present in database please check the id"));
        return mapper.responseMapper(product);
    }

    //Update product from database
    public Product updateProduct(Long id,ProductUpdateDTO updatedProduct){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFound("Product not found in database please check the product id"));
        product.setProductName(updatedProduct.getProductName());
        product.setModifiedBy(updatedProduct.getModifiedBy());
        product.setModifiedOn(new Date());

        return productRepository.save(product);
    }

    //Deleting product from database
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()-> new ProductNotFound("Product not found in database please check the product id"));
        if(product != null){
         productRepository.deleteById(id);
        }
    }
}
