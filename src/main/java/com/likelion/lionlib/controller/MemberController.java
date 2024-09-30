package com.likelion.lionlib.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.LoanResponse;
import com.likelion.lionlib.dto.MemberResponse;
import com.likelion.lionlib.dto.ProfileRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.service.LoanService;
import com.likelion.lionlib.service.MemberService;
import com.likelion.lionlib.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
	private final MemberService memberService;
	private final LoanService loanService;
	private final ReservationService reservationService;

	// 회원 정보 조회
	@GetMapping("/members/{memberId}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {
		log.info("Request GET member with ID: {}", memberId);
		MemberResponse memberResponse = memberService.findMember(memberId);
		return ResponseEntity.ok(memberResponse);
	}

	// 프로필 수정
	@PutMapping("/members")
	public ResponseEntity<MemberResponse> putMember(Authentication authentication,
		@RequestBody ProfileRequest updateRequest) {
		Long memberId = ((CustomUserDetails)authentication.getPrincipal()).getId();
		log.info("Request PUT update member with ID: {}", memberId);
		MemberResponse updatedMember = memberService.updateMember(memberId, updateRequest);
		return ResponseEntity.ok(updatedMember);
	}

	// 사용자의 대출 목록 조회
	@GetMapping("/members/loans")
	public ResponseEntity<List<LoanResponse>> getLoansByMemberId(
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		log.info("Request GET loans for member with ID: {}", customUserDetails.getId());
		List<LoanResponse> loans = loanService.getLoansByMemberId(customUserDetails.getId());
		log.info("Response GET loans for member: {}", loans);
		return ResponseEntity.ok(loans);
	}

	@GetMapping("/members/reservations")
	public ResponseEntity<List<ReservationResponse>> getMemberReservation(
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		log.info("Request GET a reservation: {}", customUserDetails.getId());
		return ResponseEntity.ok(reservationService.getMemberReservation(customUserDetails.getId()));
	}
}