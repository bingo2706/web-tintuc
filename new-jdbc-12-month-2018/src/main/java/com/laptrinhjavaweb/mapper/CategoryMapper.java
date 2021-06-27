package com.laptrinhjavaweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.laptrinhjavaweb.model.categoryModel;

public class CategoryMapper implements RowMapper<categoryModel>{

	@Override
	public categoryModel mapRow(ResultSet rs) {
		try {
			categoryModel category = new categoryModel();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            category.setCode(rs.getString("code"));
            category.setCreatedDate(rs.getTimestamp("createddate"));
            return category;
		} catch (SQLException e) {
			return null;
		}
		
	}
     
}
