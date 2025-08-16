package com.training.expenseTracker.repository;

import com.training.expenseTracker.entity.Expenses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    boolean existsByCategory_Id(Integer categoryId);

    @Query("select count(e) from Expenses e where e.category.id = :categoryId")
    long countByCategoryId(Integer categoryId);
    @Query("""
       SELECT e
       FROM Expenses e
       WHERE (:from IS NULL OR e.date >= :from)
         AND (:to IS NULL OR e.date <= :to)
         AND (:categoryId IS NULL OR e.category.id = :categoryId)
       """)
    Page<Expenses> search(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("categoryId") Integer  categoryId,
            Pageable pageable
    );
    @Query("""
       SELECT COALESCE(SUM(e.amount), 0)
       FROM Expenses e
       WHERE (:from IS NULL OR e.date >= :from)
         AND (:to IS NULL OR e.date <= :to)
         AND (:categoryId IS NULL OR e.category.id = :categoryId)
       """)
    BigDecimal total(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            @Param("categoryId") Integer  categoryId
    );
    List<Expenses> findByCategory_Id(int categoryId);
    @Query("""
       SELECT e FROM Expenses e
       WHERE (:from IS NULL OR e.date >= :from)
         AND (:to IS NULL OR e.date <= :to)
         AND (:categoryId = 0 OR e.category.id = :categoryId)
       """)
    List<Expenses> findByDateBetweenAndCategory_Id( @Param("from") LocalDate from,
                                                   @Param("to") LocalDate to,
                                                   @Param("categoryId") int categoryId
                                                   );
}
