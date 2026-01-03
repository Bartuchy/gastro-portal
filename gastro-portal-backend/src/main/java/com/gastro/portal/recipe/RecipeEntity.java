package com.gastro.portal.recipe;

import com.gastro.portal.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Recipe")
@Table(name = "recipe")
public class RecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;
    private String name;
    private String category;
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

//    @ElementCollection
//    @CollectionTable(name = "ingredient", joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "name")
    private String ingredients;

//    @ElementCollection
//    @CollectionTable(name = "direction", joinColumns = @JoinColumn(name = "id"))
//    @Column(name = "name")
    private String directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    public RecipeEntity(String name, String category, String description, String ingredients, String directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
