package com.recipe.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private Long id;
    private Long recipeId;
    private Long userId;
    private Integer rating;
    private LocalDateTime createdAt;
}


