package com.adeekobank.serviceImpl;

import java.util.List;

import com.adeeko.dao.impl.BankAccountDAOImpl;
import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;

public class BankAccountServicesImpl implements BankAccountServices {
	
	private BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();

	
	@Override
	public User userLogin(String username, String password) throws BusinessException {
		System.out.println("In service layer");
		User loginUser = bankAccountDao.userLogin(username, password);
		return loginUser;
	}



	@Override
	public int createAccount(Account account) throws BusinessException {
		int c = 0;
		c = bankAccountDao.createAccount(account);
		return c;
	}



	@Override
	public long accountBalanceViewByCustomer(long accountNumber) throws BusinessException {
		long accountBalance = 0;
		if (accountNumber >= 1000001 || accountNumber < 9999999) {
			accountBalance = bankAccountDao.accountBalanceViewByCustomer(accountNumber);
		} else {
			System.out.println("Invalid user account number.");
		}
		return accountBalance;
	}



	@Override
	public int customerWithdrawal(Transaction transaction) throws ClassNotFoundException, BusinessException {
	int c = 0;  
		System.out.println("In customer withdrawal method");
		long withdrawalAmt = transaction.getAmount();
		System.out.println("print withdrawal amt: " + withdrawalAmt);
		long customerActNumber = transaction.getSenderAccountNumber();
		System.out.println("print cust act number" + customerActNumber);
		long recieverActNumber = transaction.getRecieverAccountNumber();
		System.out.println("print reciever acct numb: " + recieverActNumber);
		if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
			System.out.println("Account number found");
			System.out.println("In service");
		}
		else {
			System.out.println("In service....acct number not found");
		}
		long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
		long overlordAccount = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
		System.out.println("Printing oerlord account balance: " + overlordAccount);
		long newAccountBal = 0;
		long overlordAccountBal = 0;
		if(withdrawalAmt > 0 && withdrawalAmt <= customerAccountBal) {
			System.out.println("In service");
			System.out.println("valid withdrawal amount");
			newAccountBal = customerAccountBal - withdrawalAmt;
			overlordAccountBal = overlordAccount - withdrawalAmt;
			bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
			bankAccountDao.updateAccountBal(recieverActNumber, overlordAccountBal);
			
			c = bankAccountDao.customerWithdrawal(transaction);
		} else {
			System.out.println("Enter a valid amount");
		}
		return c;
	}



	@Override
	public long customerDeposit(Transaction transaction) {
		long c = 0;
		long depositAmt = transaction.getAmount();
		long customerActNumber = transaction.getSenderAccountNumber();
		long recieverActNumber = transaction.getRecieverAccountNumber();
		if(depositAmt > 0) {
			System.out.println("In service");
			System.out.println("valid amount");
			try {
				if(customerActNumber == bankAccountDao.searchAccountNumber(customerActNumber) && recieverActNumber == bankAccountDao.searchAccountNumber(recieverActNumber)) {
					System.out.println("Account number found");
					System.out.println("In service");
				}
				else {
					System.out.println("In service....acct number not found");
				}
				long customerAccountBal = bankAccountDao.accountBalanceViewByCustomer(customerActNumber);
				long overlordAccount = bankAccountDao.accountBalanceViewByCustomer(recieverActNumber);
				System.out.println("Printing oerlord account balance: " + overlordAccount);
				long newAccountBal = 0;
				long overlordAccountBal = 0;
				newAccountBal = customerAccountBal + depositAmt;
				overlordAccountBal = overlordAccount + depositAmt;
				bankAccountDao.updateAccountBal(customerActNumber, newAccountBal);
				bankAccountDao.updateAccountBal(recieverActNumber, overlordAccountBal);
					
				c = bankAccountDao.customerDeposit(transaction);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Enter a valid amount");
		}
		return c;
	}



	@Override
	public void rejectInvalidTransactions() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean accountStatusByEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void customerAccountViewByEmployee(int employeeId, int userId) throws BusinessException {
		
		int verifyEmployeeId = bankAccountDao.checkUserType(employeeId);
		System.out.println("print userId is :" + verifyEmployeeId);
		int verifyUserId = bankAccountDao.checkUserType(userId);
		System.out.println("print userId is :" + verifyUserId);
		if(verifyEmployeeId >= 10 && verifyEmployeeId  <= 999  && verifyUserId >1000 && verifyUserId < 9999) {
			bankAccountDao.customerAccountViewByEmployee(verifyEmployeeId, verifyUserId);
		} else {
			System.out.println("===================================================");
			System.out.println("You are not authorized to view customer accounts.");
			System.out.println("===================================================");
		}
		
	}



	@Override
	public int registerForCustomerAccountByUser(int standardUserId, Account account) {
		int c = 0;
		int verifyUserId = bankAccountDao.checkUserType(standardUserId);
		System.out.println("print userId is :" + verifyUserId);
		
		if(verifyUserId >= 5 && verifyUserId <= 999) {
			try {
				System.out.println("In service");
				c = bankAccountDao.registerForCustomerAccountByUser(verifyUserId, account);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Not an authorized personnel.");
		}
		return c;
	}



	@Override // pending
	public int transferMoneyToAnotherAccount(Account account) {
		int c = 0;
		if (account.getAccountNumber() > 1000000000 && account.getAccountNumber() < 2000000000) {
			try {
				c = bankAccountDao.transferMoneyToAnotherAccount(account);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;
	}



	@Override  // pending
	public int acceptTransferToCustomerAccount(Transaction transaction) throws ClassNotFoundException, BusinessException {
		int c = 0;
		
		if (transaction.getRecieverAccountNumber() > 1000000000 && transaction.getRecieverAccountNumber() < 2000000000) {
			try {
				System.out.println("In service");
				c = bankAccountDao.acceptTransferToCustomerAccount(transaction);
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Account number not found");
		}
		
		
		return c;
	}



	@Override
	public int updateContact(int userID, long contact) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int updateEmail(int userId, String email) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int updatePassword(int userId, String password) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void viewAllUsers(int userId) throws BusinessException {
		try {
				if(userId == 104) {
					System.out.println("Access granted to Admin from service");
					bankAccountDao.viewAllUsers(userId);
				} else {
					System.out.println("In else");
				System.out.println("Access Failed..Only Admins can access these records.");
				}
			} catch (BusinessException e) {
				throw new BusinessException("Contact SYSTEM ADMINSTRATOR");
			}
	}



	@Override
	public long searchAccountNumber(long accountNumber) {
		long c = bankAccountDao.searchAccountNumber(accountNumber);
		return c;
	}



	@Override
	public void updateAccountBal(long accountNumber, long balance) throws BusinessException {
		bankAccountDao.updateAccountBal(accountNumber, balance);
		
	}



	@Override
	public int checkUserType(int userId) {
		
		int checkUserIdType = bankAccountDao.checkUserType(userId);
		return checkUserIdType;
	}



	
}
