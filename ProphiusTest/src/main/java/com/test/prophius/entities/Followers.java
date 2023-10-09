package com.test.prophius.entities;


import javax.persistence.*;

@Entity
@Table(name = "user_followers")
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private User folower;

}
