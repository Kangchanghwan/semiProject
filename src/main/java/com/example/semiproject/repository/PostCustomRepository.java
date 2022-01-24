package com.example.semiproject.repository;

import com.example.semiproject.entity.Member;
import com.example.semiproject.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostCustomRepository {

    private final EntityManager entityManager;

    public Page<Post> findByEmail(String email, Pageable pageable){
        List list = entityManager.createQuery("select p from Post p join fetch p.member m where  m.email = :email")
                .setParameter("email", email).getResultList();
        int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        final Page<Post> page = new PageImpl<Post>(list.subList(start, end), pageable, list.size());
        return page;
    }

    public Optional<Post> findByIdAndEmail(Long id,String email){
        return Optional.ofNullable(entityManager.createQuery("select distinct p from Post p " +
                " join fetch p.member m " +
                " where m.email = :email and p.id = :id", Post.class).setParameter("email",email).setParameter("id",id).getSingleResult());
    };




}
