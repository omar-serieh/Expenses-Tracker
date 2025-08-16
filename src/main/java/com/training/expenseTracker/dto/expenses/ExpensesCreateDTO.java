package com.training.expenseTracker.dto.expenses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ExpensesCreateDTO {
    private BigDecimal expenseAmount;
    private String expenseDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expenseDate;

    private int expenseCategoryId;
}
