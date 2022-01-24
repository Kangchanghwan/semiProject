package com.example.semiproject.repository;

import com.example.semiproject.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository {


    private final EntityManager entityManager;

    public Optional<Member> findByEmail(String email){
       return Optional.ofNullable(entityManager.createQuery("select distinct m from Member m " +
               " join fetch m.roles r " +
               " where m.email = :email ", Member.class).setParameter("email",email).getSingleResult());
    };




}
