package com.adeekobank.model;

public class Transaction {
	
	private int transactionId;
	private long amount;
	private Account senderAccountNumber;
	private int recieverAccountNumber;
	private int transactionType;

	public Transaction() {
		
	}

	public Transaction(int transactionId, long amount, Account senderAccountNumber, int recieverAccountNumber,
			int transactionType) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.senderAccountNumber = senderAccountNumber;
		this.recieverAccountNumber = recieverAccountNumber;
		this.transactionType = transactionType;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Account getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(Account senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public int getRecieverAccountNumber() {
		return recieverAccountNumber;
	}

	public void setRecieverAccountNumber(int recieverAccountNumber) {
		this.recieverAccountNumber = recieverAccountNumber;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", amount=" + amount + ", senderAccountNumber="
				+ senderAccountNumber + ", recieverAccountNumber=" + recieverAccountNumber + ", transactionType="
				+ transactionType + "]";
	}

}
