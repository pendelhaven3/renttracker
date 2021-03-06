package com.pj.renttracker.model;

public enum PaymentType {

	ADVANCE("Advance"), DEPOSIT("Deposit"), RENT("Rent");
	
	private String description;
	
	private PaymentType(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	public boolean isRent() {
		return this == RENT;
	}

	public boolean isDeposit() {
		return this == DEPOSIT;
	}
	
	public boolean isAdvance() {
		return this == ADVANCE;
	}
	
}
