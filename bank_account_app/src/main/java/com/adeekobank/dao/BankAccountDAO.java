package com.adeekobank.dao;

import java.util.List;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public interface BankAccountDAO {

	
	public User userLogin(String username, String password) throws BusinessException;
	public int createAccount(User user, long balance);
	public Balance accountBalanceViewByCustomer(int userId, long accountNumber);
	public Balance customerWithdrawal(long amount);
	public Balance customerDeposit(long amount);
	public void rejectInvalidTransactions(); 
	public boolean accountStatusByEmployee(int employeeId);
	public Account customerAccountViewByEmployee(int employeeId, int accountNumber);
	public int registerForCustomerAccountByUser(String userName);
	public Transaction transferMoneyToAnotherAccount(long amount, int accountNumber);
	public Balance acceptTransferToCustomerAccount(long amount);
	public int updateContact(int contact);
	public int updateEmail(String email);
	public int updatePassword(String password);
	public List<User> viewAllUsers(int userId) throws BusinessException;
}
