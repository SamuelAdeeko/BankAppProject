package com.adeekobank.model;

public class Account {

	private int accountNumber;
	private User userId;
	private String accountType;
	
	public Account() {
	}

	public Account(int accountNumber, User userId, String accountType) {
		super();
		this.accountNumber = accountNumber;
		this.userId = userId;
		this.accountType = accountType;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "Account [accountNumber=" + accountNumber + ", userId=" + userId + ", accountType=" + accountType + "]";
	}
	
}
