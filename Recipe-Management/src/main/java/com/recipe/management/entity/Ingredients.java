package com.recipe.management.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredients", uniqueConstraints = {@UniqueConstraint(columnNames = "NAME")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String unit;

    private Double quantity;
}
