package com.pj.renttracker.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.renttracker.dao.ContractDao;
import com.pj.renttracker.dao.ContractPaymentDao;
import com.pj.renttracker.dao.ContractRentDao;
import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractPayment;
import com.pj.renttracker.model.ContractRent;
import com.pj.renttracker.model.search.ContractSearchCriteria;
import com.pj.renttracker.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired private ContractDao contractDao;
	@Autowired private ContractPaymentDao contractPaymentDao;
	@Autowired private ContractRentDao contractRentDao;
	
	@Override
	public List<Contract> getAllContracts() {
		return contractDao.getAll();
	}

	@Transactional
	@Override
	public void save(Contract contract) {
		contractDao.save(contract);
	}

	@Override
	public Contract getContract(long id) {
		Contract contract = contractDao.get(id);
		contract.setRents(contractRentDao.findAllByContract(contract));
		contract.setPayments(contractPaymentDao.findAllByContract(contract));
		return contract;
	}

	@Transactional
	@Override
	public void save(ContractPayment payment) {
		contractPaymentDao.save(payment);
		
		if (payment.getPaymentType().isRent()) {
			if (isTotalPaymentsEqualToRentAmount(payment.getRent())) {
				markRentAsPaid(payment.getRent());
			}
		}
	}

	private boolean isTotalPaymentsEqualToRentAmount(ContractRent rent) {
		return getTotalRentPayment(rent).compareTo(rent.getAmount()) >= 0;
	}

	private BigDecimal getTotalRentPayment(ContractRent rent) {
		BigDecimal total = BigDecimal.ZERO;
		for (ContractPayment payment : contractPaymentDao.findAllByRent(rent)) {
			total = total.add(payment.getAmount());
		}
		return total;
	}

	@Transactional
	@Override
	public void delete(ContractPayment payment) {
		contractPaymentDao.delete(payment);
	}

	@Override
	public List<Contract> findContractsWithUpcomingRent() {
		ContractSearchCriteria criteria = new ContractSearchCriteria();
		criteria.setRentalDates(getCurrentDayAndNextSevenDays());
		return contractDao.search(criteria);
	}

	private static Set<Integer> getCurrentDayAndNextSevenDays() {
		final int NUMBER_OF_DAYS = 7;
		Set<Integer> dates = new HashSet<>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i <= NUMBER_OF_DAYS; i++) {
			int date = calendar.get(Calendar.DATE);
			dates.add(date);
			if (i < NUMBER_OF_DAYS && isDateTheLastDayOfMonth(date, calendar)) {
				while (date <= 31) {
					dates.add(date++);
				}
			}
			calendar.add(Calendar.DATE, 1);
		}
		return dates;
	}

	private static int getLastDayOfMonth(Calendar calendar) {
		return calendar.getActualMaximum(Calendar.DATE);
	}

	private static boolean isDateTheLastDayOfMonth(int date, Calendar calendar) {
		return date == getLastDayOfMonth(calendar);
	}

	@Transactional
	@Override
	public void save(ContractRent rent) {
		contractRentDao.save(rent);
	}

	@Override
	public List<ContractRent> findAllRentsByContract(Contract contract) {
		return contractRentDao.findAllByContract(contract);
	}

	@Transactional
	@Override
	public void markRentAsPaid(ContractRent rent) {
		rent.setPaid(true);
		save(rent);
	}

	@Transactional
	@Override
	public void markRentAsUnpaid(ContractRent rent) {
		rent.setPaid(false);
		save(rent);
	}
	
}
