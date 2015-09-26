package com.pj.renttracker.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.pj.renttracker.util.DateUtil;

public class ContractTest {

	private Contract contract;
	
	@Before
	public void setUp() {
		contract = new Contract();
	}
	
	@Test
	public void getPreviousRentDate_referenceDateLessThanOneMonthFromStartDate() {
		contract.setStartDate(DateUtil.toDate("07/25/2015"));
		contract.setRentalDate(25);
		assertNull(contract.getPreviousRentDate(DateUtil.toDate("08/24/2015")));
	}
	
	@Test
	public void getPreviousRentDate_referenceDateOneMonthFromStartDate() {
		contract.setStartDate(DateUtil.toDate("07/25/2015"));
		contract.setRentalDate(25);
		assertNull(contract.getPreviousRentDate(DateUtil.toDate("08/25/2015")));
	}
	
	@Test
	public void getPreviousRentDate_currentDayEqualToRentalDate() {
		contract.setStartDate(DateUtil.toDate("07/25/2015"));
		contract.setRentalDate(25);
		assertEquals(DateUtil.toDate("08/25/2015"),
				contract.getPreviousRentDate(DateUtil.toDate("09/25/2015")));
	}
	
	@Test
	public void getPreviousRentDate_currentDayGreaterThanRentalDate() {
		contract.setStartDate(DateUtil.toDate("07/25/2015"));
		contract.setRentalDate(25);
		assertEquals(DateUtil.toDate("10/25/2015"),
				contract.getPreviousRentDate(DateUtil.toDate("10/30/2015")));
	}

	@Test
	public void getPreviousRentDate_currentDayLessThanRentalDate() {
		contract.setStartDate(DateUtil.toDate("07/25/2015"));
		contract.setRentalDate(25);
		assertEquals(DateUtil.toDate("09/25/2015"),
				contract.getPreviousRentDate(DateUtil.toDate("10/20/2015")));
	}
	
	@Test
	public void isRentDate() {
		contract.setRentalDate(25);
		assertTrue(contract.isRentDate(DateUtil.toDate("10/25/2015")));
		assertFalse(contract.isRentDate(DateUtil.toDate("10/24/2015")));
	}
	
}
