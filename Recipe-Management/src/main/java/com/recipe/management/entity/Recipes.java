package com.recipe.management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "recipes", uniqueConstraints = {@UniqueConstraint(columnNames = "TITLE")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "prep_time_minute")
    private Double prepTimeMinute;

    @Lob
    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @UpdateTimestamp
    @Column(name = "modified_on")
    private LocalDateTime modifiedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_kind")
    private RecipeKind recipeKind;

    // added to handle cascading
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredients> recipeIngredients;

    @ManyToMany(mappedBy = "recipes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Menus> menus;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ratings> ratings;
}

