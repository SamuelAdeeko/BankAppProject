package com.adeekobank.main;

import java.sql.Date;
import java.util.Scanner;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;
import com.adeekobank.serviceImpl.BankAccountServicesImpl;

import log4j_adeekobank.service.AdeekoBankLogService;

public class AccountLogin {

	
	public static void main(String[] args) throws BusinessException {
		AdeekoBankLogService service = new AdeekoBankLogService();
		BankAccountServices bankAccountServices = new BankAccountServicesImpl();
	//	BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();
		
		Scanner scanner = new Scanner(System.in);
		service.servicelog("Welcome to Adeeko Bank App Version 1.0");
		service.servicelog("---------------------------------------");
		service.servicelog("Enter your login information below.");
		
		
		
		try {
			
			service.servicelog("Enter your Username:");
			String username= scanner.nextLine();
			service.servicelog("Enter your Password:");
			String password = scanner.nextLine();
			User login = bankAccountServices.userLogin(username, password);
			if(login != null) {
				service.servicelog("Hello, " + username.toUpperCase() + " | January 13, 2021");
				int choice = 0;
				
				do {
					System.out.println("============================");
					System.out.println(" Account balance details below.......");
					System.out.println();
					System.out.println(" What will you like to do today?");
					System.out.println("----------------------------------");
					System.out.println("Select from the list below.....");
					System.out.println("1) Create an account");
					System.out.println("2) Update email address ");
					System.out.println("3) Update user password ");
					System.out.println("4) Update user contact number ");
					System.out.println("6)Customer account view by Employee");
					System.out.println("7)Account balance view by customer");	
					System.out.println("8)All customer account view by Admin");
					System.out.println("9)Customer withdrawal");
					System.out.println("10)Customer deposit");
					System.out.println("11)Register for Customer account by User");
					System.out.println("12)Transfer money to another account");
					System.out.println("13)Recieve transfer in customer account");
			
					choice = Integer.parseInt(scanner.nextLine());
					switch (choice) {
					
					case 1: 
						
						try {
								System.out.println("case-1 was selected");
								System.out.println("Create an account.... " + username );
							
							
							long userid = login.getUserId();	
						//	System.out.println("Enter an Account number..");
							service.servicelog("User Type (customer, Admin, Employee)");
							String userType= scanner.nextLine();
							service.servicelog("First Name");
							String firstName = scanner.nextLine();
							
							service.servicelog("Last Name");
							String lastName = scanner.nextLine();
							service.servicelog("UserName");
							String userName = scanner.nextLine();
							service.servicelog("Email");
							String email = scanner.nextLine();  
							
							service.servicelog("Password");
							String pasword = scanner.nextLine();
							
							service.servicelog("Date of Birth");
							String dob = scanner.nextLine();
							
							service.servicelog("Street Address");
							String streetAddress = scanner.nextLine();
							
							service.servicelog("City");
							String city = scanner.nextLine();
							
							service.servicelog("State");
							String state = scanner.nextLine();
							
							service.servicelog("Country");
							String country = scanner.nextLine();
							
							service.servicelog("Zip Code");
							long zipCode = Long.parseLong(scanner.nextLine());
							
							service.servicelog("Contact Number");
							long contactNumber = Long.parseLong(scanner.nextLine());
							
							long actNumber = generateRandomLongAccountNumber();
							long newCustomerUserId = generateRandomLongCustomerId();
							service.servicelog("Savings or current Account");
							String actType= scanner.nextLine();
							
							service.servicelog("Enter a starting balance");
							long startBalance= Long.parseLong(scanner.nextLine());
							long actId= generateRandomLongAccountId(); 
							Account newAccount = new Account(actNumber,newCustomerUserId, actType, startBalance, actId);
							User newUser = new User(newCustomerUserId,userName, firstName,lastName, email, pasword,userType, contactNumber, dob, streetAddress, city, state, zipCode, country);
							bankAccountServices.createAccount(newUser, newAccount);
						} catch (Exception e) {
							throw new BusinessException("Invalid account details");
						}
						
						break;
						
					case 2: 
						try {
							service.servicelog("case-2 was selected");
							service.servicelog("========================================");
							service.servicelog("Update email address");
							service.servicelog("Enter new email address");
							String newEmail = scanner.nextLine();
							service.servicelog("confirm email address");
							String confirmEmail = scanner.nextLine();
							long userid = login.getUserId();
							if (confirmEmail.equals(newEmail)){
								bankAccountServices.updateEmail(userid, newEmail);
							} else {
								service.servicelog("========================================");
								service.servicelog("Email address does not match.");
								service.servicelog("========================================");
							}
						} catch (Exception e) {
							throw new BusinessException(e.getMessage());
						}
						
						break;
						
					case 3: 
						try {
							service.servicelog("case-3 was selected");
							service.servicelog("========================================");
							service.servicelog("Update password");
							service.servicelog("Enter new password");
							String newPassword = scanner.nextLine();
							service.servicelog("Confirm new password");
							String confirmPassword = scanner.nextLine();
							long userid = login.getUserId();
							if (newPassword.equals(confirmPassword)){
								bankAccountServices.updatePassword(userid, confirmPassword);
							} else {
								service.servicelog("========================================");
								service.servicelog("Password does not match.");
								service.servicelog("========================================");
							}
							} catch (Exception e) {
								throw new BusinessException(e.getMessage());
							}
							
							break;
							
							
					case 4: 
						try {
								service.servicelog("case-4 was selected");
								service.servicelog("Update contact number");
								service.servicelog("Enter contact number");
								long newContact = Long.parseLong(scanner.nextLine());
								long userid = login.getUserId();
								bankAccountServices.updateContact(userid, newContact);
							} catch (BusinessException e) {
								throw new BusinessException(e.getMessage());
							}
							
							break;
							
						
				
					case 5: 
						service.servicelog("case-5 was selected");
						service.servicelog("View All Transaction ");
					

						
					case 6:
		
						try {
				//			if(login != null) {
								System.out.println("case-6 was selected");
								System.out.println("Customer account view by employee...");
								long customerUserId = login.getUserId(); 
								System.out.println("Employee Id Number");
								int employeeId = Integer.parseInt(scanner.nextLine());
								bankAccountServices.customerAccountViewByEmployee(employeeId, customerUserId);
									
				//				} 
							
						}catch (BusinessException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;
				
					
					
					case 7:
						try {
							System.out.println("case-7 was selected");
							System.out.println("Account Balance View By Customer...");
							System.out.println("Enter account number");
							long actNumber = Long.parseLong(scanner.nextLine());
							long userId = login.getUserId();
							if(userId >= 1000 && userId <= 9999) {
								bankAccountServices.accountBalanceViewByCustomer(actNumber);
							} else {
								service.servicelog("Not an authorized user");
							}
							
							
						
						}catch (BusinessException e) {
						e.printStackTrace();
							throw new BusinessException("Access denied, please try again");
						}
						break;
						
					case 8:
						
						try {
				//			if(login != null) {
								System.out.println("case-8 was selected");
								System.out.println("All Customer account view by Admin...");
								long userid = login.getUserId();
								bankAccountServices.viewAllUsers(userid);
									
				//				} 
							
						}catch (BusinessException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;
						
					case 9: 
						try {
						System.out.println("case-9 was selected");
						System.out.println("customer withdrawal");
						System.out.println("Input Transaction Id");
						long transId = Long.parseLong(scanner.nextLine());
						System.out.println("Amount you want to withdraw");
						long amount = Long.parseLong(scanner.nextLine());
						System.out.println("Account number to withdraw from...");
						long customerAct = Long.parseLong(scanner.nextLine());
//						System.out.println("Reciever Account");
//						long recieverAct = Long.parseLong(scanner.nextLine());
						long overlordAccount = 1234567890;
						Transaction withDrawal = new Transaction(transId,amount,customerAct,overlordAccount,"withdrawal");
						
						bankAccountServices.customerWithdrawal(withDrawal);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
							break;
							
					case 10: 
						try {
						System.out.println("case-10 was selected");
						System.out.println("customer deposit");
						System.out.println("Input Transaction Id");
						long transId = Long.parseLong(scanner.nextLine());
						System.out.println("Input Amount");
						long amount = Long.parseLong(scanner.nextLine());
						System.out.println("Sender Account");
						long senderAct = Long.parseLong(scanner.nextLine());
//						System.out.println("Reciever Account");
//						long recieverAct = Long.parseLong(scanner.nextLine());	
						long overlordAccount = 1234567890;
						Transaction deposit = new Transaction(transId,amount,senderAct,overlordAccount,"deposit");
						
						bankAccountServices.customerDeposit(deposit);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
							break;
							
					case 11: 
						try {
						System.out.println("case-11 was selected");
						System.out.println("Register for customer account by user");
						long customerUserId = login.getUserId(); 
						System.out.println("Account Number");
						long accountNumber = Long.parseLong(scanner.nextLine());
						System.out.println("Account Type (Savings or Checking)");
						String actType = scanner.nextLine();
						System.out.println("Opening Balance");
						long balance = Long.parseLong(scanner.nextLine());
						System.out.println("Account Id");
						int actId = Integer.parseInt(scanner.nextLine());
						System.out.println("Enter User Id (Employee or Admin only)");
						int standardUserId = Integer.parseInt(scanner.nextLine());
//						System.out.println("Date of Birth");
//						String dateFormat = "dd/MM/yyyy";
//						Date dob = new SimpleDateFormat(dateFormat).parse(scanner.nextLine());
						Account newCustomerAccount = new Account(accountNumber, customerUserId, actType, balance, actId);
						bankAccountServices.registerForCustomerAccountByUser(standardUserId, newCustomerAccount);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new BusinessException("UserId not found");
						}
					
							break;
							
					case 12: 
						try {
						service.servicelog("case-12 was selected");
						service.servicelog("Transfer money to another account");
						service.servicelog("Transfer From Account Number");
						long actNumber = Long.parseLong(scanner.nextLine());
						service.servicelog("Transfer To Reciever's Account");
						long receiverAct = Long.parseLong(scanner.nextLine());
						service.servicelog("Input Amount To Transfer");
						long amount = Long.parseLong(scanner.nextLine());
						service.servicelog("Input Transaction Id");
						long transId = generateRandomTransactionId();
					
						Transaction transfer = new Transaction(transId,amount,actNumber,receiverAct,"Outgoing Transfer");
						bankAccountServices.transferMoneyToAnotherAccount(transfer);
						} catch (Exception e) {
							throw new BusinessException(e.getMessage());
						}
					
							break;
							
					case 13: 
						try {
							service.servicelog("case-13 was selected");
							service.servicelog("Accept transfers to customer account");
							
							
							service.servicelog("case-13 was selected");
							service.servicelog("Accept Money Into Customer Account");
							service.servicelog("Transfer From Account Number");
							long actNumber = Long.parseLong(scanner.nextLine());
							service.servicelog("Transfer To Reciever's Account");
							long receiverAct = Long.parseLong(scanner.nextLine());
							service.servicelog("Input Amount To Transfer");
							long amount = Long.parseLong(scanner.nextLine());
							service.servicelog("Input Transaction Id");
							long transId = generateRandomTransactionId();
						
							Transaction transfer = new Transaction(transId,amount,actNumber,receiverAct,"Outgoing Transfer");
							bankAccountServices.acceptTransferToCustomerAccount(transfer);	
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					
							break;
							
//					case 14: 
//						try {
//						System.out.println("case-14 was selected");
//						System.out.println("search for account number");
//						System.out.println("Input Account Number");
//						long actNumber = Long.parseLong(scanner.nextLine());					
//						Account acctNo = new Account()
//						
//						bankAccountServices.acceptTransferToCustomerAccount(deposit);
//						} catch (ClassNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					
//							break;
					}
						
					} while (choice!= 15);

			}
		}catch (BusinessException e){
			e.printStackTrace();
		}
		
	
		
	
		
}
	public static long generateRandomLongAccountNumber() {
	    long leftLimit = 1000001L;
	    long rightLimit = 9999999L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	public static long generateRandomLongEmployeeId() {
	    long leftLimit = 10L;
	    long rightLimit = 999L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	public static long generateRandomLongAdminId() {
	    long leftLimit = 1L;
	    long rightLimit = 9L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	public static long generateRandomLongCustomerId() {
	    long leftLimit = 1000L;
	    long rightLimit = 9999L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	public static long generateRandomLongAccountId() {
	    long leftLimit = 1L;
	    long rightLimit = 8999998L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	
	public static long generateRandomTransactionId() {
	    long leftLimit = 1L;
	    long rightLimit = 1000000L;
	    long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	    return generatedLong;
	}
	


	}

