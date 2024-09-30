package com.likelion.lionlib.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberResponse {
	private Long id;
	private Long profileId;
	private String email;
	private String password;
	private String name;
	private Role role;

	public static MemberResponse fromEntity(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.email(member.getEmail())
			.name(member.getName())
			.password(member.getPassword())
			.profileId(member.getProfile().getId())
			.role(member.getRole())
			.build();

	}
}
