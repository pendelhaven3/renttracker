package com.pj.renttracker.model.search;

import java.util.HashSet;
import java.util.Set;

public class ContractSearchCriteria {

	private Set<Integer> dueDates = new HashSet<>();

	public Set<Integer> getDueDates() {
		return dueDates;
	}

	public void setDueDates(Set<Integer> dueDates) {
		this.dueDates = dueDates;
	}
	
}
