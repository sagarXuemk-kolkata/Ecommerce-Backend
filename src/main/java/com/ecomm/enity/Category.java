package com.ecomm.enity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean active=true;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Builder
    public Category(Long id, String name, String description, String imageUrl, Boolean active, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active != null ? active : true;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }


    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        if(this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }
}
