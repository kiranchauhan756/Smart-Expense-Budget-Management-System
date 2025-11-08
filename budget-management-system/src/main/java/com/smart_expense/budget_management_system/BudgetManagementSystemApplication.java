package com.smart_expense.budget_management_system;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BudgetManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(BudgetManagementSystemApplication.class, args);

	}
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

}
