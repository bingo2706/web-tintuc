package com.laptrinhjavaweb.service;

import java.util.List;


import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface IUserService {
	UserModel findByUserNameAndPasswordAndStatus(String username,String password,Integer status);
	UserModel save(UserModel userModel);
	UserModel findOne(Long id);
	List<UserModel> findAll(Pageble pageble);
	int getCountItems ();
	 void delete(long[] ids);
	 UserModel Update(UserModel userModel);
}
