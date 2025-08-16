package com.training.expenseTracker.dto.expenses;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensesUpdateDTO(
        BigDecimal amount,
        String description,
        LocalDate date,
        Integer categoryId
) {}
