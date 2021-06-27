package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.laptrinhjavaweb.dao.IUserDAO;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.RoleModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.paging.Pageble;

public class UserDAO implements IUserDAO{
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
	public UserModel findByUserNameAndPasswordAndStatus(String username, String password, Integer status) {
		StringBuilder sql = new StringBuilder("SELECT * FROM USER AS u");
		sql.append(" INNER JOIN role AS r ON r.id=u.roleid");
		sql.append(" WHERE u.name = ? AND PASSWORD = ? AND STATUS = ?");
		List<UserModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setInt(3, status);
				rs = ps.executeQuery();
				while(rs.next()){
					UserModel user = new UserModel();
				    user.setId(rs.getLong("id"));
				    user.setUsername(rs.getString("name"));
				    user.setPassword(rs.getString("password"));
				    user.setFullname(rs.getString("fullname"));
				    user.setStatus(rs.getInt("status"));
				    RoleModel role = new RoleModel();
				    role.setCode(rs.getString("code"));
				    role.setName(rs.getString(12));
				    user.setRole(role);
					user.setCreatedDate(rs.getTimestamp("createddate"));
					user.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						user.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						user.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(user);
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
	public List<UserModel> findAll(Pageble pageble) {
		List<UserModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    StringBuilder sql = new StringBuilder("SELECT * FROM USER,ROLE WHERE USER.ROLEID = ROLE.ID ");
	    if(pageble.getSorter() != null && StringUtils.isNotBlank(pageble.getSorter().getSortName())&& StringUtils.isNotBlank(pageble.getSorter().getSortBy())){
	    	sql.append("ORDER BY "+pageble.getSorter().getSortName()+" "+pageble.getSorter().getSortBy()+"");
	    }
	    if(pageble.getOffset() != null && pageble.getLimit() !=null &&  pageble.getLimit() != 0 ){
	    	sql.append(" LIMIT "+pageble.getOffset()+", "+pageble.getLimit()+"");
	    }
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql.toString());
				rs = ps.executeQuery();
				while(rs.next()){
					UserModel users = new UserModel();
					users.setId(rs.getLong("id"));
					users.setUsername(rs.getString("name"));
					users.setPassword(rs.getString("password"));
					users.setFullname(rs.getString("fullname"));
					users.setStatus(rs.getInt("status"));
					users.setRoleId(rs.getLong("roleid"));
					users.setRoleCode(rs.getString("code"));
					users.setCreatedDate(rs.getTimestamp("createddate"));
					users.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						users.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						users.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(users);
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
	public Long save(UserModel userModel) {
		Connection connection = null;
		StringBuilder sql = new StringBuilder("INSERT INTO user (name, password,");
		sql.append(" fullname, status, roleid, createddate, createdby)");
		sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), statement.RETURN_GENERATED_KEYS);
			statement.setString(1, userModel.getUsername());
			statement.setString(2, userModel.getPassword());
			statement.setString(3, userModel.getFullname());
			statement.setInt(4, 1);
			statement.setLong(5, userModel.getRoleId());
			statement.setTimestamp(6, userModel.getCreatedDate());
			statement.setString(7, userModel.getCreatedBy());
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
	@Override
	public UserModel findOne(Long id) {
		List<UserModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM user WHERE id = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while(rs.next()){
					UserModel users = new UserModel();
					users.setId(rs.getLong("id"));
					users.setUsername(rs.getString("name"));
					users.setPassword(rs.getString("password"));
					users.setFullname(rs.getString("fullname"));
					users.setStatus(rs.getInt("status"));
					users.setRoleId(rs.getLong("roleid"));
					users.setCreatedDate(rs.getTimestamp("createddate"));
					users.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						users.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						users.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(users);
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
	public int getCountItems() {
		 Connection connection = getConnection();
		    String sql = "SELECT count(*) FROM user";
		    PreparedStatement ps = null;
		    
		    ResultSet rs = null;
		    if(connection != null){
		    	try {
					ps = connection.prepareStatement(sql);
					int count = 0;
					rs = ps.executeQuery();
					while(rs.next()){
						count = rs.getInt(1);
					}
					
					return count;
				} catch (SQLException e) {	
					return 0;
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
						return 0;
					}
					
				}
		    }
			return 0;
	}
	@Override
	public void Update(UserModel userModel) {
		String sql = "update user set name =?, password =?, fullname =?, roleid=?, createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? where id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
	
		try {
		    
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, userModel.getUsername());
			statement.setString(2, userModel.getPassword());
			statement.setString(3, userModel.getFullname());
			statement.setLong(4, userModel.getRoleId());
			statement.setTimestamp(5, userModel.getCreatedDate());
			statement.setString(6, userModel.getCreatedBy());
			statement.setTimestamp(7, userModel.getModifieddate());
			statement.setString(8, userModel.getModifiedBy());
			statement.setLong(9, userModel.getId());
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
		String sql = "DELETE FROM USER WHERE id = ?";
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
     
}
