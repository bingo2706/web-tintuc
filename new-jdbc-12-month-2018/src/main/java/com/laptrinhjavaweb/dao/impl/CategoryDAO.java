package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.mapper.CategoryMapper;
import com.laptrinhjavaweb.model.categoryModel;

public class CategoryDAO extends AbstractDAO<categoryModel> implements ICategoryDAO {

	@Override
	public List<categoryModel> findAll() {
		String sql = "SELECT * FROM category";
		return query(sql, new CategoryMapper());
	}

	@Override
	public categoryModel findOneByCode(String code) {
		List<categoryModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM category WHERE code = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, code);
				rs = ps.executeQuery();
				while(rs.next()){
					categoryModel categorymodel = new categoryModel();			
					categorymodel.setId(rs.getLong("id"));
					categorymodel.setCode(rs.getString("code"));
					categorymodel.setName(rs.getString("name"));
					categorymodel.setCreatedDate(rs.getTimestamp("createddate"));
					results.add(categorymodel);
				}
				
				 return results.isEmpty() ? null : results.get(0);
			} catch (SQLException e) {	
				return null;
			} finally{
				try {
					if(connection != null){
						connection.close();
					}
					if(ps != null){
						ps.close();
					}
					if(rs != null){
						rs.close();
					}
				} catch (SQLException e) {
					return null;
				}
				
			}
	    }
	   return null;
		
	}

	@Override
	public categoryModel findOne(Long id) {
		List<categoryModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM category WHERE id = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while(rs.next()){
					categoryModel categorymodel = new categoryModel();			
					categorymodel.setId(rs.getLong("id"));
					categorymodel.setCode(rs.getString("code"));
					categorymodel.setName(rs.getString("name"));
					results.add(categorymodel);
				}
				
				 return results.isEmpty() ? null : results.get(0);
			} catch (SQLException e) {	
				return null;
			} finally{
				try {
					if(connection != null){
						connection.close();
					}
					if(ps != null){
						ps.close();
					}
					if(rs != null){
						rs.close();
					}
				} catch (SQLException e) {
					return null;
				}
				
			}
	    }
	   return null;
	}


	@Override
	public void Update(categoryModel categoryModel) {
String sql = "update category set name =?, code =?, createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? where id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
	
		try {
		    
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, categoryModel.getName());
			statement.setString(2, categoryModel.getCode());
			statement.setTimestamp(3, categoryModel.getCreatedDate());
			statement.setString(4, categoryModel.getCreatedBy());
			statement.setTimestamp(5, categoryModel.getModifieddate());
			statement.setString(6, categoryModel.getModifiedBy());
			statement.setLong(7, categoryModel.getId());
			statement.executeUpdate();
			connection.commit();
	
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public void Delete(Long id) {
		String sql = "DELETE FROM category WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
		
	

	@Override
	public Long save(categoryModel categoryModel) {
		Connection connection = null;
		StringBuilder sql = new StringBuilder("INSERT INTO category (name, code,");
		sql.append(" createddate, createdby)");
		sql.append(" VALUES(?, ?, ?, ?)");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), statement.RETURN_GENERATED_KEYS);
			statement.setString(1, categoryModel.getName());
			statement.setString(2, categoryModel.getCode());
			statement.setTimestamp(3, categoryModel.getCreatedDate());
			statement.setString(4, categoryModel.getCreatedBy());
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
			return id;
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	

}
