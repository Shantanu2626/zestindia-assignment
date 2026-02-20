package com.zestindia.product.controller;

import com.zestindia.product.dtos.ItemResponseDTO;
import com.zestindia.product.dtos.ProductRequestDTO;
import com.zestindia.product.dtos.ProductResponseDTO;
import com.zestindia.product.dtos.ProductUpdateDTO;
import com.zestindia.product.model.Product;
import com.zestindia.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // Save the product in database
    @PostMapping("/saveproduct")
    public ResponseEntity<ProductResponseDTO> saveProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO product = productService.addProduct(productRequestDTO);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    // This controller created for getting all product from database
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList , HttpStatus.OK);
    }

    //Get products by productID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        ProductResponseDTO productResponse = productService.getProductById(id);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    //This controller created for updating the product
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable Long id , @RequestBody ProductUpdateDTO dto){
        Product product = productService.updateProduct(id , dto);
        return new ResponseEntity<>(product , HttpStatus.OK);
    }

    // This controller is created for deleting the product from database
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItemsByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getItemsByProductId(id));
    }
}
