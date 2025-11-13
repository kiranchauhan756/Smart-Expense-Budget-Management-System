package com.smart_expense.budget_management_system.configuration;

import com.smart_expense.budget_management_system.entity.Category;
import com.smart_expense.budget_management_system.entity.DateDescription;
import com.smart_expense.budget_management_system.entity.Role;
import com.smart_expense.budget_management_system.entity.User;
import com.smart_expense.budget_management_system.repository.CategoryRepository;
import com.smart_expense.budget_management_system.repository.RoleRepository;
import com.smart_expense.budget_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;


    @Autowired
    public DataInitializer(RoleRepository roleRepository,UserRepository userRepository,PasswordEncoder passwordEncoder,CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        //Adding Default role ADMIN and USER in ROLE table
        Optional<Role>userRole= Optional.of(roleRepository.findByName("USER").orElseGet(() ->
                roleRepository.save(new Role("USER", "Default role for users"))));
        Optional<Role> adminRole= Optional.of(roleRepository.findByName("ADMIN").orElseGet(() ->
                roleRepository.save(new Role("ADMIN", "Role for admins"))));

        //Adding default Admin
        if(userRepository.findByUsername("admin").isEmpty()){
            User user=new User("budgetIQAdmin@gmail.com", passwordEncoder.encode( "admin"), "admin", true, LocalDateTime.now(),LocalDateTime.now());
            user.setRoles(List.of(userRole.get(),adminRole.get()));
            userRepository.save(user);
        }

        //Adding (Food,Groceries,Rent,Shopping,Medicine,Travel,School Fee,Electricity Bill,Gym,Electronics) default categories in  Category table
        if(categoryRepository.count()==0) {
            categoryRepository.save(new Category("Food", "Expenses related to eating out, restaurants, or daily meals.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Groceries", "Daily household essentials and food items purchased from stores or supermarkets.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Rent", "Monthly house or apartment rent payments.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Shopping", "Purchases of clothes, accessories, home decor, and other personal items.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Medicine", "Medical bills, pharmacy purchases, and health-related expenses.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Travel", "Transportation, trips, fuel, public transport fares, or travel bookings.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("School Fee", "Payments for tuition fees, books, and other education-related costs.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Electricity Bill", "Monthly electricity and utility payments.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Gym", "Membership fees or fitness-related subscriptions and expenses.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
            categoryRepository.save(new Category("Electronics", "Purchase or repair of gadgets like mobiles, laptops, or home appliances.", new DateDescription(LocalDateTime.now(), 1L, "admin", LocalDateTime.now(), "admin", 1L)));
        }


    }
}
