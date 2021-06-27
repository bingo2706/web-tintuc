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

import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public class NewDAO implements INewDAO{
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
	public List<NewModel> findByCategoryId(Long categoryId) {
		List<NewModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM news where categoryid = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, categoryId);
				rs = ps.executeQuery();
				while(rs.next()){
					NewModel news = new NewModel();
					news.setId(rs.getLong("id"));
					news.setTitle(rs.getString("title"));
					news.setContent(rs.getString("content"));
					news.setCategoryid(rs.getLong("categoryid"));
					news.setThumbnail(rs.getString("thumbnail"));
					news.setShortdescription(rs.getString("shortdescription"));
					news.setCreatedDate(rs.getTimestamp("createddate"));
					news.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						news.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						news.setModifiedBy(rs.getString("modifiedby"));
					}
					
		            results.add(news);
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
	public Long save(NewModel newModel) {
		Connection connection = null;
		StringBuilder sql = new StringBuilder("INSERT INTO news (title, content,");
		sql.append(" thumbnail, shortdescription, categoryid, createddate, createdby)");
		sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Long id = null;
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), statement.RETURN_GENERATED_KEYS);
			statement.setString(1, newModel.getTitle());
			statement.setString(2, newModel.getContent());
			statement.setString(3, newModel.getThumbnail());
			statement.setString(4, newModel.getShortdescription());
			statement.setLong(5, newModel.getCategoryid());
			statement.setTimestamp(6, newModel.getCreatedDate());
			statement.setString(7, newModel.getCreatedBy());
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
	public NewModel findOne(Long id) {
		List<NewModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    String sql = "SELECT * FROM news WHERE id = ?";
	    PreparedStatement ps = null;
	    
	    ResultSet rs = null;
	    if(connection != null){
	    	try {
				ps = connection.prepareStatement(sql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				while(rs.next()){
					NewModel news = new NewModel();
					news.setId(rs.getLong("id"));
					news.setTitle(rs.getString("title"));
					news.setContent(rs.getString("content"));
					news.setCategoryid(rs.getLong("categoryid"));
					news.setThumbnail(rs.getString("thumbnail"));
					news.setShortdescription(rs.getString("shortdescription"));
					news.setCreatedDate(rs.getTimestamp("createddate"));
					news.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						news.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						news.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(news);
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
	public void update(NewModel updateNew) {
		
		StringBuilder sql = new StringBuilder("UPDATE news SET title = ?, thumbnail = ?,");
		sql.append(" shortdescription = ?, content = ?, categoryid = ?,");
		sql.append(" createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? WHERE id = ?");
		Connection connection = null;
		PreparedStatement statement = null;
	
		try {
		    
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString());
			statement.setString(1, updateNew.getTitle());
			statement.setString(2, updateNew.getThumbnail());
			statement.setString(3, updateNew.getShortdescription());
			statement.setString(4, updateNew.getContent());
			statement.setLong(5, updateNew.getCategoryid());
			statement.setTimestamp(6, updateNew.getCreatedDate());
			statement.setString(7, updateNew.getCreatedBy());
			statement.setTimestamp(8, updateNew.getModifieddate());
			statement.setString(9, updateNew.getModifiedBy());
			statement.setLong(10, updateNew.getId());
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
	public void delete(long id) {
		String sql = "DELETE FROM news WHERE id = ?";
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
	public List<NewModel> findAll(Pageble pageble) {
		List<NewModel> results = new ArrayList<>();
	    Connection connection = getConnection();
	    StringBuilder sql = new StringBuilder("SELECT * FROM NEWS ");
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
					NewModel news = new NewModel();
					news.setId(rs.getLong("id"));
					news.setTitle(rs.getString("title"));
					news.setContent(rs.getString("content"));
					news.setCategoryid(rs.getLong("categoryid"));
					news.setThumbnail(rs.getString("thumbnail"));
					news.setShortdescription(rs.getString("shortdescription"));
					news.setCreatedDate(rs.getTimestamp("createddate"));
					news.setCreatedBy(rs.getString("createdby"));
					if (rs.getTimestamp("modifieddate") != null) {
						news.setModifieddate(rs.getTimestamp("modifieddate"));
					}
					if (rs.getString("modifiedby") != null) {
						news.setModifiedBy(rs.getString("modifiedby"));
					}
			
					
		            results.add(news);
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
	public int getCountItems() {
		
	    Connection connection = getConnection();
	    String sql = "SELECT count(*) FROM news";
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

	

	
      
}
