package com.training.expenseTracker.controller;

import com.training.expenseTracker.dto.expenses.ExpensesCreateDTO;
import com.training.expenseTracker.dto.expenses.ExpensesReadDTO;
import com.training.expenseTracker.dto.expenses.ExpensesUpdateDTO;
import com.training.expenseTracker.service.ExpensesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {
    private final ExpensesService expensesService;
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }
    @GetMapping
    ResponseEntity<List<ExpensesReadDTO>> getAllExpenses() {
        List<ExpensesReadDTO> expensesReadDTO=expensesService.allExpenses();
        return ResponseEntity.ok(expensesReadDTO);
    }
    @GetMapping("/{id}")
    ResponseEntity<ExpensesReadDTO> getExpensesById(@PathVariable long id) {
        ExpensesReadDTO expensesReadDTO=expensesService.getById(id);
        return ResponseEntity.ok(expensesReadDTO);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteExpensesById(@PathVariable long id) {
        expensesService.delete(id);
        return ResponseEntity.ok("Deleted expense successfully");
    }
    @PostMapping
    ResponseEntity<ExpensesReadDTO> createExpenses(@RequestBody ExpensesCreateDTO expensesCreateDTO) {
        ExpensesReadDTO expensesReadDTO=expensesService.create(expensesCreateDTO);
        return ResponseEntity.ok(expensesReadDTO);
    }
    @PutMapping("/{id}")
    ResponseEntity<ExpensesReadDTO> updateExpenses(@PathVariable long id, @RequestBody ExpensesUpdateDTO expensesUpdateDTO) {
        ExpensesReadDTO expensesReadDTO=expensesService.update(id, expensesUpdateDTO);
        return ResponseEntity.ok(expensesReadDTO);
    }
    @GetMapping("/search")
    ResponseEntity<Page<ExpensesReadDTO>> searchExpenses( @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                          @RequestParam(required = false) Integer categoryId,
                                                          @PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC)
                                                          Pageable pageable) {
        Page<ExpensesReadDTO> expensesReadDTOS=expensesService.search(from, to, categoryId, pageable);
        return ResponseEntity.ok(expensesReadDTOS);
    }
    @GetMapping("/total")
    ResponseEntity<BigDecimal> totalExpenses(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                              @RequestParam(required = false) Integer categoryId) {
        BigDecimal expensesTotal=expensesService.total(from, to, categoryId);
        return ResponseEntity.ok(expensesTotal);
    }
    @GetMapping("/all/{categoryId}")
    ResponseEntity<List<ExpensesReadDTO>> getAllExpensesByCategory(@PathVariable Integer categoryId) {
        List<ExpensesReadDTO> expensesReadDTOS=expensesService.allExpensesByCategory(categoryId);
        return ResponseEntity.ok(expensesReadDTOS);
    }
    @GetMapping("/allBy")
    ResponseEntity<List<ExpensesReadDTO>> getAllExpensesByCategoryAndDate(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                                                          @RequestParam(required = false) Integer categoryId){
        List<ExpensesReadDTO> expensesReadDTOS=expensesService.allExpensesByCategoryAndDate(from, to, categoryId);
        return ResponseEntity.ok(expensesReadDTOS);

    }


}
