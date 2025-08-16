package com.training.expenseTracker.mapper.expenses;

import com.training.expenseTracker.dto.expenses.ExpensesUpdateDTO;
import com.training.expenseTracker.entity.Expenses;
import com.training.expenseTracker.exceptions.ApiException;
import com.training.expenseTracker.repository.CategoriesRepository;
import org.mapstruct.*;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public interface ExpensesUpdateMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Expenses expenses, ExpensesUpdateDTO expensesUpdateDTO);
    @AfterMapping
    default void linkCategory(ExpensesUpdateDTO expensesUpdateDTO, @MappingTarget Expenses expenses, CategoriesRepository categoriesRepository) {
        if (expensesUpdateDTO.categoryId() != null) {
            expenses.setCategory(
                    categoriesRepository.findById(expensesUpdateDTO.categoryId())
                            .orElseThrow(() -> new ApiException("Category not found", HttpStatus.NOT_FOUND))
            );
        }
    }
}
