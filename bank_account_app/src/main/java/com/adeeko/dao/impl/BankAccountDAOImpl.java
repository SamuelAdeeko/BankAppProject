package com.adeeko.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.dbutil.PostgresqlConnection;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

import log4j_adeekobank.service.AdeekoBankLogService;

public class BankAccountDAOImpl implements BankAccountDAO {
	AdeekoBankLogService service = new AdeekoBankLogService();
	
	@Override  //works
	public User userLogin(String username, String password) throws BusinessException {
		User user = null;
		
	try(Connection connection = PostgresqlConnection.getConnection()) {
		String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users where username = ? AND password = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery();
	
		if(resultSet.next()) {
			user = new User();
			user.setUserName(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setUserId(resultSet.getInt("userid"));
			user.setFirstName(resultSet.getString("firstname"));
			user.setLastName(resultSet.getString("lastname"));
			user.setEmail(resultSet.getString("email"));
			user.setUserType(resultSet.getString("usertype"));
			user.setContact(resultSet.getInt("contact"));
			
		} else {
			service.servicelog("========================================");
			service.servicelog("Username and passowrd does not exist.");
			service.servicelog("========================================");
		}
	} catch (ClassNotFoundException | SQLException e) {
		service.servicelog("========================================");
		throw new BusinessException("Invalid User, contact SYS Admin");	
	} 
		
		return user;
	}
	
	@Override  
	public int createAccount(User user, Account account) throws BusinessException {
		int c = 0;
		
		// insert into account table data and user table....
		// I used PreparedStatement to insert into account and user tables in the same method
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.accounts (accountnumber, userid, accounttype,balance, account_id) values ( ?, ? , ?,?,? )" ;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, account.getAccountNumber());
			preparedStatement.setLong(2, account.getUserId());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.setLong(4, account.getBalance());
			preparedStatement.setLong(5, account.getAccount_id());
			preparedStatement.executeUpdate();
			// end of update in account table
			
			String sql1 = "insert into adeekobank.users (userid, username, firstname, lastname, email, password, usertype, contact, dob, streetAddress, city, state, country, zip_code) values ( ?, ? , ?,?,?,?,?,?,?,?,?,?,?,? )";
			PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
			preparedStatement1.setLong(1, user.getUserId());
			preparedStatement1.setString(2, user.getUserName());
			preparedStatement1.setString(3, user.getFirstName());
			preparedStatement1.setString(4, user.getLastName());
			preparedStatement1.setString(5, user.getEmail());
			preparedStatement1.setString(6, user.getPassword());
			preparedStatement1.setString(7, user.getUserType());
			preparedStatement1.setLong(8, user.getContact());
			preparedStatement1.setString(9, user.getDob()); 		
			preparedStatement1.setString(10, user.getStreetAddress());
			preparedStatement1.setString(11, user.getCity());
			preparedStatement1.setString(12, user.getState());
			preparedStatement1.setString(13, user.getCountry());
			preparedStatement1.setLong(14, user.getZipCode());
			preparedStatement1.executeUpdate();
			// end of update into user table
			service.servicelog("========================================");
			service.servicelog("User Profile And Account Created Successfully.");
			service.servicelog("========================================");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Invalid operation, contact SYS Admin");
		} 
		
		return c;
	}


	@Override  //works
	public long customerWithdrawal(Transaction transaction) throws BusinessException, ClassNotFoundException {
		long c = 0;
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.transactions (transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype) values ( ?,?,?,?,? )";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, transaction.getTransactionId() );
			preparedStatement.setLong(2, transaction.getAmount());
			preparedStatement.setLong(3, transaction.getSenderAccountNumber());
			preparedStatement.setLong(4, transaction.getRecieverAccountNumber());
			preparedStatement.setString(5, transaction.getTransactionType());
			c = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Internal error occured, Contact SYS Admin");
		} 
		
		return c;
	}

	@Override  //works
	public long customerDeposit(Transaction transaction) throws ClassNotFoundException, BusinessException  {
		int c = 0;
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.transactions (transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype) values ( ?,?,?,?,? )";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, transaction.getTransactionId() );
			preparedStatement.setLong(2, transaction.getAmount());
			preparedStatement.setLong(3, transaction.getSenderAccountNumber());
			preparedStatement.setLong(4, transaction.getRecieverAccountNumber());
			preparedStatement.setString(5, transaction.getTransactionType());
			c = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Internal error occured, Contact SYS Admin");
		} 
		
		return c;
	}
	
	@Override   //works
	public long accountBalanceViewByCustomer(long accountNumber) throws BusinessException {
		long c =0;
		
		try (Connection connection = PostgresqlConnection.getConnection()){
			String sql = "select accountnumber, userid, accounttype,balance, account_id from adeekobank.accounts where accountnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				service.servicelog("========================================");
				service.servicelog("Your account Balance of: " + resultSet.getLong("balance"));
				c = resultSet.getLong("balance");
			} 
		} catch (SQLException | ClassNotFoundException e) {
		
			throw new BusinessException("Internal error occured, contact SYS Admin");
		} 
		return c;
	}


	@Override
	public boolean accountStatusByEmployee(long employeeId) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override   // works
	public void customerAccountViewByEmployee(long employeeId, long userId) throws BusinessException {
		
		
		try (Connection connection= PostgresqlConnection.getConnection()){
		String sql = "select accountnumber, userid, accounttype, balance, account_id from adeekobank.accounts where userid = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, userId);
		ResultSet resultset = preparedStatement.executeQuery();
		while(resultset.next()) {
			service.servicelog("========================================");
			service.servicelog("Account Number: " + resultset.getLong("accountnumber"));
			service.servicelog("UserId: "+ resultset.getInt("userid"));
			service.servicelog("Account Type: " + resultset.getString("accounttype"));
			service.servicelog("Account Balance: " + resultset.getLong("balance"));
			service.servicelog("Account Id: " + resultset.getInt("account_id"));
			service.servicelog("========================================");
		} 
		} catch ( SQLException | ClassNotFoundException e) {
			service.servicelog("====================================================");
			throw new BusinessException("Invalid account number. Contact SYS Admin");
		}
	
	}
		

	
	@Override // edit to remove user argument and replace with account ref type
    public int registerForCustomerAccountByUser(long standardUserId, Account account) throws BusinessException, ClassNotFoundException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.accounts (accountnumber, userid, accounttype, balance, account_id) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account.getAccountNumber());
			preparedStatement.setLong(2, account.getUserId());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.setLong(4, account.getBalance());
			preparedStatement.setLong(5, account.getAccount_id());
	//	preparedStatement.setDate(9, new java.sql.Date(user.getDob().getTime()));
			c = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new BusinessException("Error occured, Contact SYS Admin");
		}
		return c;
	}

	@Override  //works
	public long transferMoneyToAnotherAccount(Transaction transaction) throws BusinessException {
		long c = 0;
		
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.transactions (transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype) values ( ?,?,?,?,? )";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, transaction.getTransactionId() );
			preparedStatement.setLong(2, transaction.getAmount());
			preparedStatement.setLong(3, transaction.getSenderAccountNumber());
			preparedStatement.setLong(4, transaction.getRecieverAccountNumber());
			preparedStatement.setString(5, transaction.getTransactionType());
			c = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			
			throw new BusinessException("Internal error, contact SYS Admin");
		}
    
		return c;
	}
	

	@Override
	public long acceptTransferToCustomerAccount(Transaction transaction ) throws ClassNotFoundException, BusinessException{
		long c = 0;
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.transactions (transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype) values ( ?,?,?,?,? )";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, transaction.getTransactionId() );
			preparedStatement.setLong(2, transaction.getAmount());
			preparedStatement.setLong(3, transaction.getSenderAccountNumber());
			preparedStatement.setLong(4, transaction.getRecieverAccountNumber());
			preparedStatement.setString(5, transaction.getTransactionType());
			c = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new BusinessException("Internal error occured, Contact SYS Admin");
		} 
		
		return c;
	}

	@Override  //works
	public int updateContact(long userID, long contact) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set contact = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, contact);
			preparedStatement.setLong(2, userID);
			c = preparedStatement.executeUpdate();
			service.servicelog("========================================");
			service.servicelog("Contact number updated successfully.");
			service.servicelog("========================================");
		} catch (SQLException | ClassNotFoundException  e) {
			service.servicelog("========================================");
			throw new BusinessException("Invalid contact number");	
		} return c;	
	}

	@Override  //works
	public int updateEmail(long userId, String email) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set email = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setLong(2, userId);
			c = preparedStatement.executeUpdate();
			service.servicelog("========================================");
			service.servicelog("Email updated successfully.");
			service.servicelog("========================================");
		} catch (SQLException | ClassNotFoundException  e) {
			service.servicelog("========================================");
			throw new BusinessException("Invalid email address");	
		} return c;	
	}

	

	@Override   // works
	public int updatePassword(long userId, String password) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set password = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, password);
			preparedStatement.setLong(2, userId);
			c = preparedStatement.executeUpdate();
			service.servicelog("========================================");
			service.servicelog("Password Updated successfully.");
			service.servicelog("========================================");
		} catch (SQLException | ClassNotFoundException  e) {
			service.servicelog("========================================");
			throw new BusinessException("Invalid email address");	
		} 
		return c;	
	}


	@Override  // works
	public void viewAllUsers(long userId) throws BusinessException {

		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				service.servicelog("UserId: " + resultSet.getInt("userid"));
				service.servicelog("Username: " + resultSet.getString("username"));
				service.servicelog("Firstname: " + resultSet.getString("firstname"));
				service.servicelog("Lastname: " + resultSet.getString("lastname"));
				service.servicelog("Email: " + resultSet.getString("email"));
				service.servicelog("Password: " + resultSet.getString("password"));
				service.servicelog("User type: " + resultSet.getString("usertype"));
				service.servicelog("Contact Number: " + resultSet.getLong("contact"));
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			service.servicelog("======================================================");
			throw new BusinessException("Internal error occured, contact SYS Admin");
		}
	}

	@Override
	public long searchAccountNumber(long accountNumber) throws BusinessException {
		long c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select accountnumber, userid,accounttype,balance,account_id from adeekobank.accounts where accountnumber = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				c = resultSet.getLong("accountnumber");
			} else {
				service.servicelog("Account number not found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error occured, contact SYS Admin");
		} 
		
		return c;
	}

	@Override
	public void updateAccountBal(long accountNumber, long balance) throws BusinessException {
		
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.accounts set balance = ? where accountnumber = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, balance);
			preparedStatement.setLong(2, accountNumber);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Invalid operation, contact SYS Admin");
		} 
		
	
	}

	@Override
	public long checkUserType(long userId) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				c = resultSet.getInt("userid");
			} else {
				service.servicelog("No record for userId found.");
			}
		} catch (ClassNotFoundException |SQLException e) {
			throw new BusinessException("Invalid operation, contact SYS Admin");
		} 
		return c;
	}

	@Override
	public long allCustomerTransaction(long accountNumber) throws BusinessException {
		long c = 0;
		try (Connection connection = PostgresqlConnection.getConnection()){
			// Queries the database for account number in sender account number column
			// preparedStatement.setLong(1, accountNumber) passes account number into the parameter to query
			String sql = "select transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype from adeekobank.transactions where recieveraccountnumber = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				System.out.println("Transaction Id: " + resultSet.getLong("transactionid"));
				System.out.println("Amount: " + resultSet.getLong("amount"));
				System.out.println("senderaccountnumber: " + resultSet.getLong("senderaccountnumber"));
				System.out.println("recieveraccountnumber: " + resultSet.getLong("recieveraccountnumber"));
				System.out.println("transactiontype: " + resultSet.getString("transactiontype"));
			} 
			// Queries the database for account number in sender account number column
			String sql1 = "select transactionid, amount, senderaccountnumber, recieveraccountnumber, transactiontype from adeekobank.transactions where senderaccountnumber = ? ";
			PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
			preparedStatement1.setLong(1, accountNumber);
			ResultSet resultSet1 = preparedStatement1.executeQuery();
			
			while(resultSet1.next()) {
				service.servicelog("Transaction Id: " + resultSet1.getLong("transactionid"));
				service.servicelog("Amount: " + resultSet1.getLong("amount"));
				service.servicelog("senderaccountnumber: " + resultSet1.getLong("senderaccountnumber"));
				service.servicelog("recieveraccountnumber: " + resultSet1.getLong("recieveraccountnumber"));
				service.servicelog("transactiontype: " + resultSet1.getString("transactiontype"));
			} 
			
			
		} catch (SQLException | ClassNotFoundException e) {
			throw new BusinessException("Internal error, contact SYS Admin");
		} 
		return c;
	}

	

}
