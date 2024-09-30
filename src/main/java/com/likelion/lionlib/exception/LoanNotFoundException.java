package com.likelion.lionlib.exception;

public class LoanNotFoundException extends RuntimeException{
	public LoanNotFoundException() {
		super("Loan을 찾을 수 없습니다.");
	}

	public LoanNotFoundException(Long memberId) {
		super("Member " + memberId + " 이 대여한 정보를 찾을 수 없습니다.");
	}
}
