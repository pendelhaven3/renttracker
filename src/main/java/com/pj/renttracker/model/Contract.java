package com.pj.renttracker.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Contract {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Tenant tenant;
	
	@OneToOne
	private Unit unit;
	
	private Date startDate;
	private BigDecimal rentalAmount;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy("paymentDate DESC")
	private List<ContractPayment> payments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(BigDecimal rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public List<ContractPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<ContractPayment> payments) {
		this.payments = payments;
	}

}
