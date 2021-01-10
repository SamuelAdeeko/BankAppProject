package com.adeekobank.service;

import java.util.List;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public interface BankAccountServices {
	
	public User userLogin(String username, String password) throws BusinessException;
	public int createAccount(Account account) throws BusinessException;
	public int accountBalanceViewByCustomer(int userId, long accountNumber) throws BusinessException;
	public int customerWithdrawal(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public int customerDeposit(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public void rejectInvalidTransactions(); 
	public boolean accountStatusByEmployee(int employeeId);
	public void customerAccountViewByEmployee(int employeeId, long accountNumber) throws BusinessException;
	public int registerForCustomerAccountByUser(int userId, User user );
	public int transferMoneyToAnotherAccount(Account account);
	public int acceptTransferToCustomerAccount(Transaction transaction) throws ClassNotFoundException, BusinessException;
	public int updateContact(int userID, long contact) throws BusinessException;
	public int updateEmail(int userId, String email) throws BusinessException ;
	public int updatePassword(int userId , String password) throws BusinessException;
	public void viewAllUsers(int userId) throws BusinessException;

}
