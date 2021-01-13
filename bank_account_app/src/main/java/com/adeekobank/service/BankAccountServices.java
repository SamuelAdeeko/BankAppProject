package com.adeekobank.service;

import java.util.List;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public interface BankAccountServices {
	
	public User userLogin(String username, String password) throws BusinessException;
	public int createAccount(User user, Account account) throws BusinessException;
	public long accountBalanceViewByCustomer(long accountNumber) throws BusinessException;
	public long customerWithdrawal(Transaction transaction) throws BusinessException, ClassNotFoundException;
	public long customerDeposit(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public boolean accountStatusByEmployee(long employeeId);
	public void customerAccountViewByEmployee(long employeeId, long userId) throws BusinessException;
	public int registerForCustomerAccountByUser(long standardUserId, Account account ) throws BusinessException, ClassNotFoundException;
	public long transferMoneyToAnotherAccount(Transaction transaction) throws BusinessException;
	public long acceptTransferToCustomerAccount(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public int updateContact(long userID, long contact) throws BusinessException;
	public int updateEmail(long userId, String email) throws BusinessException ;
	public int updatePassword(long userId , String password) throws BusinessException;
	public void viewAllUsers(long userId) throws BusinessException;
	public long searchAccountNumber(long accountNumber);
	public void updateAccountBal (long accountNumber, long balance) throws BusinessException;
	public long checkUserType(long userId);
	public long allCustomerTransaction(long accountNumber) throws BusinessException;
}
