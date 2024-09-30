package com.likelion.lionlib.service;

import org.springframework.stereotype.Service;

import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Profile;
import com.likelion.lionlib.domain.Role;
import com.likelion.lionlib.dto.MemberResponse;
import com.likelion.lionlib.dto.ProfileRequest;
import com.likelion.lionlib.repository.MemberRepository;
import com.likelion.lionlib.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final ProfileRepository profileRepository;

	private final GlobalService globalService;

	// Member 생성
	public void createMember(String email, String encodedPassword) {
		Member newMember = Member.builder()
			.email(email)
			.password(encodedPassword)
			.role(Role.BABYLION) // 역할 설정
			.build();
		memberRepository.save(newMember);

		Profile profile = Profile.builder()
			.member(newMember)
			.build();
		profileRepository.save(profile);
	}

	// 로그인 처리
	public boolean login(String email, String password) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new RuntimeException("Invalid email or password"));

		return member.getPassword().equals(password);
	}

	// 회원 정보 조회
	public MemberResponse findMember(Long memberId) {
		Member member = globalService.findMemberById(memberId);
		return MemberResponse.fromEntity(member);
	}

	// 프로필 수정
	public MemberResponse updateMember(Long memberId, ProfileRequest profileRequest) {
		Member member = globalService.findMemberById(memberId);

		Profile profile = profileRepository.findByMember(member)
			.orElseThrow(() -> new RuntimeException("Profile not found"));

		member.setName(profileRequest.getName());

		profile.setBio(profileRequest.getBio());
		profile.setGeneration(profileRequest.getGeneration());
		profile.setMajor(profileRequest.getMajor());
		profile.setImageUrl(profileRequest.getImageUrl());
		profile.setGithubLink(profileRequest.getGithubLink());

		memberRepository.save(member);
		profileRepository.save(profile);

		return MemberResponse.fromEntity(member);
	}
}