package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.laptrinhjavaweb.dao.ICommentDAO;
import com.laptrinhjavaweb.model.CommentModel;
import com.laptrinhjavaweb.model.UserModel;

public class CommentDAO implements ICommentDAO{

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
	public List<CommentModel> findAll(Long newId) {
		List<CommentModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM COMMENT,user WHERE NEWSID = ? AND comment.userid = user.id ";
	  
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, newId);
				rs = ps.executeQuery();
				while(rs.next()){
					CommentModel comment = new CommentModel();
					comment.setId(rs.getLong("id"));
					comment.setContent(rs.getString("content"));
					comment.setUserId(rs.getLong("userid"));
					comment.setNewId(rs.getLong("newsid"));
					comment.setNameUser(rs.getString("name"));
					comment.setParentId(rs.getLong("parentid"));
					comment.setCreatedDate(rs.getTimestamp("createddate"));
					comment.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						comment.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						comment.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(comment);
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
	public Long save(CommentModel comment) {
		Connection connection = null;
		StringBuilder sql = new StringBuilder("INSERT INTO comment (content, userid,");
		sql.append(" newsid,createddate, createdby, parentid )");
		sql.append(" VALUES(?, ?, ?, ?, ?, ?)");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), statement.RETURN_GENERATED_KEYS);
			statement.setString(1, comment.getContent());
			statement.setLong(2, comment.getUserId());
			statement.setLong(3, comment.getNewId());
			statement.setTimestamp(4, comment.getCreatedDate());
			statement.setString(5, comment.getCreatedBy());
			statement.setLong(6, comment.getParentId());
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
	public void Update(CommentModel comment) {
    String sql = "update comment set content =?, createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? where id = ?";
		
		Connection connection = null;
		PreparedStatement statement = null;
	
		try {
		    
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			statement.setString(1, comment.getContent());
			statement.setTimestamp(2, comment.getCreatedDate());
			statement.setString(3, comment.getCreatedBy());
			statement.setTimestamp(4, comment.getModifieddate());
			statement.setString(5, comment.getModifiedBy());
			statement.setLong(6, comment.getId());
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
		String sql = "DELETE FROM comment WHERE id = ?";
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
	public CommentModel findOne(Long id) {
		List<CommentModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM comment WHERE id = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while(rs.next()){
					CommentModel comment = new CommentModel();
					comment.setId(rs.getLong("id"));
					comment.setContent(rs.getString("content"));
					comment.setUserId(rs.getLong("userid"));
					comment.setNewId(rs.getLong("newsid"));
					comment.setCreatedDate(rs.getTimestamp("createddate"));
					comment.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						comment.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						comment.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(comment);
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
      

