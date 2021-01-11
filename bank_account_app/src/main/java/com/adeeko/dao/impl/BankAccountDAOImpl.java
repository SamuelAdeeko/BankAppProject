package com.adeeko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.dbutil.PostgresqlConnection;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public class BankAccountDAOImpl implements BankAccountDAO {

	@Override  //works
	public User userLogin(String username, String password) throws BusinessException {
		User user = null;
		
	try(Connection connection = PostgresqlConnection.getConnection()) {
		String sql = "select userid, firstname, lastname, email, usertype, contact from adeekobank.users where username = ? AND password = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery();
	
		if(resultSet.next()) {
			System.out.println("In DAO");
			user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setUserId(resultSet.getInt("userid"));
			user.setFirstName(resultSet.getString("firstname"));
			user.setLastName(resultSet.getString("lastname"));
			user.setEmail(resultSet.getString("email"));
			user.setUserType(resultSet.getString("usertype"));
			user.setContact(resultSet.getInt("contact"));
			
		} else {
			System.out.println("========================================");
			System.out.println("Username and passowrd does not exist.");
			System.out.println("========================================");
		}
	} catch (ClassNotFoundException | SQLException e) {
		System.out.println("========================================");
		throw new BusinessException("Invalid User, contact SYS Admin");	
	} 
		
		return user;
	}
	
	@Override    //works
	public int createAccount(Account account) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.accounts (accountnumber, userid, accounttype,balance, account_id) values ( ?, ? , ?,?,? )" ;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, account.getAccountNumber());
			preparedStatement.setInt(2, account.getUserId());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.setLong(4, account.getBalance());
			preparedStatement.setInt(5, account.getAccount_id());
			c = preparedStatement.executeUpdate();
			System.out.println("Update executed");
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Invalid operation, contact SYS Admin");
		} 
		
		return c;
	}


	@Override  //works
	public int customerWithdrawal(Transaction transaction) throws BusinessException, ClassNotFoundException {
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
			System.out.println("Query executed");

		} catch (SQLException e) {
			e.printStackTrace();
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
			System.out.println("Query executed");

		} catch (SQLException e) {
			e.printStackTrace();
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
			System.out.println("Query executed");
			while(resultSet.next()) {
//				System.out.println("Account Number: " + accountNumber);
//				System.out.println("User id: " + userId);
//				System.out.println("Account Type: " + resultSet.getString("accounttype"));
				System.out.println("Balance: " + resultSet.getLong("balance"));
	//			System.out.println("Account Id: " + resultSet.getInt("account_id"));
				c = resultSet.getLong("balance");
		
			} 
		} catch (SQLException | ClassNotFoundException e) {
			throw new BusinessException("Invalid account number");
		} 
		return c;
	}

	

	@Override
	public void rejectInvalidTransactions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean accountStatusByEmployee(int employeeId) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override   // works
	public void customerAccountViewByEmployee(int employeeId, int userId) throws BusinessException {
		
		
		try (Connection connection= PostgresqlConnection.getConnection()){
		String sql = "select accountnumber, userid, accounttype, balance, account_id from adeekobank.accounts where userid = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, userId);
		ResultSet resultset = preparedStatement.executeQuery();
		System.out.println("Query executed");
		while(resultset.next()) {
			System.out.println("========================================");
			System.out.println("Account Number: " + resultset.getLong("accountnumber"));
			System.out.println("UserId: "+ resultset.getInt("userid"));
			System.out.println("Account Type: " + resultset.getString("accounttype"));
			System.out.println("Account Balance: " + resultset.getLong("balance"));
			System.out.println("Account Id: " + resultset.getInt("account_id"));
			System.out.println("========================================");
		} 
		} catch ( SQLException | ClassNotFoundException e) {
			System.out.println("====================================================");
			throw new BusinessException("Invalid account number. Contact SYS Admin");
		}
	
	}
		

	
	@Override // edit to remove user argument and replace with account ref type
    public int registerForCustomerAccountByUser(int standardUserId, Account account) throws BusinessException, ClassNotFoundException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.accounts (accountnumber, userid, accounttype, balance, account_id) values (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account.getAccountNumber());
			preparedStatement.setInt(2, account.getUserId());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.setLong(4, account.getBalance());
			preparedStatement.setInt(5, account.getAccount_id());
	//	preparedStatement.setDate(9, new java.sql.Date(user.getDob().getTime()));
			c = preparedStatement.executeUpdate();
			System.out.println("Query executed"); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("Error occured, Contact SYS Admin");
		}
		return c;
	}

	@Override  //works
	public int transferMoneyToAnotherAccount(Account account) throws BusinessException {
		int c = 0;
		
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "insert into adeekobank.accounts (accountnumber,userid,accounttype,balance,account_id) values ( ?,?,?,?,?) ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, account.getAccountNumber());
			preparedStatement.setInt(2, account.getUserId());
			preparedStatement.setString(3, account.getAccountType());
			preparedStatement.setLong(4, account.getBalance());
			preparedStatement.setInt(5, account.getAccount_id());
			c = preparedStatement.executeUpdate();
			System.out.println("Query executed");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException("Internal error, contact SYS Admin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
		return c;
	}

	@Override
	public int acceptTransferToCustomerAccount(Transaction transaction ) throws ClassNotFoundException, BusinessException{
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
			System.out.println("Query executed");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Internal error occured, Contact SYS Admin");
		} 
		
		return c;
	}

	@Override  //works
	public int updateContact(int userID, long contact) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set contact = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, contact);
			preparedStatement.setInt(2, userID);
			c = preparedStatement.executeUpdate();
			
		} catch (SQLException | ClassNotFoundException  e) {
			System.out.println("========================================");
			throw new BusinessException("Invalid contact number");	
		} return c;	
	}

	@Override  //works
	public int updateEmail(int userId, String email) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set email = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, userId);
			c = preparedStatement.executeUpdate();
			
		} catch (SQLException | ClassNotFoundException  e) {
			System.out.println("========================================");
			throw new BusinessException("Invalid email address");	
		} return c;	
	}

	

	@Override   // works
	public int updatePassword(int userId, String password) throws BusinessException {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "update adeekobank.users set password = ? where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, password);
			preparedStatement.setInt(2, userId);
			c = preparedStatement.executeUpdate();
			
		} catch (SQLException | ClassNotFoundException  e) {
			System.out.println("========================================");
			throw new BusinessException("Invalid email address");	
		} return c;	
	}


	@Override  // works
	public void viewAllUsers(int userId) throws BusinessException {

		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query executed");
			while(resultSet.next()) {
				System.out.println("UserId: " + resultSet.getInt("userid"));
				System.out.println("Username: " + resultSet.getString("username"));
				System.out.println("Firstname: " + resultSet.getString("firstname"));
				System.out.println("Lastname: " + resultSet.getString("lastname"));
				System.out.println("Email: " + resultSet.getString("email"));
				System.out.println("Password: " + resultSet.getString("password"));
				System.out.println("User type: " + resultSet.getString("usertype"));
				System.out.println("Contact Number: " + resultSet.getLong("contact"));
				
				
//				User user = new User();
//				user.setUserId(resultSet.getInt("userid")));
//				user.setUserName(resultSet.getString("username"));
//				user.setFirstName(resultSet.getString("firstname"));
//				user.setLastName(resultSet.getString("lastname"));
//				user.setEmail(resultSet.getString("email"));
//				user.setPassword(resultSet.getString("password"));
//				user.setUserType(resultSet.getString("usertype"));
//				user.setContact(resultSet.getInt("contact"));
//				usersList.add(user);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("======================================================");
			System.out.println(e);
			throw new BusinessException("Internal error occured, contact SYS Admin");
			
		}
	}

	@Override
	public long searchAccountNumber(long accountNumber) {
		long c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select accountnumber, userid,accounttype,balance,account_id from adeekobank.accounts where accountnumber = ? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, accountNumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query executed in DAO");
			if(resultSet.next()) {
				System.out.println("Account Number: " + resultSet.getLong("accountnumber"));
				c = resultSet.getLong("accountnumber");
			} else {
				System.out.println("Account number not found");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			System.out.println("Updated new account bal executed");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new BusinessException("Invalid operation, contact SYS Admin");
		} 
		
	
	}

	@Override
	public int checkUserType(int userId) {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users where userid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query executed");
			if(resultSet.next()) {
				c = resultSet.getInt("userid");
			} else {
				System.out.println("No record for userId found.");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}


	
	
	

	

	
	

	
	



	


	

}
