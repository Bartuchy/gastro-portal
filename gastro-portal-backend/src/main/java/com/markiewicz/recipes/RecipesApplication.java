package com.markiewicz.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = { DataSourceAutoConfiguration.class })
public class RecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}


//	@Bean
//	CommandLineRunner runner(RecipeRepository repository, MongoTemplate mongoTemplate) {
//		return args -> {
//			Recipe recipe = new Recipe(
//					LocalDateTime.now(),
//					"category1",
//					"some description",
//					List.of("ingredient1", "ingredient2"),
//					List.of("direction1", "direction2")
//			);
//			repository.insert(recipe);
//		};
//	}

}
