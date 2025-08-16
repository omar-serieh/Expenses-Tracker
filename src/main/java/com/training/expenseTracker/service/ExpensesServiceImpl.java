package com.training.expenseTracker.service;
import com.training.expenseTracker.dto.expenses.ExpensesCreateDTO;
import com.training.expenseTracker.dto.expenses.ExpensesReadDTO;
import com.training.expenseTracker.dto.expenses.ExpensesUpdateDTO;
import com.training.expenseTracker.entity.Categories;
import com.training.expenseTracker.entity.Expenses;
import com.training.expenseTracker.exceptions.ApiException;
import com.training.expenseTracker.mapper.expenses.ExpensesCreateMapper;
import com.training.expenseTracker.mapper.expenses.ExpensesReadMapper;
import com.training.expenseTracker.mapper.expenses.ExpensesUpdateMapper;
import com.training.expenseTracker.repository.CategoriesRepository;
import com.training.expenseTracker.repository.ExpensesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final CategoriesRepository categoriesRepository;
    private final ExpensesCreateMapper expensesCreateMapper;
    private final ExpensesReadMapper expensesReadMapper;
    private final ExpensesUpdateMapper expensesUpdateMapper;
    public ExpensesServiceImpl(ExpensesRepository expensesRepository, CategoriesRepository categoryRepository, ExpensesCreateMapper expensesCreateMapper, ExpensesReadMapper expensesReadMapper, ExpensesUpdateMapper expensesUpdateMapper) {
        this.expensesRepository = expensesRepository;
        this.categoriesRepository = categoryRepository;
        this.expensesCreateMapper = expensesCreateMapper;
        this.expensesReadMapper = expensesReadMapper;
        this.expensesUpdateMapper = expensesUpdateMapper;
    }

    @Override
    public ExpensesReadDTO create(ExpensesCreateDTO expensesCreateDTO) {
        Optional<Categories> categories=categoriesRepository.findById(expensesCreateDTO.getExpenseCategoryId());
        if(categories.isEmpty()){
            throw new ApiException("Category not found",HttpStatus.NOT_FOUND);
        }
        Categories category=categories.get();
        Expenses expenses = expensesCreateMapper.toEntity(expensesCreateDTO);
        expenses.setCategory(category);
        expenses = expensesRepository.save(expenses);
        return expensesReadMapper.toDto(expenses);
    }

    @Override
    @Transactional
    public ExpensesReadDTO update(Long id, ExpensesUpdateDTO expensesUpdateDTO) {
        Optional<Expenses> expenses = expensesRepository.findById(id);
        if(expenses.isEmpty()){
            throw new ApiException("expense not found", HttpStatus.NOT_FOUND);
        }
        Expenses expensesEntity = expenses.get();
        expensesUpdateMapper.updateEntity(expensesEntity,expensesUpdateDTO);
        expensesUpdateMapper.linkCategory(expensesUpdateDTO,expensesEntity,categoriesRepository);
        expensesEntity = expensesRepository.save(expensesEntity);
        return expensesReadMapper.toDto(expensesEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Expenses> expenses = expensesRepository.findById(id);
        if(expenses.isEmpty()){
            throw new ApiException("expense not found", HttpStatus.NOT_FOUND);
        }
        expensesRepository.deleteById(id);
    }

    @Override
    public ExpensesReadDTO getById(Long id) {
        Optional<Expenses> expenses = expensesRepository.findById(id);
        if(expenses.isEmpty()){
            throw new ApiException("expense not found", HttpStatus.NOT_FOUND);
        }
        Expenses expensesEntity = expenses.get();
        return expensesReadMapper.toDto(expensesEntity);
    }

    @Override
    public Page<ExpensesReadDTO> search(LocalDate from, LocalDate to, Integer categoryId, Pageable pageable) {
        Page<Expenses> expensesPage = expensesRepository.search(from, to, categoryId, pageable);
        return expensesPage.map(expensesReadMapper::toDto);
    }


    @Override
    public BigDecimal total(LocalDate from, LocalDate to, Integer  categoryId) {
        return expensesRepository.total(from, to, categoryId);
    }


    @Override
    public List<ExpensesReadDTO> allExpenses() {
        List<Expenses> expenses = expensesRepository.findAll();
        if(expenses.isEmpty()){
            throw new ApiException("No Expenses to display", HttpStatus.NOT_FOUND);
        }
        return expenses.stream()
                .map(expensesReadMapper::toDto)
                .toList();
    }

    @Override
    public List<ExpensesReadDTO> allExpensesByCategory(Integer  categoryId) {
        Optional<Categories> category = categoriesRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new ApiException("Category not found", HttpStatus.NOT_FOUND);
        }
        List<Expenses> expenses = expensesRepository.findByCategory_Id(categoryId);
        if(expenses.isEmpty()){
            throw new ApiException("No Expenses to display", HttpStatus.NOT_FOUND);
        }
        return expenses.stream()
                .map(expensesReadMapper::toDto)
                .toList();
    }

    @Override
    public List<ExpensesReadDTO> allExpensesByCategoryAndDate(LocalDate from, LocalDate to, Integer  categoryId) {
        Optional<Categories> category = categoriesRepository.findById(categoryId);
        if(category.isEmpty()){
            throw new ApiException("Category not found", HttpStatus.NOT_FOUND);
        }
        List<Expenses> expenses = expensesRepository.findByDateBetweenAndCategory_Id(from,to,categoryId);
        if(expenses.isEmpty()){
            throw new ApiException("No Expenses to display", HttpStatus.NOT_FOUND);
        }
        return expenses.stream()
                .map(expensesReadMapper::toDto)
                .toList();
    }
}
