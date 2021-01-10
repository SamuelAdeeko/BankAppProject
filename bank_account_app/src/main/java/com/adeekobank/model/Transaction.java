package com.adeekobank.model;

public class Transaction {
	
	private long transactionId;
	private long amount;
	private long senderAccountNumber;
	private long recieverAccountNumber;
	private String transactionType;

	public Transaction() {
		
	}

	public Transaction(long transactionId, long amount, long senderAccountNumber, long recieverAccountNumber,
			String transactionType) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.senderAccountNumber = senderAccountNumber;
		this.recieverAccountNumber = recieverAccountNumber;
		this.transactionType = transactionType;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(long senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public long getRecieverAccountNumber() {
		return recieverAccountNumber;
	}

	public void setRecieverAccountNumber(long recieverAccountNumber) {
		this.recieverAccountNumber = recieverAccountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", amount=" + amount + ", senderAccountNumber="
				+ senderAccountNumber + ", recieverAccountNumber=" + recieverAccountNumber + ", transactionType="
				+ transactionType + "]";
	}

	

}
