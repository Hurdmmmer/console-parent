package com.tdh.gps.console.service.api;

import java.util.List;

import com.tdh.gps.console.model.Users;


public interface UserInfoService {
	
	Users findById(long id);
	
	Users findByName(String name);
	
	void saveUser(Users user);
	
	void updateUser(Users user);
	
	void deleteUserById(long id);

	List<Users> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(Users user);
	
}
