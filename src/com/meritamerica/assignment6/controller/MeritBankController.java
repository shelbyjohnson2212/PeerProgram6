package com.meritamerica.assignment6.controller;

import java.util.List;

import javax.validation.Valid;

import com.meritamerica.assignment6.*;
import com.meritamerica.assignment6.exceptions.*;
import com.meritamerica.assignment6.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeritBankController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountHolderRepository accountHolderRepository;

	@Autowired
	private AccountHolderContactDetailsRepository accountHolderContactDetailsRepository;

	@Autowired
	private CheckingAccountRepository checkingAccountRepository;

	@Autowired
	private SavingsAccountRepository savingsAccountRepository;

	@Autowired
	private CDAccountRepository cdAccountRepository;

	@PostMapping("/AccountHolders")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountHolder addAccountHolder(@RequestBody @Valid AccountHolder accountHolder) {
		accountHolderRepository.save(accountHolder);
		return accountHolder;


	}
	
	@GetMapping(value="/AccountHolders")
	@ResponseStatus(HttpStatus.OK)
	public AccountHolder[] getAccountHolders() {
		return MeritBank.getAccountHolders();
	}
	
	@GetMapping(value="/AccountHolders/{id}") 
	public AccountHolder getAccountHolder(@PathVariable("id") long id) throws NotFoundException
	{
		AccountHolder account = MeritBank.getAccountHolder(id);

		if (account == null) {
			logger.error("No account exists");
			throw new NotFoundException("No account exists");
		}
	
		return account;
	}
	
	@PostMapping(value="/AccountHolders/{id}/CheckingAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CheckingAccount addChecking(@PathVariable("id") long id, @RequestBody CheckingAccount checking ) throws NotFoundException, ExceedsCombinedBalanceLimitException,
	NegativeAmountException
	{				
		AccountHolder account = this.getAccountHolder(id);
		
		if (checking.getBalance() < 0) {
			logger.warn("Negative amount exception");
			throw new NegativeAmountException();
		}
		
		account.addCheckingAccount(checking);
		
		return checking;
	}
	
	@GetMapping(value="/AccountHolders/{id}/CheckingAccounts")
	public CheckingAccount[] getChecking(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder account = this.getAccountHolder(id);
		
		return account.getCheckingAccounts();
	}
	
	@PostMapping(value="/AccountHolders/{id}/SavingsAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public SavingsAccount addSaving(@PathVariable("id") long id, @RequestBody SavingsAccount savings ) throws NotFoundException, ExceedsCombinedBalanceLimitException,
	NegativeAmountException
	{
				
		AccountHolder account = this.getAccountHolder(id);
		
		if (savings.getBalance() < 0) {
			logger.warn("Negative amount exception");
			throw new NegativeAmountException();
		}
		
		account.addSavingsAccount(savings);
		
		return savings;
	}
	
	@GetMapping(value="/AccountHolders/{id}/SavingsAccounts")
	public SavingsAccount[] getSavings(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder account = this.getAccountHolder(id);
		return account.getSavingsAccounts();
	}
	
	@PostMapping(value="/AccountHolders/{id}/CDAccounts")
	@ResponseStatus(HttpStatus.CREATED)
	public CDAccount addCDAccount(@PathVariable("id") long id, @RequestBody CDAccount CDAccount ) throws NotFoundException, ExceedsCombinedBalanceLimitException,
	NegativeAmountException, ExceedsFraudSuspicionLimitException, MissingFieldException
	{			
		AccountHolder account = this.getAccountHolder(id);
		
		if (CDAccount.getBalance() < 0) {
			logger.warn("Negative amount exception");
			throw new NegativeAmountException();
		}
		
		// need explaining on CDAccount.getInterestRate() >=1
		if (CDAccount.getInterestRate() <= 0 || CDAccount.getTerm() <= 0 || CDAccount.getInterestRate() >= 1) {
			logger.warn("Missing interest rate or term");
			throw new MissingFieldException("Missing interest rate or term");
		}			

		account.addCDAccount(CDAccount);
		
		return CDAccount;
	}
	
	@GetMapping(value="/AccountHolders/{id}/CDAccounts")
	public CDAccount[] getCDAccounts(@PathVariable("id") long id) throws NotFoundException {
		AccountHolder account = this.getAccountHolder(id);
		
		return account.getCDAccounts();
	}
	@CrossOrigin
	@PostMapping("/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering createCDOffering(@RequestBody CDOffering offering) throws MissingFieldException {
		
		// need more question on || offering.getInterestRate() >= 1)
//		if (offering.getInterestRate() <= 0 || offering.getTerm() <= 0 || offering.getInterestRate() >= 1) {
//			logger.warn("Missing interest rate or term");
//			throw new MissingFieldException("Missing interest rate or term");
//		}	
		
		MeritBank.addCDOffering(offering);
		return offering;
	}
	@CrossOrigin
	@GetMapping("/CDOfferings")
	public List<CDOffering>getCDOfferings() throws NotFoundException {
		List<CDOffering> CDOfferings = MeritBank.getCDOfferings();
	    return CDOfferings;
	}
	
}