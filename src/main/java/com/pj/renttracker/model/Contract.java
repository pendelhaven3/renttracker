package com.pj.renttracker.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

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
	private Integer rentalDate;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy("paymentDate DESC")
	private List<ContractPayment> payments;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@OrderBy("rentDate DESC")
	private List<ContractRent> rents;

	@Transient
	private Date nextRentalDate;
	
	public Date getNextRentalDate() {
		return nextRentalDate;
	}
	
	@PostLoad
	public void calculateNextRentalDate() {
		Calendar calendar = Calendar.getInstance();
		int currentDay = calendar.get(Calendar.DATE);
		if (currentDay > rentalDate) {
			calendar.add(Calendar.MONTH, 1);
		}
		calendar.set(Calendar.DATE, rentalDate);
		nextRentalDate = calendar.getTime();
	}
	
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

	public Integer getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(Integer rentalDate) {
		this.rentalDate = rentalDate;
	}

	public List<ContractRent> getRents() {
		return rents;
	}

	public void setRents(List<ContractRent> rents) {
		this.rents = rents;
	}

}
