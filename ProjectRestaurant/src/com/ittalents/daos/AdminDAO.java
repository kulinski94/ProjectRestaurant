package com.ittalents.daos;

import java.security.NoSuchAlgorithmException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import com.ittalents.pojos.HashCoding;
import com.ittalents.pojos.RegisterWaiter;

@Component("adminDAO")
public class AdminDAO extends ObjectDAO{

	public void registerWaiter(RegisterWaiter waiter) throws DuplicateKeyException{
		String sql = "insert into RESTAURANT.WAITER (username,password,fName,lName) VALUES (?,?,?,?)";
		try {
			jdbc.update(sql, new Object[] {waiter.getUsername(),HashCoding.md5(waiter.getPassword()),waiter.getfName(),waiter.getlName() });
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

}
