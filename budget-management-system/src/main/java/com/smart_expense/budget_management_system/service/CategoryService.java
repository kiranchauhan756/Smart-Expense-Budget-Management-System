package com.smart_expense.budget_management_system.service;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.DateDescription;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.exception.CategoryAlreadyExistsException;
import com.smart_expense.budget_management_system.exception.CategoryNotFoundException;
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

    public void saveCategory(Category category){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user=userRepository.findByUsername(username);
        Optional<Category> category1 = categoryRepository.findByName(category.getName());

        if(category1.isEmpty()) {
            if (user.isPresent()) {
                category.setDefaultCategory(false);
                category.setDateDescription(new DateDescription(LocalDateTime.now(), user.get().getId(), user.get().getUsername(), LocalDateTime.now(), user.get().getUsername(), user.get().getId()));
                categoryRepository.save(category);
            }
        }
        else {
                throw new CategoryAlreadyExistsException("Category with this name already exists.");
            }
    }

    public List<Category> getAllCategories(){
        if(categoryRepository.findAll().isEmpty()) throw new CategoryNotFoundException("No Category Exists");
        return categoryRepository.findAll();
    }
    public Optional<Category> findCategoryById(Long id){
        if(categoryRepository.findById(id).isEmpty())throw new CategoryNotFoundException("No Category found with this id");
        return categoryRepository.findById(id);
    }

    public void deleteCategory(long id){
        Optional<Category> category=categoryRepository.findById(id);
        if(category.isEmpty())throw new CategoryNotFoundException("No Category found with this id");
        else{
            categoryRepository.deleteById(id);
        }
    }

    public Integer getTotalCategories(){
        if(categoryRepository.count()!=0)return Math.toIntExact(categoryRepository.count());
        throw new CategoryNotFoundException("No categories are present in the system yet...");
    }


}
