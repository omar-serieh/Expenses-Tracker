package com.training.expenseTracker.mapper.expenses;

import com.training.expenseTracker.dto.expenses.ExpensesCreateDTO;
import com.training.expenseTracker.entity.Expenses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpensesCreateMapper {
    @Mapping(source = "expenseAmount" ,target = "amount")
    @Mapping(source = "expenseDescription" ,target = "description")
    @Mapping(source = "expenseDate" ,target = "date")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    Expenses toEntity(ExpensesCreateDTO expensesCreateDTO);
}
