package com.pj.renttracker.model;

import java.util.Date;

public class ContractDue {

	private Contract parent;
	private Date dueDate;

	public Contract getParent() {
		return parent;
	}

	public void setParent(Contract parent) {
		this.parent = parent;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
