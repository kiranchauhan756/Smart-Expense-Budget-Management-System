package com.smart_expense.budget_management_system.repository;

import com.smart_expense.budget_management_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query("SELECT c FROM Category c WHERE c.user.id = :userId OR c.isDefaultCategory = true")
    List<Category> findByUserIdOrDefault(@Param("userId") Long userId);

}
