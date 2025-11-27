package com.smart_expense.budget_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    @NotBlank(message = "Category name is required")
    private String name;
    private boolean isDefaultCategory;
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Expenses> expenses;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Category(String name, boolean isDefaultCategory,String description,LocalDateTime createdDate,LocalDateTime lastModifiedDate,User user) {
        this.name = name;
        this.isDefaultCategory=isDefaultCategory;
        this.description = description;
        this.createdDate=createdDate;
        this.lastModifiedDate=lastModifiedDate;
        this.user=user;
    }


}
