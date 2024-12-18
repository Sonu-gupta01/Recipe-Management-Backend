package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.IngredientDTO;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.INGREDIENTS_BASE)
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping(RestConstants.INGREDIENT_BY_ID)
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(ingredientService.doGetIngredientById(id));
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() throws BusinessException {
        return ResponseEntity.ok(ingredientService.doGetAllIngredients());
    }

    @PostMapping
    public ResponseEntity<String> createIngredient(@RequestBody IngredientDTO ingredientDTO) throws BusinessException {
        ingredientService.doCreateIngredient(ingredientDTO);
        return ResponseEntity.ok(ErrorMessages.INGREDIENT_CREATED);
    }

    @PutMapping(RestConstants.INGREDIENT_BY_ID)
    public ResponseEntity<String> updateIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO) throws BusinessException {
        ingredientService.doUpdateIngredient(id, ingredientDTO);
        return ResponseEntity.ok(ErrorMessages.INGREDIENT_UPDATED);
    }

    @DeleteMapping(RestConstants.INGREDIENT_BY_ID)
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) throws BusinessException {
        ingredientService.deleteIngredientById(id);
        return ResponseEntity.ok(ErrorMessages.INGREDIENT_DELETED);
    }
}
