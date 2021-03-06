package com.example.semiproject.repository;

import com.example.semiproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Page<Post> findByRemoveYnAndTitleContainingOrderByCreatedDateDesc(Boolean removeYn,String title, Pageable pageable);
    Page<Post> findByTitleContainingOrderByCreatedDateDesc(String title, Pageable pageable);

}
