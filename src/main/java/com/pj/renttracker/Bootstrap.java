package com.pj.renttracker;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.renttracker.model.Contract;
import com.pj.renttracker.model.ContractRent;
import com.pj.renttracker.service.ContractService;

@Component
public class Bootstrap {

	@Autowired private ContractService contractService;
	
	public void run() {
		for (Contract contract : contractService.getAllContracts()) {
			contract = contractService.getContract(contract.getId());
			
			Date referenceDate = newDate();
			if (!contract.isRentDate(referenceDate)) {
				referenceDate = contract.getPreviousRentDate(newDate());
			}
			while (referenceDate != null) {
				if (!contract.hasRentWithRentDate(referenceDate)) {
					ContractRent rent = new ContractRent();
					rent.setParent(contract);
					rent.setAmount(contract.getRentalAmount());
					rent.setRentDate(referenceDate);
					contractService.save(rent);
					referenceDate = contract.getPreviousRentDate(referenceDate);
				} else {
					referenceDate = null;
				}
			}
		}
	}
	
	Date newDate() {
		return DateUtils.truncate(new Date(), Calendar.DATE);
	}
	
}
