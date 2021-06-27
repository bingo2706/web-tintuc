package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.IRoleDAO;
import com.laptrinhjavaweb.dao.IUserDAO;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.RoleModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.IUserService;

public class UserService implements IUserService{
    @Inject
    private IUserDAO userDao;
    @Inject
    private IRoleDAO roleDao;
	@Override
	public UserModel findByUserNameAndPasswordAndStatus(String username, String password, Integer status) {
	
		return userDao.findByUserNameAndPasswordAndStatus(username, password, status);
	}
	@Override
	public UserModel save(UserModel userModel) {
		userModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		RoleModel roleModel = roleDao.findOneByCode(userModel.getRoleCode());
		userModel.setRoleId(roleModel.getId());
		Long userid = userDao.save(userModel);	
		return userDao.findOne(userid);
	}
	@Override
	public List<UserModel> findAll(Pageble pageble) {
	    
		return userDao.findAll(pageble);
	}
	@Override
	public int getCountItems() {
		// TODO Auto-generated method stub
		return userDao.getCountItems();
	}
	@Override
	public UserModel findOne(Long id) {
		UserModel userModel = userDao.findOne(id);
		RoleModel roleMode = roleDao.findOne(userModel.getRoleId());
		userModel.setRoleCode(roleMode.getCode());
		return userModel;
	}
	@Override
	public void delete(long[] ids) {
		for (long id: ids) {	
			userDao.Delete(id);
		}
		
	}
	@Override
	public UserModel Update(UserModel userModel) {
		UserModel oldUser = userDao.findOne(userModel.getId());
		userModel.setCreatedDate(oldUser.getCreatedDate());
		userModel.setCreatedBy(oldUser.getCreatedBy());
		userModel.setModifieddate(new Timestamp(System.currentTimeMillis()));
		RoleModel roleModel = roleDao.findOneByCode(userModel.getRoleCode());
		userModel.setRoleId(roleModel.getId());
		userDao.Update(userModel);		
		return userDao.findOne(userModel.getId());
	}
 
}
