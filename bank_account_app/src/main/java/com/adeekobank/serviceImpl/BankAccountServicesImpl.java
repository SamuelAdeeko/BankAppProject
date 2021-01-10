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
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int accountBalanceViewByCustomer(int userId, long accountNumber) throws BusinessException {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int customerWithdrawal(Transaction transaction) throws ClassNotFoundException, BusinessException {
	int c = 0;  
	
		long withdrawalAmt = transaction.getAmount();
	
		if(withdrawalAmt > 0) {
			System.out.println("In service");
			System.out.println("valid amount");
			c = bankAccountDao.customerWithdrawal(transaction);
		} else {
			System.out.println("Enter a valid amount");
		}
		return c;
	}



	@Override
	public int customerDeposit(Transaction transaction) {
		int c = 0;
		long depositAmt = transaction.getAmount();
		
		if(depositAmt > 0) {
			System.out.println("In service");
			System.out.println("valid amount");
			try {
				c = bankAccountDao.customerDeposit(transaction);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
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
	public void customerAccountViewByEmployee(int employeeId, long accountNumber) throws BusinessException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public int registerForCustomerAccountByUser(int userId, User user) {
		int c = 0;
		if(userId > 100 && userId < 106) {
			try {
				System.out.println("In service");
				c = bankAccountDao.registerForCustomerAccountByUser(userId, user);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;
	}



	@Override
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



	@Override
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



	
}
