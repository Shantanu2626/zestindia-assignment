package com.zestindia.product.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String createdBy;
    private Date createdOn;
    private String modifiedBy;
    private Date modifiedOn;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();
}
