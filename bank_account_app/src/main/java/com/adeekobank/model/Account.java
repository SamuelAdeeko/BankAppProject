package com.adeekobank.model;

public class Account {

	private long accountNumber;
	private int userId;
	private String accountType;
	private long balance;
	private int account_id;
	
	public Account() {
	}

	public Account(long accountNumber, int userId, String accountType, long balance, int account_id) {
		super();
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.accountType = accountType;
		this.balance = balance;
		this.account_id = account_id;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", userId=" + userId + ", accountType=" + accountType
				+ ", balance=" + balance + ", account_id=" + account_id + "]";
	}

}