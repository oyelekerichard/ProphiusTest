package com.test.prophius.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String content;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "likes_counter")
    private int likesCount;
    @ManyToOne
    private User user;
}
