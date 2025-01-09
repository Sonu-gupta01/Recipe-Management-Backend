package com.recipe.management.entity;

import jakarta.persistence.*;


import lombok.*;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_RECIPE_ID"))
    private Recipes recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_INGREDIENT_ID"))
    private Ingredients ingredient;

}