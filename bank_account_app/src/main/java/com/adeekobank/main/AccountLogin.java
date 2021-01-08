package com.adeekobank.main;

import java.util.Scanner;

import com.adeeko.dao.impl.BankAccountDAOImpl;
import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.User;

public class AccountLogin {

	
	public static void main(String[] args) throws BusinessException {
		
	//	BankAccountServices bankAccountServices = new BankAccountServicesImpl();
		BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();
		
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
			User login = bankAccountDao.userLogin(username, password);
			if(login != null) {
	
				System.out.println("Access granted");
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
					System.out.println("1) Update contact number");
					System.out.println("2) Update email address ");
					System.out.println("3) View all users by Admin ");
					
					
					choice = Integer.parseInt(scanner.nextLine());
					switch (choice) {
					
					case 1: 
						System.out.println("case-1 was selected");
						System.out.println("Update contact number in progress........ ");
						break;
						
					case 2: 
						System.out.println("case-2 was selected");
						System.out.println("Update email address in progress........ ");
						break;
				
					case 3: 
						System.out.println("case-3 was selected");
						System.out.println("Enter userId: ");
					
//						try {
//							int userId = Integer.parseInt(scanner.nextLine());
//							System.out.println("Printing all users details.....");
//							List<User> listOfAllUsers = bankAccountServices.viewAllUsers(userId);
//							if(listOfAllUsers != null && listOfAllUsers.size() > 0) {
//								System.out.println("The total number of records found is: " + listOfAllUsers.size());
//								System.out.println("Printing users information....");
//								for(User i : listOfAllUsers) {
//									System.out.println(i);
//								}
//						}
//							} catch (BusinessException | NumberFormatException e) {
//								System.out.println("Access Denied, Contact Admin");
//								System.out.println(e.getMessage());
//						}
//						break;	
						
					case 4:
						System.out.println("case-4 was selected");
						break;
					}
		 
					} while (choice!= 5);


		}
		}catch (BusinessException e){
			System.out.println("Invalid login information, please try again");
			System.out.println(e);
		}
		
	
		
	
		
}
	
	}


