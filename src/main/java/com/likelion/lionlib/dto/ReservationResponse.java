package com.likelion.lionlib.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.likelion.lionlib.domain.Reservation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReservationResponse {
	private Long bookId;

	public static ReservationResponse fromEntity(Reservation reservation) {
		return ReservationResponse.builder()
			.bookId(reservation.getBook().getId())
			.build();
	}
}
