package com.ecomm.enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String altText;
    private Boolean isPrimary;
    private Integer displayOrder=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public ProductImage(Long id, String imageUrl, String altText, Boolean isPrimary,
                        Integer displayOrder, Product product) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.isPrimary = isPrimary != null ? isPrimary : false;
        this.displayOrder = displayOrder != null ? displayOrder : 0;
        this.product = product;
    }
}
