package com.pj.renttracker.model.search;

import java.util.HashSet;
import java.util.Set;

public class ContractSearchCriteria {

	private Set<Integer> rentalDates = new HashSet<>();

	public Set<Integer> getRentalDates() {
		return rentalDates;
	}

	public void setRentalDates(Set<Integer> rentalDates) {
		this.rentalDates = rentalDates;
	}
	
}
