package com.smart_expense.budget_management_system.repository;

import com.smart_expense.budget_management_system.entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expenses,Long> {
}
