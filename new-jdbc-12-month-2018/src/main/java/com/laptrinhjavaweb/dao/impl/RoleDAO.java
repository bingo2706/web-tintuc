package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.laptrinhjavaweb.dao.IRoleDAO;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.RoleModel;
import com.laptrinhjavaweb.model.categoryModel;

public class RoleDAO implements IRoleDAO{
	 ResourceBundle mybundle = ResourceBundle.getBundle("db");
	public Connection getConnection(){
		try {
			Class.forName(mybundle.getString("driverName"));
			String url = mybundle.getString("url");
			String user= mybundle.getString("user");
			String password = mybundle.getString("password");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException |SQLException e) {
			return null;
			
		} 
	}
	
	@Override
	public RoleModel findOneByCode(String code) {
		List<RoleModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM role WHERE code = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setString(1, code);
				rs = ps.executeQuery();
				while(rs.next()){
					RoleModel roleModel = new RoleModel();			
					roleModel.setId(rs.getLong("id"));
					roleModel.setCode(rs.getString("code"));
					roleModel.setName(rs.getString("name"));
					results.add(roleModel);
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
	public List<RoleModel> findAll() {
		List<RoleModel> results = new ArrayList<>();
	    Connection connection = getConnection();
		String sql = "SELECT * FROM ROLE";
		 PreparedStatement ps = null;
		    
		    ResultSet rs = null;
		    if(connection != null){
		    	try {
					ps = connection.prepareStatement(sql.toString());
					rs = ps.executeQuery();
					while(rs.next()){
						RoleModel role = new RoleModel();
						role.setId(rs.getLong("id"));
						role.setName(rs.getString("name"));
						role.setCode(rs.getString("code"));
						role.setCreatedDate(rs.getTimestamp("createddate"));
						role.setCreatedBy(rs.getString("createdby"));
						if (rs.getTimestamp("modifieddate") != null) {
							role.setModifieddate(rs.getTimestamp("modifieddate"));
						}
						if (rs.getString("modifiedby") != null) {
							role.setModifiedBy(rs.getString("modifiedby"));
						}
				
						
			            results.add(role);
					}
					
					return results;
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
	public RoleModel findOne(Long id) {
		List<RoleModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM role WHERE id = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while(rs.next()){
					RoleModel roleModel = new RoleModel();			
					roleModel.setId(rs.getLong("id"));
					roleModel.setCode(rs.getString("code"));
					roleModel.setName(rs.getString("name"));
					results.add(roleModel);
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

}
