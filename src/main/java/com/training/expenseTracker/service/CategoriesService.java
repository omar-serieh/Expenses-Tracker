package com.training.expenseTracker.service;

import com.training.expenseTracker.dto.categories.CategoriesCreateDTO;
import com.training.expenseTracker.dto.categories.CategoriesReadDTO;
import com.training.expenseTracker.dto.categories.CategoriesUpdateDTO;

import java.util.List;

public interface CategoriesService {
    CategoriesReadDTO create(CategoriesCreateDTO categoriesCreateDTO);
    CategoriesReadDTO update(Integer id, CategoriesUpdateDTO categoriesUpdateDTO);
    void delete(Integer id);
    CategoriesReadDTO getById(Integer id);
    List<CategoriesReadDTO> findAll();
}
