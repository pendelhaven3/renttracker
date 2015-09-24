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
	
}
