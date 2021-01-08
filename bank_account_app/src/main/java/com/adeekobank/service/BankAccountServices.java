package com.adeekobank.service;

import java.util.List;

import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.User;

public interface BankAccountServices {
	
	public int updateContact(int contact);
	public int updateEmail(String email);
	public int updatePassword(String password);
	public List<User> viewAllUsers(int userId) throws BusinessException ;

}
