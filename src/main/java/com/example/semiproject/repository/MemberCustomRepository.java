package com.example.semiproject.repository;

import com.example.semiproject.entity.Member;
import com.example.semiproject.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository {


    private final EntityManager entityManager;

    public Optional<Member> findByEmail(String email){
       return Optional.ofNullable(entityManager.createQuery("select distinct m from Member m " +
               " join fetch m.roles r " +
               " where m.email = :email ", Member.class).setParameter("email", email).getSingleResult());
    };

    public List<Member> findByEmailContainAndRole(String email, String role){
      return entityManager.createQuery("select m from Member m " +
                "join fetch  m.roles r where m.email like :email and r.name = :role")
                .setParameter("email","%"+email+"%").setParameter("role",role).getResultList();
    }

}
