//package com.recipe.management.entity;
//
//import jakarta.persistence.*;
//
//import java.io.Serializable;
//import java.util.Set;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "categories")
//public class Category implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false, updatable = false)
//    private Integer id;
//
//    @Column(name = "name", unique = true, nullable = false)
//    private String name;
//
//    @Column(name = "description")
//    private String description;
//
//    // Relationships with cascading rules
//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<RecipeCategory> recipeCategories;
//
//
//}
