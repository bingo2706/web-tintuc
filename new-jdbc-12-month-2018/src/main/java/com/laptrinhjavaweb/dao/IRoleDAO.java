package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.RoleModel;


public interface IRoleDAO {
 RoleModel findOneByCode(String code);
 List<RoleModel> findAll();
 RoleModel findOne(Long id);
}
