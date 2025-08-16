package com.training.expenseTracker.controller;

import com.training.expenseTracker.dto.categories.CategoriesCreateDTO;
import com.training.expenseTracker.dto.categories.CategoriesReadDTO;
import com.training.expenseTracker.dto.categories.CategoriesUpdateDTO;
import com.training.expenseTracker.service.CategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoriesController {
    private final CategoriesService categoriesService;
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }
    @PostMapping
    ResponseEntity<CategoriesReadDTO> createCategory(@RequestBody CategoriesCreateDTO categoriesCreateDTO) {
        CategoriesReadDTO categoriesReadDTO=categoriesService.create(categoriesCreateDTO);
        return ResponseEntity.ok(categoriesReadDTO);
    }
    @PutMapping("/{id}")
    ResponseEntity<CategoriesReadDTO> updateCategory(@RequestBody CategoriesUpdateDTO categoriesUpdateDTO, @PathVariable Integer id) {
        CategoriesReadDTO categoriesReadDTO=categoriesService.update(id,categoriesUpdateDTO);
        return ResponseEntity.ok(categoriesReadDTO);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoriesService.delete(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
    @GetMapping("/{id}")
    ResponseEntity<CategoriesReadDTO> findCategoryById(@PathVariable Integer id) {
        CategoriesReadDTO categoriesReadDTO=categoriesService.getById(id);
        return ResponseEntity.ok(categoriesReadDTO);
    }
    @GetMapping("/all")
    ResponseEntity<List<CategoriesReadDTO>> findAllCategories() {
        List<CategoriesReadDTO> categoriesReadDTOList=categoriesService.findAll();
        return ResponseEntity.ok(categoriesReadDTOList);
    }


}
