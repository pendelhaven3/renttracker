package com.pj.renttracker;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractRent;
import com.pj.renttracker.service.ContractService;
import com.pj.renttracker.util.DateUtil;

@RunWith(MockitoJUnitRunner.class)
public class BootstrapTest {

	@Mock private ContractService contractService;
	
	private Bootstrap bootstrap;
	
	@Before
	public void setUp() {
		bootstrap = spy(Bootstrap.class);
		ReflectionTestUtils.setField(bootstrap, "contractService", contractService);
	}
	
	@Test
	public void run_createsContractRentOneMonthAfterStartDate() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("10/25/2015"));
		
		bootstrap.run();
		
		verify(contractService).save(argThat(new ArgumentMatcher<ContractRent>() {

			@Override
			public boolean matches(Object argument) {
				ContractRent rent = (ContractRent)argument;
				return rent.getRentDate().equals(DateUtil.toDate("10/25/2015"))
						&& rent.getAmount().equals(contract.getRentalAmount());
			}
		}));
	}
	
	@Test
	public void run_doesNotCreateContractRentOneMonthAfterStartDateIfAlreadyExisting() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		ContractRent rent = new ContractRent();
		rent.setRentDate(DateUtil.toDate("10/25/2015"));
		contract.setRents(Arrays.asList(rent));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("10/25/2015"));
		
		bootstrap.run();
		
		verify(contractService, never()).save(any(ContractRent.class));;
	}
	
	@Test
	public void run_doesNotCreateContractRentLessThanOneMonthAfterStartDate() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("10/24/2015"));
		
		bootstrap.run();
		
		verify(contractService, never()).save(any(ContractRent.class));
	}
	
	@Test
	public void run_createsContractRentMoreThanOneMonthAfterStartDate() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("10/30/2015"));
		
		bootstrap.run();
		
		verify(contractService).save(argThat(new ArgumentMatcher<ContractRent>() {

			@Override
			public boolean matches(Object argument) {
				ContractRent rent = (ContractRent)argument;
				return rent.getRentDate().equals(DateUtil.toDate("10/25/2015"))
						&& rent.getAmount().equals(contract.getRentalAmount());
			}
		}));
	}

	@Test
	public void run_createsTwoContractRentsTwoMonthsAfterStartDate() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("11/30/2015"));
		
		bootstrap.run();
		
		ArgumentCaptor<ContractRent> rentsCaptor = ArgumentCaptor.forClass(ContractRent.class);
		verify(contractService, times(2)).save(rentsCaptor.capture());
		
		List<ContractRent> rents = rentsCaptor.getAllValues();
		assertEquals(DateUtil.toDate("11/25/2015"), rents.get(0).getRentDate());
		assertEquals(DateUtil.toDate("10/25/2015"), rents.get(1).getRentDate());
	}
	
	@Test
	public void run_createsContractRentTwoMonthsAfterStartDateAndFirstMonthRentAlreadyExisiting() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		ContractRent rent = new ContractRent();
		rent.setRentDate(DateUtil.toDate("10/25/2015"));
		contract.setRents(Arrays.asList(rent));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("11/30/2015"));
		
		bootstrap.run();
		
		ArgumentCaptor<ContractRent> rentsCaptor = ArgumentCaptor.forClass(ContractRent.class);
		verify(contractService).save(rentsCaptor.capture());
		
		List<ContractRent> rents = rentsCaptor.getAllValues();
		assertEquals(1, rents.size());
		assertEquals(DateUtil.toDate("11/25/2015"), rents.get(0).getRentDate());
	}
	
	@Test
	public void run_createsContractRentsFiveMonthsAfterStartDate() {
		Contract contract = new Contract();
		contract.setId(1L);
		contract.setRentalDate(25);
		contract.setStartDate(DateUtil.toDate("09/25/2015"));
		contract.setRentalAmount(new BigDecimal("3000"));
		
		when(contractService.getContract(1L)).thenReturn(contract);
		when(contractService.getAllContracts()).thenReturn(Arrays.asList(contract));
		when(bootstrap.newDate()).thenReturn(DateUtil.toDate("03/01/2016"));
		
		bootstrap.run();
		
		ArgumentCaptor<ContractRent> rentsCaptor = ArgumentCaptor.forClass(ContractRent.class);
		verify(contractService, times(5)).save(rentsCaptor.capture());
		
		List<ContractRent> rents = rentsCaptor.getAllValues();
		assertEquals(DateUtil.toDate("02/25/2016"), rents.get(0).getRentDate());
		assertEquals(DateUtil.toDate("01/25/2016"), rents.get(1).getRentDate());
		assertEquals(DateUtil.toDate("12/25/2015"), rents.get(2).getRentDate());
		assertEquals(DateUtil.toDate("11/25/2015"), rents.get(3).getRentDate());
		assertEquals(DateUtil.toDate("10/25/2015"), rents.get(4).getRentDate());
	}
	
}
