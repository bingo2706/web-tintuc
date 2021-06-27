package com.laptrinhjavaweb.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.model.RoleModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.model.categoryModel;
import com.laptrinhjavaweb.service.ICategoryService;

public class CategoryService implements ICategoryService{
    
	@Inject
	private ICategoryDAO categoryDao;
	
	@Override
	public List<categoryModel> findAll() {
		
		return categoryDao.findAll();
	}

	@Override
	public categoryModel save(categoryModel categoryModel) {
		categoryModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	
		Long categoryid = categoryDao.save(categoryModel);	
		return categoryDao.findOne(categoryid);
	}

	@Override
	public void delete(long[] ids) {
		for (long id: ids) {	
			categoryDao.Delete(id);
		}

	}

	@Override
	public categoryModel Update(categoryModel categoryModel) {
		categoryModel oldCategory = categoryDao.findOne(categoryModel.getId());
		categoryModel.setCreatedDate(oldCategory.getCreatedDate());
		categoryModel.setCreatedBy(oldCategory.getCreatedBy());
		categoryModel.setModifieddate(new Timestamp(System.currentTimeMillis()));
		categoryDao.Update(categoryModel);
		return categoryDao.findOne(categoryModel.getId());
	}

	@Override
	public categoryModel findOne(Long id) {
		// TODO Auto-generated method stub
		return categoryDao.findOne(id);
	}
       
}
