package com.adeekobank.dao;

import java.util.List;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public interface BankAccountDAO {

	
	public User userLogin(String username, String password) throws BusinessException;
	public int createAccount(Account account) throws BusinessException;
	public long accountBalanceViewByCustomer(long accountNumber) throws BusinessException;
	public int customerWithdrawal(Transaction transaction) throws BusinessException, ClassNotFoundException;
	public long customerDeposit(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public void rejectInvalidTransactions(); 
	public boolean accountStatusByEmployee(int employeeId);
	public void customerAccountViewByEmployee(int employeeId, int userId) throws BusinessException;
	public int registerForCustomerAccountByUser(int standardUserId, Account account ) throws BusinessException, ClassNotFoundException;
	public int transferMoneyToAnotherAccount(Account account) throws BusinessException;
	public int acceptTransferToCustomerAccount(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public int updateContact(int userID, long contact) throws BusinessException;
	public int updateEmail(int userId, String email) throws BusinessException ;
	public int updatePassword(int userId , String password) throws BusinessException;
	public void viewAllUsers(int userId) throws BusinessException;
	public long searchAccountNumber(long accountNumber);
	public void updateAccountBal (long accountNumber, long balance) throws BusinessException;
	public int checkUserType(int userId);
}
