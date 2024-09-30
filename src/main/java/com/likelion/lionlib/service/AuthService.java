package com.likelion.lionlib.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.likelion.lionlib.dto.SignupRequest;
import com.likelion.lionlib.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final MemberRepository memberRepository;

	private final MemberService memberService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// 회원가입 처리
	public void joinProcess(SignupRequest signupRequest) {
		if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
			throw new RuntimeException("이미 존재하는 이메일입니다.");
		}
		memberService.createMember(signupRequest.getEmail(), bCryptPasswordEncoder.encode(signupRequest.getPassword()));
	}
}