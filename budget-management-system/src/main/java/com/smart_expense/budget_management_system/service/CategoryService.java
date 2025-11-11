package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.DateDescription;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.repository.CategoryRepository;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository=userRepository;
    }

    public Category saveCategory(Category category){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user=userRepository.findByUsername(username);
        if(user.isPresent()){
            category.setDefaultCategory(false);
            category.setDateDescription(new DateDescription(LocalDateTime.now(),user.get().getId(),user.get().getUsername(),LocalDateTime.now(),user.get().getUsername(),user.get().getId()));
            }
      return  categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
