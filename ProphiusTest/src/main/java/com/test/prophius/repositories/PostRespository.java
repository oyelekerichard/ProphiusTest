package com.test.prophius.repositories;

import com.test.prophius.entities.Posts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRespository extends PagingAndSortingRepository<Posts, Long> {
}
