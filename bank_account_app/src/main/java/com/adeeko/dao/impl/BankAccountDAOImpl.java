package com.adeeko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.Request;

import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.dbutil.PostgresqlConnection;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.Account;
import com.adeekobank.model.Balance;
import com.adeekobank.model.Transaction;
import com.adeekobank.model.User;

public class BankAccountDAOImpl implements BankAccountDAO {

	@Override
	public User userLogin(String username, String password) throws BusinessException {
		User user = null;
//		Map<String, String> map = new HashMap <String, String>();
//		map.put(username, password);
		//if()
	try(Connection connection = PostgresqlConnection.getConnection()) {
		String sql = "select userid, firstname, lastname, email, usertype, contact from adeekobank.users where username = ? AND password = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		ResultSet resultSet = preparedStatement.executeQuery();
		System.out.println("Query executed");
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
			System.out.println("Invalid username and password");
			
		}
	} catch (ClassNotFoundException | SQLException e) {
		throw new BusinessException("Invalid User, contact SYS Admin");
	} 
		
		
		return user;
	}

	@Override
	public int createAccount(User user, long balance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Balance accountBalanceViewByCustomer(int userId, long accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance customerWithdrawal(long amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance customerDeposit(long amount) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Account customerAccountViewByEmployee(int employeeId, int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int registerForCustomerAccountByUser(String userName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Transaction transferMoneyToAnotherAccount(long amount, int accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Balance acceptTransferToCustomerAccount(long amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateContact(int contact) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEmail(String email) {
		int c = 0;
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, password, usertype, contact from adeekobank.users where email = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "email");
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("Query executed");
			
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
	public int updatePassword(String password) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<User> viewAllUsers(int userId) throws BusinessException {
		List<User> usersList = new ArrayList<>();
		try(Connection connection = PostgresqlConnection.getConnection()) {
			String sql = "select userid, username, firstname, lastname, email, password, usertype, contact from adeekobank.users";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query executed");
			while(resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("userid"));
				user.setUserName(resultSet.getString("username"));
				user.setFirstName(resultSet.getString("firstname"));
				user.setLastName(resultSet.getString("lastname"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType(resultSet.getString("usertype"));
				user.setContact(resultSet.getInt("contact"));
				usersList.add(user);
			}
			if(usersList.size() == 0 ) {
				throw new BusinessException("No account user found");
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("in exception");
			System.out.println(e);
			throw new BusinessException("Internal error occured, contact SYS Admin");
			
		} 
		
		return usersList;
	}

}
