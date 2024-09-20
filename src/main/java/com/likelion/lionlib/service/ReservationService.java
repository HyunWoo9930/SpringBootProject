package com.likelion.lionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.BookReservationResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.repository.BookRepository;
import com.likelion.lionlib.repository.MemberRepository;
import com.likelion.lionlib.repository.ReservationRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final BookRepository bookRepository;
	private final MemberRepository memberRepository;

	public ReservationService(ReservationRepository reservationRepository, BookRepository bookRepository,
		MemberRepository memberRepository) {
		this.reservationRepository = reservationRepository;
		this.bookRepository = bookRepository;
		this.memberRepository = memberRepository;
	}

	public void reserve(ReservationRequest reservationRequest) {
		reservationRepository.findByBookIdAndMemberId(reservationRequest.getBookId(), reservationRequest.getMemberId())
			.ifPresent(reservation -> {
				throw new IllegalArgumentException("이미 예약한 책입니다.");
			});

		Book book = bookRepository.findById(reservationRequest.getBookId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));

		Member member = memberRepository.findById(reservationRequest.getMemberId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		if (book.getQuantity() == 0) {
			throw new IllegalArgumentException("재고가 없는 책입니다.");
		} else {
			book.setQuantity(book.getQuantity() - 1);
			bookRepository.save(book);
			reservationRepository.save(Reservation.builder()
				.book(book)
				.member(member)
				.build());
		}
	}

	public void cancel(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
		Book book = reservation.getBook();
		book.setQuantity(book.getQuantity() + 1);
		bookRepository.save(book);
		reservationRepository.delete(reservation);
	}

	public ReservationResponse getReservation(Long reservationsId) {
		Reservation reservation = reservationRepository.findById(reservationsId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));

		return ReservationResponse.fromEntity(reservation);
	}

	public List<ReservationResponse> getMemberReservation(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

		return reservationRepository.findAllByMemberId(memberId)
			.stream()
			.map((ReservationResponse::fromEntity))
			.toList();
	}

	public BookReservationResponse getBookReservation(Long bookId) {
		bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책입니다."));
		return BookReservationResponse.builder()
			.count(reservationRepository.countAllByBookId(bookId))
			.build();
	}
}
