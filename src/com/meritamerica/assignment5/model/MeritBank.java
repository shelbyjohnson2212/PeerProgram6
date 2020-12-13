package com.meritamerica.assignment5.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.meritamerica.assignment5.exceptions.ExceedsAvailableBalanceException;
import com.meritamerica.assignment5.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment5.exceptions.NegativeAmountException;


public class MeritBank {
	private static long accIndex = 0;
	private static AccountHolder[] accountHolders = new AccountHolder[10];
	private static CDOffering[] CDOfferings = new CDOffering[0];
	
	private static int numbOfAccountHolder = 0;
	public static FraudQueue fraudQueue = new FraudQueue();
	
	public static void addAccountHolder(AccountHolder accountHolder) {
		MeritBank.numbOfAccountHolder++;
		
		if (MeritBank.numbOfAccountHolder >= MeritBank.accountHolders.length) {
			AccountHolder[] accounts = Arrays.copyOf(MeritBank.accountHolders, MeritBank.accountHolders.length * 2);
			MeritBank.accountHolders = accounts;
		}
		
		MeritBank.accountHolders[MeritBank.numbOfAccountHolder - 1] = accountHolder;
		
	}
	
	public static void addCDOffering(CDOffering offering) {
		CDOffering[] offerings = Arrays.copyOf(MeritBank.CDOfferings, MeritBank.CDOfferings.length + 1);
		offerings[offerings.length - 1] = offering;
		MeritBank.CDOfferings = offerings;
	}
	
	public static AccountHolder getAccountHolder(long id) {
		for (AccountHolder account : MeritBank.accountHolders) {
			if (account == null) {
				return null;
			}
			if (account.getId() == id) {
				return account;
			}
		}
		
		return null;
		
		
	}
	public static BankAccount findAccount(long ID) {
		if (accountHolders != null) {
			for (int i = 0; i < accountHolders.length; i++) {
				if (accountHolders[i] == null) {
					break;
				}
				BankAccount acc = accountHolders[i].findAccount(ID);
				if (acc != null) {
					return acc;
				}
			}
		}
		
		return null;
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}
	
	public static String decimalFormat(double numb) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(numb);
	}
	
	public static String formatNumber(double d) {
	    if(d == (int) d)
	        return String.format("%d",(int)d);
	    else
	        return String.format("%s",d);
	} 
	
	/*
	 * Write Merit Bank data to file
	 */
	public static boolean writeToFile(String fileName) {
		StringBuilder data = new StringBuilder();
		// add NextNumber
		data.append(Long.toString(MeritBank.getNextAccountNumber()) + "\n") ;
		// add account CDOffering --------------------------------------
		// add number of CDOfferings
		data.append(Integer.toString(MeritBank.CDOfferings.length)  + "\n");
		
		// add CDOffering
		for (CDOffering offering: CDOfferings) {
			data.append(offering.getTerm() + "," + offering.getInterestRate() + "\n");
		}
		
		// add account holder -----------------------------------------
		// add number of account holders
		data.append(Integer.toString(MeritBank.accountHolders.length) + "\n");
		
		for (AccountHolder accountHolder: MeritBank.accountHolders) {
			// add account holder information
			data.append(accountHolder.getFirstName() + "," + accountHolder.getMiddleName() + "," 
			+ accountHolder.getLastName()+ "," + accountHolder.getSSN() + "\n");
			// add checkings
			data.append(MeritBank.addCheckingData(accountHolder)) ;
			// add savings
			data.append(MeritBank.addSavingData(accountHolder));
			// add CDAccount
			data.append(MeritBank.addCDData(accountHolder));
		}
	
		// start writing into file
		try {
			FileWriter writer = new FileWriter(fileName);
	        writer.write(data.toString());
	        writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	public static boolean readFromFile(String fileName) {
		 try {
	            BufferedReader reader = new BufferedReader(new FileReader(fileName));
	            String line = "";
	            int numOfCDOfferings;
	            int numOfAccountHolders;
	            int numOfCheckings;
	            int numbOfSavings;
	            int numbOfCDAccounts;
	            // Process next account number
	            line = reader.readLine();
	            MeritBank.setNextAccountNumber(Integer.parseInt(line));
	            
	            // CD offerings-----------------------------------------------------------------
	            line = reader.readLine();
	            numOfCDOfferings = Integer.parseInt(line);
	            
	            // create a array of CDOffering
	            CDOfferings = new CDOffering[numOfCDOfferings];
	            // read all the CDOfferings in and create all of them
	            for (int i = 0; i < numOfCDOfferings; i++) {
	            	line = reader.readLine().trim(); // trim() is a String method to get rid of white space before and after a line.
	            	CDOfferings[i] = CDOffering.readFromString(line);
	            }
	            
	            // Account holders--------------------------------------------------------------
	            line = reader.readLine();
	            numOfAccountHolders = Integer.parseInt(line);
	            
	            accountHolders = new AccountHolder[numOfAccountHolders];
	            
	            for (int j = 0; j < numOfAccountHolders; j++) {
	            	try {
	            		line = reader.readLine();
		            	// create account holder 
		            	AccountHolder acc = AccountHolder.readFromString(line);
		            	// process checking account
		            	line = reader.readLine();
		            	numOfCheckings = Integer.parseInt(line);
		            	
		            	acc.createCheckingArray(numOfCheckings);
		            	
		            	for (int x = 0; x < numOfCheckings; x++) {
		            		CheckingAccount checkAcc = CheckingAccount.readFromString(reader.readLine());
		            		// process transaction inside the account
		            		MeritBank.readTransactions(reader, checkAcc);
		            		
		            		
		            		acc.addCheckingAccount(checkAcc);
		            		
		            	}
		            	// process saving account
		            	line = reader.readLine();
		            	numbOfSavings = Integer.parseInt(line);
		            	
		            	acc.createSavingArray(numbOfSavings);
		            	
		            	for (int y = 0; y < numbOfSavings; y++) {
		            		SavingsAccount savingAcc = SavingsAccount.readFromString(reader.readLine());
		            		
		            		// process transaction inside the account
		            		MeritBank.readTransactions(reader, savingAcc);
		            		
		            		acc.addSavingsAccount(savingAcc);
		            	}	            	
		            	
		            	// process CD account
		            	numbOfCDAccounts = Integer.parseInt(reader.readLine());
		            	
		            	acc.createCDAccounts(numbOfCDAccounts);            	
		            	
		            	for (int z = 0; z < numbOfCDAccounts; z++) {
		            		CDAccount CDOAcc = CDAccount.readFromString(reader.readLine());
		            		
		            		// process transaction inside the account
		            		MeritBank.readTransactions(reader, CDOAcc);
		            		
		            		acc.addCDAccount(CDOAcc);
		            	}
		            	
		            	// add account holder to the array
		            	accountHolders[j] = acc;
		       
	            	} catch(Exception e) {
	            		e.printStackTrace();
	            		System.out.println("something worng");
	            		return false;
	            	}	
	            }
	            
	            
	            // read FraudQueue
            	MeritBank.readFraudQueue(reader);
	            
	            reader.close();
	        } catch (Exception e) {
	        	System.out.println("Exception");
	        	e.printStackTrace();
	            return false;
	        }
		 
		 return true;
	}
	
	private static void readFraudQueue(BufferedReader reader) throws IOException, ParseException {
		int pendingNum = Integer.parseInt(reader.readLine());
	
		for (int i= 0; i < pendingNum; i++) {
			MeritBank.fraudQueue.addTransaction(readTransactionType(reader.readLine()));
		}
	}
	
	private static Transaction readTransactionType(String line) throws ParseException {
		String[] datas = line.split(",");
		
		// Create a date formatter
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		int sourceID = Integer.parseInt(datas[0]);
		int targetID = Integer.parseInt(datas[1]);
		BankAccount targetAcc = MeritBank.findAccount(targetID);
		double amount = Integer.parseInt(datas[2]);
		Date date = formatter.parse(datas[3]);
		
		// if this is not a transfer transaction
		if (sourceID != -1) {
			if (amount >= 0) {
				return new DepositTransaction(targetAcc, amount, date);
			} else {
				return new WithdrawTransaction(targetAcc, amount, date);
			}
		} else {
			// if this is a transfer transaction
			BankAccount sourceAcc = MeritBank.findAccount(sourceID);
			return new TransferTransaction(sourceAcc, targetAcc, amount, date);
		}
	}
	
	private static void readTransactions(BufferedReader reader, BankAccount acc) throws IOException, ParseException, 
	  ExceedsFraudSuspicionLimitException, NegativeAmountException, ExceedsAvailableBalanceException {
		int numOfTransaction = Integer.valueOf(reader.readLine()); // number of transactions
		
		for (int i = 0; i < numOfTransaction; i++) {
			String line = reader.readLine();
			String[] datas = line.split(",");
			
			// Create a date formatter
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			
			int sourceID = Integer.parseInt(datas[0]);
			int targetID = Integer.parseInt(datas[1]);
			double amount = Double.parseDouble(datas[2]);
			Date date = formatter.parse(datas[3]);
			
			// if this is not a transfer transaction
			if (sourceID != -1) {
				if (amount >= 0) {
					acc.addTransaction(new DepositTransaction(acc, amount, date));
				} else {
					acc.addTransaction(new WithdrawTransaction(acc, amount, date));
				}
			} else {
				// if this is a transfer transaction
				BankAccount sourceAcc = MeritBank.findAccount(sourceID);
				acc.addTransaction(new TransferTransaction(sourceAcc, acc, amount, date));
			}
		}
	}
	
	private static String addSavingData(AccountHolder acc) {
		StringBuilder data = new StringBuilder();
		int numbOfSavings = 0;
		SavingsAccount[] savings = acc.getSavingsAccounts();
		
		for (int i = 0; i < savings.length; i++) {
			if (savings[i] == null) {
				break;
			}
			
			// increase number of checking
			numbOfSavings++;
			
			data.append(savings[i].writeToString() + "\n");
		}
		
		return numbOfSavings + "\n" + data.toString();
	}
	
	// sort account from small to large
	public static AccountHolder[] sortAccountHolders() {
		AccountHolder[] accountHolder = MeritBank.accountHolders;
		
		int n = accountHolder.length; 
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) 
                if (accountHolder[j].compareTo(accountHolder[j+1]) > 0) 
                { 
                    // swap accountHolder[j+1] and accountHolder[i] 
                    AccountHolder temp = accountHolder[j]; 
                    accountHolder[j] = accountHolder[j+1]; 
                    accountHolder[j+1] = temp; 
                } 
        }
        
        return accountHolder;
	}
	
	private static String addCDData(AccountHolder acc) {
		StringBuilder data = new StringBuilder();
		int numbOfCDs = 0;
		CDAccount[] cds = acc.getCDAccounts();
		
		for (int i = 0; i < cds.length; i++) {
			if (cds[i] == null) {
				break;
			}
			
			// increase num of checking
			numbOfCDs++;
			
			data.append(cds[i].writeToString() + "\n");
		}
		
		return numbOfCDs + "\n" + data.toString();
	}
	
	/*
	 * convert all the needed checking account information to String and return
	 */
	private static String addCheckingData(AccountHolder acc) {
		StringBuilder data = new StringBuilder();
		int numbOfCheckings = 0;
		CheckingAccount[] checkings = acc.getCheckingAccounts();
		
		for (int i = 0; i < checkings.length; i++) {
			if (checkings[i] == null) {
				break;
			}
			
			// increase num of checking
			numbOfCheckings++;
			
			data.append(checkings[i].writeToString() + "\n");
		}
		
		return numbOfCheckings + "\n" + data.toString();
	}
	
	public static AccountHolder[] getAccountHolders() {
		AccountHolder[] accounts = Arrays.copyOf(MeritBank.accountHolders, MeritBank.numbOfAccountHolder);
		return accounts;
	}
	
	public static CDOffering[] getCDOfferings() {
		return CDOfferings;
	}
	
	public static CDOffering getBestCDOffering(double depositAmount) {
		double highestYield = 0;
		double tempYield = 0;
		int bestIndex = 0; 		// position of the best offerings in the CDOffering array
		
		// check if the CDOfferings is not null
		if (MeritBank.CDOfferings != null) {
			for (int i=0; i < MeritBank.CDOfferings.length; i++) {
				tempYield = MeritBank.futureValue(depositAmount, CDOfferings[i].getInterestRate(), CDOfferings[i].getTerm());
				if (tempYield > highestYield) {
					highestYield = tempYield;
					bestIndex = i;
				}
			}
			
			return CDOfferings[bestIndex];
		} else {
			return null;
		}
	}
	
	public static CDOffering getSecondBestCDOffering(double depositAmount) {

		double highestYield = 0;
		int secondBestI = 0; // second best offer index
		int bestI = 0;
		double secondBestYield = 0;
		double tempYield = 0;
		
		if (MeritBank.CDOfferings != null) {
			for (int i=0; i < MeritBank.CDOfferings.length; i++) {
				tempYield = MeritBank.futureValue(depositAmount, CDOfferings[i].getInterestRate(), CDOfferings[i].getTerm());
				if (tempYield > highestYield) {
					
					// let the second best offer take over the old best offer
					secondBestI = bestI;
					secondBestYield = highestYield;
					
					// the best offer get the new position and value
					highestYield = tempYield;
					bestI = i;
					
				}
			}
			
			return CDOfferings[secondBestI];
		} else {
			return null;
		}
	}
	
	public static void clearCDOfferings() {
		MeritBank.CDOfferings = null;
	}
	
	public static void setCDOfferings(CDOffering[] offerings) {
		CDOfferings = offerings; 
	}
	
	public static long getNextAccountNumber() {
		// get back later
		MeritBank.accIndex++;
		return accIndex;
	}
	
	public static void setNextAccountNumber(long nextAccountNumb) {
		MeritBank.accIndex = nextAccountNumb - 1;
	}
	
	public static double totalBalances() {
		double total = 0.0;
		
		// total all balances (checking and saving) in every account		
		for (int i=0; i < MeritBank.numbOfAccountHolder; i++) {
			total += MeritBank.accountHolders[i].getCheckingBalance() + MeritBank.accountHolders[i].getCheckingBalance();
		}
		
		return total;
	}
	
	public static double recursionFutureValue(double amount, int years, double interestRate) {
		if (years == 0) {
			return amount;
		} else {
			return amount * (1 + interestRate) * recursionFutureValue(1, years - 1, interestRate);
		}
		
	}

	public static double futureValue(double presentValue, double interestRate, int term) {
		double futureVal = presentValue * Math.pow(1 + interestRate, term);
		
		return futureVal;
	}
	
	// add transaction to an account
	// -- needed to be fixed, use instanceof, calling bankaccount.withdraw, deposit..etc
	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsFraudSuspicionLimitException, 
	ExceedsAvailableBalanceException {
		double amount = transaction.getAmount();
		BankAccount source = transaction.getSourceAccount();
		BankAccount target = transaction.getTargetAccount();
		
		// if amount > 1000, add to fraud queue
		if (Math.abs(transaction.getAmount()) > 1000) {
			MeritBank.fraudQueue.addTransaction(transaction);
			throw new ExceedsFraudSuspicionLimitException();
		}
		
		// if amount < 0
		if (transaction.getAmount() < 0) {
			throw new NegativeAmountException();
		}
		
		// deposit transaction
		if (transaction instanceof DepositTransaction) {
			
			// deposit money into account
			target.deposit(amount);
			
			// add transaction record
			target.addTransaction(transaction);
		} else if (transaction instanceof WithdrawTransaction) {
			// if withdraw amount larger than balance
			if (transaction.getAmount() + transaction.getTargetAccount().getBalance() < 0 ) {
				throw new ExceedsAvailableBalanceException();
			}
			
			// withdraw money
			target.withdraw(amount);
			
			// add transaction record
			transaction.getTargetAccount().addTransaction(transaction);
		} else if (transaction instanceof TransferTransaction) {
			// if transfer money more than source account balance
			if (source.getBalance() - amount  < 0) {
				throw new ExceedsAvailableBalanceException();
			}
			
			// withdraw money from source account
			source.withdraw(amount);
			
			// deposit money to target account
			target.deposit(amount);
			
			// add transaction record to both accounts
			transaction.getSourceAccount().addTransaction(transaction);
			transaction.getTargetAccount().addTransaction(transaction);
		}
		
		return true;
	}	
}