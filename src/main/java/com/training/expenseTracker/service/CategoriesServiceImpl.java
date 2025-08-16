package com.training.expenseTracker.service;

import com.training.expenseTracker.dto.categories.CategoriesCreateDTO;
import com.training.expenseTracker.dto.categories.CategoriesReadDTO;
import com.training.expenseTracker.dto.categories.CategoriesUpdateDTO;
import com.training.expenseTracker.entity.Categories;
import com.training.expenseTracker.exceptions.ApiException;
import com.training.expenseTracker.mapper.categories.CategoriesCreateMapper;
import com.training.expenseTracker.mapper.categories.CategoriesReadMapper;
import com.training.expenseTracker.mapper.categories.CategoriesUpdateMapper;
import com.training.expenseTracker.repository.CategoriesRepository;
import com.training.expenseTracker.repository.ExpensesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService{
    private final CategoriesRepository categoriesRepository;
    private final CategoriesCreateMapper categoriesCreateMapper;
    private final CategoriesReadMapper categoriesReadMapper;
    private final CategoriesUpdateMapper categoriesUpdateMapper;
    private final ExpensesRepository expensesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository, CategoriesCreateMapper categoriesCreateMapper, CategoriesReadMapper categoriesReadMapper, CategoriesUpdateMapper categoriesUpdateMapper, ExpensesRepository expensesRepository) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesCreateMapper = categoriesCreateMapper;
        this.categoriesReadMapper = categoriesReadMapper;
        this.categoriesUpdateMapper = categoriesUpdateMapper;
        this.expensesRepository = expensesRepository;
    }

    @Override
    public CategoriesReadDTO create(CategoriesCreateDTO categoriesCreateDTO) {

        Categories categories= categoriesCreateMapper.toEntity(categoriesCreateDTO);
        categories = categoriesRepository.save(categories);
        return categoriesReadMapper.toDto(categories);
    }

    @Override
    @Transactional
    public CategoriesReadDTO update(Integer id, CategoriesUpdateDTO categoriesUpdateDTO) {
        Optional<Categories> categories=categoriesRepository.findById(id);
        if(categories.isEmpty()){
            throw new ApiException("Category not found", HttpStatus.NOT_FOUND);
        }
        Categories category=categories.get();
        categoriesUpdateMapper.updateFromDto(categoriesUpdateDTO, category);
        Categories savedCategories=categoriesRepository.save(category);
        return categoriesReadMapper.toDto(savedCategories);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<Categories> categories=categoriesRepository.findById(id);
        if(categories.isEmpty()){
            throw new ApiException("Category not found", HttpStatus.NOT_FOUND);
        }
        boolean hasExpenses = expensesRepository.existsByCategory_Id(id);
        if (hasExpenses) {
            long count = expensesRepository.countByCategoryId(id);
            throw new ApiException(
                    "You can't delete this Category because it has " + count + " expenses / expense please delete this expenses or re-assign it",
                    HttpStatus.CONFLICT
            );
        }
        categoriesRepository.deleteById(id);
    }

    @Override
    public CategoriesReadDTO getById(Integer id) {
        Optional<Categories> categories=categoriesRepository.findById(id);
        if(categories.isEmpty()){
            throw new ApiException("Category not found", HttpStatus.NOT_FOUND);
        }
        Categories category=categories.get();
        return categoriesReadMapper.toDto(category);
    }

    @Override
    public List<CategoriesReadDTO> findAll() {
        List<Categories> categories = categoriesRepository.findAll();
        if(categories.isEmpty()){
            throw new ApiException("No categories to display", HttpStatus.NOT_FOUND);
        }
        return categories.stream()
                .map(categoriesReadMapper::toDto)
                .toList();
    }



}
