package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.RecipeDTO;
import com.recipe.management.entity.*;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.RECIPES_BASE)
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping(RestConstants.RECIPE_CREATE)
    public ResponseEntity<Map<String, String>> doCreateRecipe(@RequestBody RecipeDTO recipeDTO) throws BusinessException {
        recipeService.doCreateRecipe(recipeDTO);
        Map<String, String> response = new HashMap<>();
        response.put(ErrorMessages.MESSAGE, ErrorMessages.RECIPE_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(RestConstants.RECIPE_BY_ID)
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable String id) throws BusinessException {
        Long recipeId = Long.parseLong(id);
        RecipeDTO recipeDTO = recipeService.doGetRecipeById(recipeId);
        return ResponseEntity.ok(recipeDTO);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() throws BusinessException {
        List<RecipeDTO> recipes = recipeService.doGetAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @DeleteMapping(RestConstants.RECIPE_BY_ID)
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id, @RequestHeader(ErrorMessages.USER_ID) Long userId) throws BusinessException {
        recipeService.deleteRecipeById(id, userId);
        return ResponseEntity.ok(ErrorMessages.RECIPE_DELETED);
    }

    @PutMapping(RestConstants.RECIPE_BY_ID)
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO, @RequestHeader(ErrorMessages.USER_ID) Long userId) throws BusinessException {
        recipeService.doUpdateRecipe(id, recipeDTO, userId);
        return ResponseEntity.ok(ErrorMessages.RECIPE_UPDATED);
    }


    @GetMapping(RestConstants.RECIPE_SEARCH_BY_KIND)
    public ResponseEntity<List<RecipeDTO>> searchRecipesByKind(@RequestParam RecipeKind recipeKind) throws BusinessException {
        List<RecipeDTO> recipes = recipeService.doGetRecipesByRecipeKind(recipeKind);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping(RestConstants.RECIPE_SEARCH)
    public ResponseEntity<List<RecipeDTO>> searchRecipes(@RequestParam(ErrorMessages.QUERY) String query) throws BusinessException {
        List<RecipeDTO> recipes = recipeService.doSearchRecipes(query.trim());
        return ResponseEntity.ok(recipes);
    }
}


