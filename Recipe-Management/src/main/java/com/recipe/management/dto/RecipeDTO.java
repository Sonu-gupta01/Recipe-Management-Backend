package com.recipe.management.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private Double prepTimeMinute;
    private Long createdBy;
    private String imagePath;
    private String recipeKind;
    private List<IngredientDTO> ingredients;

}


