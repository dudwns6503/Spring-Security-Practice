package com.example.securitypractice.service;

import com.example.securitypractice.config.JwtService;
import com.example.securitypractice.domain.Member;
import com.example.securitypractice.domain.Role;
import com.example.securitypractice.dto.AuthenticationRequest;
import com.example.securitypractice.dto.AuthenticationResponse;
import com.example.securitypractice.dto.RegisterRequest;
import com.example.securitypractice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Member member = Member.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        String jwtToken = jwtService.generateToken(member);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getLoginId(),
                       request.getPassword()
               )
        );

        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new UsernameNotFoundException("login error"));

        String jwtToken = jwtService.generateToken(member);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
