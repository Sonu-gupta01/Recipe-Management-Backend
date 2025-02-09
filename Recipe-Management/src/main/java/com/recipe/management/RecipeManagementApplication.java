package com.recipe.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.recipe.management")
@ComponentScan(basePackages = "com.recipe.management")
@EntityScan(basePackages = "com.recipe.management.entity")
@EnableScheduling
public class RecipeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeManagementApplication.class, args);
	}

}
