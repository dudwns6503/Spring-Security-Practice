package com.example.securitypractice.repository;

import com.example.securitypractice.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
                select t from Token t inner join Member m on t.member.id = m.id
                where m.id = :memberId and (t.isExpired = false or t.isRevoked = false)
            """)
    List<Token> findAllValidTokensByMember(@Param(value = "memberId") Long memberId);

    Optional<Token> findByToken(String token);

}
