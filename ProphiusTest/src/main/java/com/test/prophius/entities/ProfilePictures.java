package com.test.prophius.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "prfoile_pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProfilePictures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Lob
    @Column(name = "picture", columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

}
