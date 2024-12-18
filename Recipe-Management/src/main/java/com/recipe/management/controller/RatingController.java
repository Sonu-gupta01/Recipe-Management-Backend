package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.RatingDTO;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.RATINGS_BASE)
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping(RestConstants.RATING_BY_ID)
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(ratingService.doGetRatingById(id));
    }

    @GetMapping
    public ResponseEntity<List<RatingDTO>> getAllRatings() throws BusinessException {
        return ResponseEntity.ok(ratingService.doGetAllRatings());
    }

    @PostMapping
    public ResponseEntity<String> createRating(@RequestBody RatingDTO ratingDTO) throws BusinessException {
        ratingService.doCreateRating(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ErrorMessages.RATING_CREATED);
    }

    @PutMapping(RestConstants.RATING_BY_ID)
    public ResponseEntity<String> updateRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) throws BusinessException {
        ratingService.doUpdateRating(id, ratingDTO);
        return ResponseEntity.ok(ErrorMessages.RATING_UPDATED);
    }

    @DeleteMapping(RestConstants.RATING_BY_ID)
    public ResponseEntity<String> deleteRatingById(@PathVariable Long id) throws BusinessException {
        ratingService.deleteRatingById(id);
        return ResponseEntity.ok(ErrorMessages.RATING_DELETED);
    }

    @GetMapping(RestConstants.AVERAGE_RATING)
    public ResponseEntity<Double> getAverageRating(@PathVariable Long recipeId) throws BusinessException {
        return ResponseEntity.ok(ratingService.doGetAverageRatingForRecipe(recipeId));
    }
}