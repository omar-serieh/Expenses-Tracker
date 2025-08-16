package com.training.expenseTracker.mapper.expenses;

import com.training.expenseTracker.dto.expenses.ExpensesReadDTO;
import com.training.expenseTracker.entity.Categories;
import com.training.expenseTracker.entity.Expenses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpensesReadMapper {

    @Mapping(source = "id", target = "expenseId")
    @Mapping(source = "category", target = "expenseCategory")
    ExpensesReadDTO toDto(Expenses expense);

    @Mapping(source = "id",    target = "expenseCategoryId")
    @Mapping(source = "name",  target = "expenseCategoryName")
    @Mapping(source = "color", target = "expenseCategoryColor")
    ExpensesReadDTO.ExpenseCategory toExpenseCategoryDto(Categories category);
}
