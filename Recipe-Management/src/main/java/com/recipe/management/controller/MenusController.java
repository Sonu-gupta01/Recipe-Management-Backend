package com.recipe.management.controller;

import com.recipe.management.constants.ErrorMessages;
import com.recipe.management.queries.RestConstants;
import com.recipe.management.dto.MenusDTO;
import com.recipe.management.exception.BusinessException;
import com.recipe.management.service.MenusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = RestConstants.BASE_URL)
@RequestMapping(RestConstants.MENUS_BASE)
public class MenusController {

    @Autowired
    private MenusService menusService;

    @GetMapping(RestConstants.MENU_BY_ID)
    public ResponseEntity<MenusDTO> getMenuById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(menusService.doGetMenuById(id));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMenus(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) throws BusinessException {
        return ResponseEntity.ok(menusService.doGetAllMenusWithPagination(page, size));
    }

    @PostMapping
    public ResponseEntity<String> createMenu(@RequestBody MenusDTO menuDTO) throws BusinessException {
        menusService.doCreateMenu(menuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ErrorMessages.MENU_CREATED);
    }

    @PutMapping(RestConstants.MENU_BY_ID)
    public ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenusDTO menuDTO) throws BusinessException {
        menusService.doUpdateMenu(id, menuDTO);
        return ResponseEntity.ok(ErrorMessages.MENU_UPDATED);
    }

    @DeleteMapping(RestConstants.MENU_BY_ID)
    public ResponseEntity<String> deleteMenuById(@PathVariable Long id) throws BusinessException {
        menusService.deleteMenuById(id);
        return ResponseEntity.ok(ErrorMessages.MENU_DELETED);
    }

    @GetMapping(RestConstants.USER_MENU)
    public ResponseEntity<Map<String, Object>> getMenusForUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(required = false) Long userId) throws BusinessException {
        if (userId == null) {
            throw new BusinessException(ErrorMessages.USER_ID_REQUIRED);
        }
        return ResponseEntity.ok(menusService.doGetMenusForUserWithPagination(userId, page, size));
    }
}