package com.example.securitypractice.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean isExpired;
    private boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public void changeState() {
        this.isExpired = !this.isExpired;
        this.isRevoked = !this.isRevoked;
    }
}
