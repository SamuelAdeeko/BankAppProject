package com.adeekobank.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.adeeko.dao.impl.BankAccountDAOImpl;
import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;

import log4j_adeekobank.service.AdeekoBankLogService;

public class BankAccountServicesImpl implements BankAccountServices {
	
	private BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();
	AdeekoBankLogService service = new AdeekoBankLogService();
	
	@Override
	public User userLogin(String username, String password) throws BusinessException {
	// all user login
		User loginUser = bankAccountDao.userLogin(username, password);
		return loginUser;
	}



	@Override // update
	public int createAccount(User user, Account account) throws BusinessException {
		// standard users should be able to create an account
		int c = 0;
		bankAccountDao.createAccount(user, account);
		
		return c;
	}



	@Override
	public long accountBalanceViewByCustomer(long accountNumber) throws BusinessException {
		long accountBalance = 0;
			accountBalance = bankAccountDao.accountBalanceViewByCustomer(accountNumber);
		return accountBalance;
	}



	@Override
	public long customerWithdrawal(Transaction transaction) throws ClassNotFoundException, BusinessException {
	long c = 0;  
	// get withdrawal amount, customer and Overlord account...overlord account for deposits and withdrawal
		long withdrawalAmt = transaction.getAmount();
		long customerActNumber = transaction.getSenderAccountNumber();
		long recieverActNumber = transaction.getRecieverAccountNumber();
		if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
			service.servicelog("========================================");
			service.servicelog("Account number found");
			service.servicelog("========================================");
		}
		else {
			service.servicelog("========================================");
			service.servicelog("Account number not found");
			service.servicelog("========================================");
		}
		long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
		long overlordAccount = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
		long newAccountBal = 0;
		long overlordAccountBal = 0;
		// checks for negative withdrawal amount and account overdraft
		if(withdrawalAmt > 0 && withdrawalAmt <= customerAccountBal) {
			newAccountBal = customerAccountBal - withdrawalAmt;
			overlordAccountBal = overlordAccount + withdrawalAmt;
			bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
			bankAccountDao.updateAccountBal(recieverActNumber, overlordAccountBal);
			c = bankAccountDao.customerWithdrawal(transaction);
		} else {
			service.servicelog("========================================");
			service.servicelog("Enter a valid amount");
			service.servicelog("========================================");
		}
		return c;
	}



	@Override
	public long customerDeposit(Transaction transaction) {
		long c = 0;
		long depositAmt = transaction.getAmount();
		long customerActNumber = transaction.getSenderAccountNumber();
		long recieverActNumber = transaction.getRecieverAccountNumber();
		// checks for negative deposit amount and verifies that both sender and reciever's accounts is present in accounts database table
		if(depositAmt > 0) {
			try {
				if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
					service.servicelog("Account number found");
				}
				else {
					service.servicelog("Account number not found");
				}
				long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
				long overlordAccount = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
				long newAccountBal = 0;
				long overlordAccountBal = 0;
				newAccountBal = customerAccountBal + depositAmt;
				overlordAccountBal = overlordAccount - depositAmt;
				bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
				bankAccountDao.updateAccountBal(recieverActNumber, overlordAccountBal);
				c = bankAccountDao.customerDeposit(transaction);
			} catch (ClassNotFoundException | BusinessException e) {
				e.printStackTrace();
			} 
		} else {
			service.servicelog("========================================");
			service.servicelog("Enter a valid amount");
			service.servicelog("========================================");
		}
		return c;
	}

	@Override
	public boolean accountStatusByEmployee(long employeeId) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void customerAccountViewByEmployee(long employeeId, long userId) throws BusinessException {
		// verify if employee id corresponds to an employee in the user table
		long verifyEmployeeId = bankAccountDao.checkUserType(employeeId);
		// checks if userId is in users database table
		long verifyUserId = bankAccountDao.checkUserType(userId);
		// if statement to run block of code if true 
		if(verifyEmployeeId >= 10 && verifyEmployeeId  <= 999  && verifyUserId >1000 && verifyUserId < 9999) {
			bankAccountDao.customerAccountViewByEmployee(verifyEmployeeId, verifyUserId);
		} else {
			service.servicelog("========================================");
			service.servicelog("You are not authorized to view customer accounts.");
			service.servicelog("========================================");
		}
		
	}



	@Override
	public int registerForCustomerAccountByUser(long standardUserId, Account account) {
		int c = 0;
		// checks for standard users Id using if statement to search through the users database table
		long verifyUserId = 0;
		try {
			verifyUserId = bankAccountDao.checkUserType(standardUserId);
		} catch (BusinessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(verifyUserId >= 1 && verifyUserId <= 999) {
			try {
				c = bankAccountDao.registerForCustomerAccountByUser(verifyUserId, account);
			} catch (ClassNotFoundException |BusinessException e) {
				e.printStackTrace();
			}
		} else {
			service.servicelog("========================================");
			service.servicelog("Not an authorized personnel.");
			service.servicelog("========================================");
		}
		return c;
	}



	@Override 
	public long transferMoneyToAnotherAccount(Transaction transaction) throws BusinessException {
		long c = 0;
		long depositAmt = transaction.getAmount();
		long customerActNumber = transaction.getSenderAccountNumber();
		long recieverActNumber = transaction.getRecieverAccountNumber();
		// checks for negative deposit amount and verifies that both sender and reciever's accounts is present in accounts database table
		if(depositAmt > 0) {
			try {
				if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
					service.servicelog("Account number found");
				}
				else {
					service.servicelog("Account number not found");
				}
				// check sender account balance for non-negative number
				long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
				if(customerAccountBal >= depositAmt) {
				long newAccountBal = 0;
				newAccountBal = customerAccountBal - depositAmt;
				bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
				long receiverAccountBal = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
				long newReceiverActBal = 0;
				newReceiverActBal = receiverAccountBal + depositAmt;
				bankAccountDao.updateAccountBal(recieverActNumber, newReceiverActBal);
				c = bankAccountDao.transferMoneyToAnotherAccount(transaction);
				service.servicelog("in service transfer executed");
				} else {
					service.servicelog("Unable to process your transaction. Please verify your details and try again.");
				}
			} catch (NumberFormatException | BusinessException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
		}
		return c;
	}



	@Override  // pending
	public long acceptTransferToCustomerAccount(Transaction transaction) throws ClassNotFoundException, BusinessException {
		long c = 0;
		long transferAmt = transaction.getAmount();
		long customerActNumber = transaction.getSenderAccountNumber();
		long recieverActNumber = transaction.getRecieverAccountNumber();
		// checks for negative deposit amount and verifies that both sender and reciever's accounts is present in accounts database table
		if(transferAmt > 0) {
			try {
				if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
					service.servicelog("Account number found");
				}
				else {
					service.servicelog("Account number not found");
				}
				// check sender account balance for non-negative number
				long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
				if(customerAccountBal >= transferAmt) {
				long newAccountBal = 0;
				newAccountBal = customerAccountBal - transferAmt;
				bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
				long receiverAccountBal = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
				long newReceiverActBal = 0;
				newReceiverActBal = receiverAccountBal + transferAmt;
				bankAccountDao.updateAccountBal(recieverActNumber, newReceiverActBal);
				c = bankAccountDao.transferMoneyToAnotherAccount(transaction);
				service.servicelog("in service transfer executed");
				} else {
					service.servicelog("Unable to process your transaction. Please verify your details and try again.");
				}
			} catch (NumberFormatException | BusinessException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
		}
		
		return c;
	}



	@Override
	public int updateContact(long userID, long contact) throws BusinessException {
		int c = 0;
		// checks if userId is in database and allows access to only customers and admin
			long verifyUserId = bankAccountDao.checkUserType(userID);
			if (verifyUserId >= 1000 || verifyUserId < 10) {
				bankAccountDao.updateContact(verifyUserId, contact);
			} else {
			service.servicelog("Enter a valid contact number");
		}
		return c;
	}



	@Override
	public int updateEmail(long userId, String email) throws BusinessException {
		int c = 0;
		
		// checks if userId is in database and allows access to only customers and admin
		long verifyUserId = bankAccountDao.checkUserType(userId);
		if (verifyUserId >= 1000 || verifyUserId < 10 && email.matches("^(.+)@(.+)$")) {
			bankAccountDao.updateEmail(verifyUserId, email);
			} else {
					service.servicelog("Enter a valid userId and email");
				}
		return c;
	}



	@Override
	public int updatePassword(long userId, String password) throws BusinessException {
		int c = 0;
		// checks if userId is in database and allows access to only customers and admin
		long verifyUserId = bankAccountDao.checkUserType(userId);
		
		if (verifyUserId >= 1000 || verifyUserId < 10) {
			bankAccountDao.updatePassword(verifyUserId, password);
		} else {
			service.servicelog("Unauthorized user Id number");
		}
		return c;
	}

	@Override
	public void viewAllUsers(long userId) throws BusinessException {
		
		// Admin can view all users which inclused (customers, employee, admin)
		long verifyUserId = bankAccountDao.checkUserType(userId);
		System.out.println("print userId is :" + verifyUserId);

		if(verifyUserId >= 1 && verifyUserId <= 9) {
	
			bankAccountDao.viewAllUsers(userId);
		} else {
			service.servicelog("Access Failed..Only Admins can access these records.");
		}
	}



	@Override
	public long searchAccountNumber(long accountNumber) {
		//use to verify if an account number exist in the account database
		long c = 0;
		try {
			c = bankAccountDao.searchAccountNumber(accountNumber);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}



	@Override
	public void updateAccountBal(long accountNumber, long balance) throws BusinessException {
		// code is used to update account balance in the database after every withdrawal or deposit
		bankAccountDao.updateAccountBal(accountNumber, balance);
		
	}



	@Override
	public long checkUserType(long userId) {
		// returns the user type of account holder (customer, admin, employee)
		long checkUserIdType = 0;
		try {
			checkUserIdType = bankAccountDao.checkUserType(userId);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkUserIdType;
	}



	@Override
	public long allCustomerTransaction(long accountNumber) throws BusinessException {
		long c = 0;
		c = bankAccountDao.allCustomerTransaction(accountNumber);
		return c;
	}

	


	
}
