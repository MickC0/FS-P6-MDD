package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 550)
    private String content;

    @ManyToOne
    @JoinColumn(name="author_id",referencedColumnName = "id")
    private UserEntity author;


    @ManyToOne
    @JoinColumn(name="post_id",referencedColumnName = "id")
    private PostEntity post;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
