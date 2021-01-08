package com.adeekobank.model;

public class Balance {

	private Account accountNumber;
	private long balanceAmount;
	private Account accountType;
	private User userId;
	
	public Balance() {
		
	}

	public Balance(Account accountNumber, long balanceAmount, Account accountType, User userId) {
		super();
		this.accountNumber = accountNumber;
		this.balanceAmount = balanceAmount;
		this.accountType = accountType;
		this.userId = userId;
	}

	public Account getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Account accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(long balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Account getAccountType() {
		return accountType;
	}

	public void setAccountType(Account accountType) {
		this.accountType = accountType;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Balance [accountNumber=" + accountNumber + ", balanceAmount=" + balanceAmount + ", accountType="
				+ accountType + ", userId=" + userId + "]";
	}
	

}