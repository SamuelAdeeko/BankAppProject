package com.adeekobank.staffMain;

import java.util.Scanner;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;
import com.adeekobank.serviceImpl.BankAccountServicesImpl;

import log4j_adeekobank.service.AdeekoBankLogService;

public class StaffLogin {

	public static void main(String[] args) {
		AdeekoBankLogService service = new AdeekoBankLogService();
		BankAccountServices bankAccountServices = new BankAccountServicesImpl();
		
		Scanner scanner = new Scanner(System.in);
		service.servicelog("Welcome to Adeeko Bank App Version 1.0");
		service.servicelog("---------------------------------------");
		service.servicelog("Employees and System Admin Login Only.");
		
		
		
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
					service.servicelog("============================");
					service.servicelog(" Account details below.......");
					service.servicelog(" What will you like to do today?");
					service.servicelog("----------------------------------");
					service.servicelog("Select from the list below.....");
					service.servicelog("1) Create an account");
					service.servicelog("2) View all customer transactions ");
					service.servicelog("3)Customer account view by Employee");
					service.servicelog("4)All customer account view by Admin");
			
					choice = Integer.parseInt(scanner.nextLine());
					switch (choice) {
					
					case 1: 
						
						try {
							service.servicelog("case-1 was selected");
							service.servicelog("Create an account.... " + username );
							
							String admin = "admin";
							String employee = "employee";
							String customer = "customer";
							service.servicelog("User Type (Admin, Employee, Customer)");
							String userType= scanner.nextLine();
							long newAdminId = generateRandomLongAdminId();
							long newEmployeeId = generateRandomLongEmployeeId();
							long newCustomerId = generateRandomLongCustomerId() ;
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
							service.servicelog("Savings or current Account");
							String actType= scanner.nextLine();
							
							service.servicelog("Enter a starting balance");
							long startBalance= Long.parseLong(scanner.nextLine());
							long actId= generateRandomLongAccountId(); 
							long checkUserAccessType = login.getUserId();
							// Generate the Admin Id or employeeId or customerId and pass it into the account creation method using if statements
							// check if logged in user is a standard user
							if(userType.equals(admin) && checkUserAccessType < 1000 ) {
								Account newAccount = new Account(actNumber,newAdminId, actType, startBalance, actId);
								User newUser = new User(newAdminId,userName, firstName,lastName, email, pasword,userType, contactNumber, dob, streetAddress, city, state, zipCode, country);
								bankAccountServices.createAccount(newUser, newAccount);
							}else if(userType.equals(employee) && checkUserAccessType < 1000) {
								Account newAccount = new Account(actNumber,newEmployeeId, actType, startBalance, actId);
								User newUser = new User(newEmployeeId,userName, firstName,lastName, email, pasword,userType, contactNumber, dob, streetAddress, city, state, zipCode, country);
								bankAccountServices.createAccount(newUser, newAccount);
							} else if(userType.equals(customer) && checkUserAccessType < 1000) {
								Account newAccount = new Account(actNumber,newCustomerId, actType, startBalance, actId);
								User newUser = new User(newCustomerId,userName, firstName,lastName, email, pasword,userType, contactNumber, dob, streetAddress, city, state, zipCode, country);
								bankAccountServices.createAccount(newUser, newAccount);
							} else {
								service.servicelog("Access denied.");
							}
							
						} catch (Exception e) {
							throw new BusinessException(e.getMessage());
						}
						
						break;
						
					
						
				
					case 2: 
						try {
							service.servicelog("case-5 was selected");
							service.servicelog("View All Transaction..");
							service.servicelog("Enter account number");
							long actNumber = Long.parseLong(scanner.nextLine());
							bankAccountServices.allCustomerTransaction(actNumber);
						
						}catch (NumberFormatException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;

						
					case 3:
		
						try {
				
								System.out.println("case-6 was selected");
								System.out.println("Customer account view by employee...");
								long employeeId = login.getUserId(); 
								System.out.println("Customer Id Number");
								long customerUserId = Long.parseLong(scanner.nextLine());
								bankAccountServices.customerAccountViewByEmployee(employeeId, customerUserId);
								
				
							
						}catch (BusinessException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;
				
						
					case 4:
						
						try {
				
								System.out.println("case-8 was selected");
								System.out.println("All Customer account view by Admin...");
								long userid = login.getUserId();
								bankAccountServices.viewAllUsers(userid);
									
				
							
						}catch (BusinessException e) {
							System.out.println(e);
							throw new BusinessException("Access denied, please try again");
						}
						break;
					
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

