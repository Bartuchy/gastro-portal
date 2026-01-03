package com.gastro.portal.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long>{

    @Query("select r from Recipe r where lower(r.name) like lower(concat('%',:name, '%')) order by r.date desc")
    Optional<List<RecipeEntity>> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    @Query("select r from Recipe r where lower(r.category) like lower(concat('%',:category, '%')) order by r.date desc")
    Optional<List<RecipeEntity>> findByCategoryContainingIgnoreCaseOrderByDateDesc(String category);

    @Query("select r from Recipe r where r.author.displayName=:nickname order by r.date desc")
    Optional<List<RecipeEntity>> findRecipesAddedByUser(String nickname);
}
