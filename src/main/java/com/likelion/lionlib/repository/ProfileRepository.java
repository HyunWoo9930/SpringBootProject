package com.likelion.lionlib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByMember(Member member);
}
