//package com.recipe.management.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.io.Serializable;
//
//@Entity
//@Data
//@Table(name = "recipe_categories")
//public class RecipeCategory implements Serializable {
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "recipe_id", nullable = false)
//    private Recipe recipe;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//}
