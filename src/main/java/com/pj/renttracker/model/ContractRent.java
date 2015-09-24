package com.pj.renttracker.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ContractRent {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Contract parent;
	
	private Date rentDate;
	private BigDecimal amount;

	public Contract getParent() {
		return parent;
	}

	public void setParent(Contract parent) {
		this.parent = parent;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
