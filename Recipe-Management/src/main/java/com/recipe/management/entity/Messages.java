package com.recipe.management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_SENDER_ID"))
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "reciver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_RECIVER_ID"))
    private Users receiver;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_RECIPE_ID_V2"))
    private Recipes recipe;

    @Lob
    @Column(name = "message_text")
    private String messageText;

    @Column(name = "sent_on")
    private LocalDateTime sentOn;
}
