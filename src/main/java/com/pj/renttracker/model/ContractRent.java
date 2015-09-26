package com.pj.renttracker.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.pj.renttracker.util.FormatterUtil;

@Entity
public class ContractRent {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private Contract parent;
	
	private Date rentDate;
	private BigDecimal amount;
	
	@Column(columnDefinition = "boolean default false")
	private boolean paid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractRent other = (ContractRent) obj;
		return new EqualsBuilder()
				.append(id,  other.getId())
				.isEquals();
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getStatus() {
		return (paid) ? "Paid" : "Not Paid";
	}
	
	public String getPeriodDescription() {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(rentDate);
		startDateCalendar.add(Calendar.MONTH, -1);
		startDateCalendar.add(Calendar.DATE, 1);
		
		return new StringBuilder()
				.append(FormatterUtil.formatDate(startDateCalendar.getTime()))
				.append(" - " )
				.append(FormatterUtil.formatDate(rentDate))
				.toString();
	}
	
	@Override
	public String toString() {
		return getPeriodDescription();
	}
	
}
