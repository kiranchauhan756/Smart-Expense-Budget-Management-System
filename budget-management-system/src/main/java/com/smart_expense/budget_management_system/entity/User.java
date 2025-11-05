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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String username;
    @Column
    private boolean status;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime lastModifiedDate;
    @Column
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;


    public void addRoles(Role role){
        if(roles==null){
            roles=new ArrayList<>();
        }
        roles.add(role);
    }

}
