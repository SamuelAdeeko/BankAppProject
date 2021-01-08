package com.adeekobank.serviceImpl;

import java.util.List;

import com.adeeko.dao.impl.BankAccountDAOImpl;
import com.adeekobank.dao.BankAccountDAO;
import com.adeekobank.exception.BusinessException;
import com.adeekobank.model.User;
import com.adeekobank.service.BankAccountServices;

public class BankAccountServicesImpl implements BankAccountServices {
	
	private BankAccountDAO bankAccountDao =  new BankAccountDAOImpl();

	@Override
	public int updateContact(int contact) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePassword(String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> viewAllUsers(int userId) throws BusinessException {
		List<User> allUserViewByAdmin = null;
		
			try {
				if(userId == 100) {
					System.out.println("Access granted to Admin from service");
					allUserViewByAdmin = bankAccountDao.viewAllUsers(userId);
				} else {
					System.out.println("In else");
				System.out.println("Access Failed..Only Admins can access these records.");
				}
			} catch (BusinessException e) {
				throw new BusinessException("Contact SYSTEM ADMINSTRATOR");
			}
		
		return allUserViewByAdmin;
	}

}
