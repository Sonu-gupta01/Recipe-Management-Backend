package com.recipe.management.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenusDTO {
    private Long id;
    private Long userId;
    private String username;
    private String menuName;
    private String mealType;
    private LocalDateTime scheduledAt;
    private List<Long> recipeIds;
    private List<String> recipeTitles;
}



