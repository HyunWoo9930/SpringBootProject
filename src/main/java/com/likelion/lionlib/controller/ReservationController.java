package com.likelion.lionlib.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.lionlib.dto.BookReservationResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.service.ReservationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ReservationController {
	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping("/reservations")
	public ResponseEntity<String> reserve(@RequestBody ReservationRequest reservationRequest) {
		log.info("Request POST a reservation: {}", reservationRequest);
		reservationService.reserve(reservationRequest);
		return ResponseEntity.ok("reservation success");
	}

	@DeleteMapping("/reservations/{reservationId}")
	public ResponseEntity<String> cancel(@PathVariable("reservationId") Long reservationId) {
		log.info("Request DELETE a reservation: {}", reservationId);
		reservationService.cancel(reservationId);
		return ResponseEntity.ok("cancel success");
	}

	@GetMapping("/reservations/{reservationsId}")
	public ResponseEntity<ReservationResponse> getReservation(@PathVariable("reservationsId") Long reservationsId) {
		log.info("Request GET a reservation: {}", reservationsId);
		return ResponseEntity.ok(reservationService.getReservation(reservationsId));
	}

	@GetMapping("/members/{memberId}/reservations")
	public ResponseEntity<List<ReservationResponse>> getMemberReservation(@PathVariable("memberId") Long memberId) {
		log.info("Request GET a reservation: {}", memberId);
		return ResponseEntity.ok(reservationService.getMemberReservation(memberId));
	}

	@GetMapping("/books/{bookId}/reservations")
	public ResponseEntity<BookReservationResponse> getBookReservation(@PathVariable("bookId") Long bookId) {
		log.info("Request GET a reservation: {}", bookId);
		return ResponseEntity.ok(reservationService.getBookReservation(bookId));
	}
}
