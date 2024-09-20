package com.likelion.lionlib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.lionlib.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByBookIdAndMemberId(Long bookId, Long memberId);

	List<Reservation> findAllByMemberId(Long memberId);

	Long countAllByBookId(Long bookId);
}
