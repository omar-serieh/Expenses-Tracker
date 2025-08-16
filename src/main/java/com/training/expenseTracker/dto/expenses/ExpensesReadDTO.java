package com.training.expenseTracker.dto.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensesReadDTO(
        long expenseId,
        BigDecimal amount,
        String description,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        ExpenseCategory expenseCategory
) {
    public record ExpenseCategory(
            int expenseCategoryId,
            String expenseCategoryName,
            String expenseCategoryColor
    ) {}
}

