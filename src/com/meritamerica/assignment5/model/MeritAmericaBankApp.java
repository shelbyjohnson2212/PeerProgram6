package com.meritamerica.assignment5.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * This class has a public modifier and the main method which is the entry point of Merit America Bank. 
 * It calls and uses other classes and methods.
 * @author Huy Cam
 */

public class MeritAmericaBankApp {
	public static void main(String[] args) {
		try {
			// read files
//			boolean result = MeritBank.readFromFile("testRead.txt");
//			
//			System.out.println(result);
//			// test getting CDOfferings data
//			CDOffering[] offerings = MeritBank.getCDOfferings();
//			for (CDOffering o: offerings) {
//				System.out.println(o.getTerm() + ", " + o.getInterestRate());
//			}
//			
//			// test Account holder and checking Acount
//			AccountHolder[] acc = MeritBank.getAccountHolders();
//			for (AccountHolder a: acc) {
//				System.out.println(a.getFirstName() + ", " + a.getMiddleName() + ", " + a.getLastName() + ", " + a.getSSN());
//				CheckingAccount[] checkings = a.getCheckingAccounts();
//				for (CheckingAccount checking: checkings) {
//					if (checking == null) {
//						break;
//					}
//					System.out.println(checking.getAccountNumber() + ", " + checking.getBalance() + "," + checking.getInterestRate());
//				}
//			}
			
			AccountHolder accountHolder = new AccountHolder(
	            	"Sadiq",
	            	"",
	            	"Manji",
	            	"123456789");
			CDAccount cda = new CDAccount();
			cda.setBalance(50000);
			cda.setInterestRate(3.0);
			accountHolder.addCDAccount(cda);
			System.out.println("Terms :" + cda.getTerm());
//			/*
//			 * Transaction process check
//			 */
//			accountHolder.addSavingsAccount(300);
//	    	
//			accountHolder.addCheckingAccount(500);
//			System.out.println("hi");
//			DepositTransaction dt1 = new DepositTransaction(accountHolder.getSavingsAccounts()[0], 50, new Date());
//			DepositTransaction dt2 = new DepositTransaction(accountHolder.getSavingsAccounts()[0], 500, new Date());
//			WithdrawTransaction wd1 = new WithdrawTransaction(accountHolder.getCheckingAccounts()[0], 150, new Date());
//			BankAccount checking = accountHolder.getCheckingAccounts()[0];
//			
//			MeritBank.processTransaction(dt1);
//			MeritBank.processTransaction(dt2);
//			MeritBank.processTransaction(wd1);
//			
//			SavingsAccount sa1 = accountHolder.getSavingsAccounts()[0];
//			ArrayList<Transaction> trans = sa1.getTransactions();
//			Transaction tran = trans.get(1);
//			Transaction tran2 = trans.get(2);
//			System.out.println(tran.toString());
//			System.out.println(tran2);
//			
//			System.out.println(wd1.getTargetAccount().getBalance());
			
			
			/*
			 * fraud queue check
			 */
			
//			Transaction q = MeritBank.fraudQueue.getTransaction() ;
			
//			System.out.println(q.getSize());
//			while ((q = MeritBank.fraudQueue.getTransaction()) != null) {
//				if (q instanceof DepositTransaction) {
//					System.out.println("Deposit transaction");
//					System.out.println(q.getAmount());
//					System.out.println(q.getTargetAccount().getAccountNumber());
//				}
//			}
			
//			System.out.println("Deposit transaction");
//			System.out.println(q.getAmount());
//			System.out.println(q.getTargetAccount().getAccountNumber());
			
			CDAccount acc = new CDAccount();
			acc.setBalance(1000);
			acc.setInterestRate(2.5);
			acc.setTerm(3);
			System.out.println(acc.getTerm() + " " + acc.getBalance() + " " + acc.getInterestRate());
	    	
//	        
//	        System.out.println(cdAccount.withdraw(500.0));
		} catch(Exception e) {
			e.printStackTrace();
			
		}
//		
		// test writer
//		MeritBank.writeToFile("writingTest.txt");
		
		
		/*
		 * Testing other features
		 */
		 //Instantiating the SimpleDateFormat class
//	      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//	      //Parsing the given String to Date object
//	      try {
//	    	  Date date = formatter.parse("01/01/2020");
//	    	  System.out.println("Date object value: "+date);
//	      } catch(Exception e) {
//	    	  e.printStackTrace();
//	      }
    	
		// Used Class.method to read data
//    	boolean result = MeritBank.readFromFile("test.txt");
		
//    	System.out.println(result);
//		try {
//			AccountHolder accountHolder = new AccountHolder(
//	            	"Sadiq",
//	            	"",
//	            	"Manji",
//	            	"123456789");
//	    	SavingsAccount savingsAccount = accountHolder.addSavingsAccount(50);
//	    	System.out.println("hello3");
//	    	System.out.println(savingsAccount.withdraw(500.00));
//	    	
//		} catch(Exception e) {
//			System.out.println(e);
//		}
		
	}
	     
}