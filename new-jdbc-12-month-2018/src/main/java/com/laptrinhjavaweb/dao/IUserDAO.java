package com.laptrinhjavaweb.dao;

import java.util.List;


import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface IUserDAO {
	public UserModel findByUserNameAndPasswordAndStatus(String username,String password,Integer status);
	List<UserModel> findAll(Pageble pageble);
	Long save(UserModel userModel);
	UserModel findOne(Long id);
	int getCountItems ();
	void Update(UserModel userModel);
	void Delete(Long id);
}
