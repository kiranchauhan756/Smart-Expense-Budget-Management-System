package com.smart_expense.budget_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    @Column(unique = true)
    private String username;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String alternateEmail;
    private String gender;
    @Column(length=10,nullable = false)
    @Size(min=10,max=10)
    private String phoneNumber;
    @Column(name="profilePicture")
    private String profilePicture;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;


    @ManyToMany(fetch=FetchType.LAZY)
    //defining user_roles table with composite key user_id & role_id
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    public User(LocalDate dob,String email, String password, String username, boolean isActive, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.dob=dob;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

   public void addRoles(Role role){
        if(roles==null){
            roles=new ArrayList<>();
        }
        roles.add(role);
    }

}

