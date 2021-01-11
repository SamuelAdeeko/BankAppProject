package com.adeekobank.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;
import com.adeekobank.serviceImpl.BankAccountServicesImpl;

public class AccountLogin {

	
	public static void main(String[] args) throws BusinessException {
		
		BankAccountServices bankAccountServices = new BankAccountServicesImpl();
	//	BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Adeeko Bank App Version 1.0");
		System.out.println("---------------------------------------");
		System.out.println("Enter your login information below.");
		System.out.println();
		
		
		try {
			System.out.println("Enter your Username:");
			String username= scanner.nextLine();
			System.out.println("Enter your Password:");
			String password = scanner.nextLine();
			User login = bankAccountServices.userLogin(username, password);
			if(login != null) {
				System.out.println("Hello, " + username.toUpperCase() + " | January 6, 2021");
				System.out.println(login);
				System.out.println();
				int choice = 0;
				
				do {
					System.out.println("Hello, " + username.toUpperCase() + " | January 6, 2021");
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
					System.out.println("13)Recieve transafer in customer account");
			
					choice = Integer.parseInt(scanner.nextLine());
					switch (choice) {
					
					case 1: 
						
						try {
								System.out.println("case-1 was selected");
								System.out.println("Create an account.... " + username );
							
							
							int userid = login.getUserId();	
							System.out.println("Enter an Account number..");
							int actNumber = Integer.parseInt(scanner.nextLine());
							System.out.println("Savings or current Account");
							String actType= scanner.nextLine();
							System.out.println("Enter a starting balance");
							long startBalance= Long.parseLong(scanner.nextLine());
							System.out.println("Account Id");
							int actId= Integer.parseInt(scanner.nextLine());
							Account newAccount = new Account(actNumber,userid, actType, startBalance, actId);
							bankAccountServices.createAccount(newAccount);				
			
						} catch (Exception e) {
							throw new BusinessException("Invalid account details");
						}
						
						break;
						
					case 2: 
						try {
				//		if(login != null) {
							System.out.println("case-2 was selected");
							System.out.println("Update email address");
							System.out.println("Enter new email address");
							String newEmail = scanner.nextLine();
							int userid = login.getUserId();
							int updatedEmail = bankAccountServices.updateEmail(userid, newEmail);
							if(updatedEmail > 0) {
								System.out.println("Your email address has beeen updated to " + newEmail);		
							} else {
								System.out.println("Incorrect email, please try again");
							}
				//			}
						} catch (Exception e) {
							System.out.println(e);
							throw new BusinessException("Invalid email, please try again");
						}
						
						break;
						
					case 3: 
						try {
				//			if(login != null) {
								System.out.println("case-3 was selected");
								System.out.println("Update password");
								System.out.println("Enter new password");
								String newPassword = scanner.nextLine();
								int userid = login.getUserId();
								int updatedPassword = bankAccountServices.updatePassword(userid, newPassword);
								if(updatedPassword > 0) {
									System.out.println("Your password has been updated successfully. " );		
								} else {
									System.out.println("Incorrect email, please try again");
								}
			//					}
							} catch (Exception e) {
								System.out.println(e);
								throw new BusinessException("Invalid email, please try again");
							}
							
							break;
							
							
					case 4: 
						try {
			//				if(login != null) {
								System.out.println("case-4 was selected");
								System.out.println("Update contact number");
								System.out.println("Enter contact number");
								long newContact = Long.parseLong(scanner.nextLine());
								int userid = login.getUserId();
								int updatedContact = bankAccountServices.updateContact(userid, newContact);
								if(updatedContact > 0) {
									System.out.println("Contact updated successfully. " );		
								} else {
									System.out.println("Incorrect contact number, please try again");
								}
			//					}
							} catch (BusinessException e) {
								System.out.println(e);
								throw new BusinessException("Invalid contact number, please try again");
							}
							
							break;
							
						
				
					case 5: 
						System.out.println("case-5 was selected");
						System.out.println("Enter userId: ");
					

						
					case 6:
		
						try {
				//			if(login != null) {
								System.out.println("case-6 was selected");
								System.out.println("Customer account view by employee...");
								int customerUserId = login.getUserId(); 
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
							bankAccountServices.accountBalanceViewByCustomer(actNumber);
						
						}catch (BusinessException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;
						
					case 8:
						
						try {
				//			if(login != null) {
								System.out.println("case-8 was selected");
								System.out.println("All Customer account view by Admin...");
								int userid = login.getUserId();
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
						int customerUserId = login.getUserId(); 
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
						System.out.println("case-12 was selected");
						System.out.println("Transfer money to another account");
						System.out.println("Enter Account Number");
						int actNumber = Integer.parseInt(scanner.nextLine());
						System.out.println("Account Type");
						String accountType = scanner.nextLine();
						int userid = login.getUserId();
						System.out.println("Enter Account Id");
						int actId = Integer.parseInt(scanner.nextLine());
						Account acctTransfer = new Account(actNumber,userid,accountType,000000l, actId);
						bankAccountServices.transferMoneyToAnotherAccount(acctTransfer);
						} catch (Exception e) {
							
							e.printStackTrace();
							throw new BusinessException("UserId not found");
						}
					
							break;
							
					case 13: 
						try {
						System.out.println("case-13 was selected");
						System.out.println("Accept transfers to customer account");
						System.out.println("Input Transaction Id");
						long transId = Long.parseLong(scanner.nextLine());
						System.out.println("Input Amount");
						long amount = Long.parseLong(scanner.nextLine());
						System.out.println("Sender Account");
						long senderAct = Long.parseLong(scanner.nextLine());
						System.out.println("Reciever Account");
						long recieverAct = Long.parseLong(scanner.nextLine());						
						Transaction deposit = new Transaction(transId,amount,senderAct,recieverAct,"Deposit");
						
						bankAccountServices.acceptTransferToCustomerAccount(deposit);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
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
			System.out.println("Invalid login information, please try again");
			System.out.println(e);
		}
		
	
		
	
		
}
	
	}


