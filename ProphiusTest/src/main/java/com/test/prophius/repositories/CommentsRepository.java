package com.test.prophius.repositories;

import com.test.prophius.entities.Comments;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends PagingAndSortingRepository<Comments, Long> {
}
