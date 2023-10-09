package com.test.prophius.entities;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private Posts posts;
}
