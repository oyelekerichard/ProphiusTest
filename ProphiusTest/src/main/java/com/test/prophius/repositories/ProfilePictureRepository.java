package com.test.prophius.repositories;

import com.test.prophius.entities.ProfilePictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePictures, Long> {

    @Modifying
    @Query(value = "UPDATE profile_picture set sig_img = ?1 WHERE username = ?2", nativeQuery = true )
    ProfilePictures updateProfilePicture(String sigImg, String username);

    ProfilePictures findDistinctByCustomerAccountNumber(String accountNumber);

}
