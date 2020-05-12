package com.filemanager.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.filemanager.controller.ConnectionManager;
import com.filemanager.model.PicturesDTO;

public class PublicUserDaoImpl implements PublicUserDao, Serializable {
	private static final long serialVersionUID = 1L;

	private static final String READ_ALL_PUBLIC_PICTURES = "Select * From Pictures Where Type = 'Public';";
	private static final String INSERT_PUBLIC_PICTURES = "Insert Into Pictures (type, bytedata, name, info) "
			+ "Values (?,?,?,?);";
	
	protected Connection conn;
	
	protected PreparedStatement readAllPublicPstmt;
	protected PreparedStatement insertPublicPstmt;
	
	/**
	 * Build connection with the ConnectionManager Enum
	 * and setup the prepared statements with sql queries
	 * created above
	 * 
	 * PostConstruct calls this method after the instance is
	 * created
	 * 
	 * */
	@PostConstruct
	protected void buildConnectionAndStatements() {
		
		try {
			
			conn = ConnectionManager.INSTANCE.getConnection();
			
			readAllPublicPstmt = conn.prepareStatement(READ_ALL_PUBLIC_PICTURES);
			insertPublicPstmt = conn.prepareStatement(INSERT_PUBLIC_PICTURES);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Database Connection/Statement Error.");
		}
	}
	
	/**
	 * Closes the Connection and all of the
	 * statements
	 * 
	 * PreDestroy calls this method before the instance
	 * gets Destroyed/Removed
	 * 
	 * */
	@PreDestroy
	protected void closeConnectionAndStatements() {
		
		try {
			
			readAllPublicPstmt.close();
			insertPublicPstmt.close();
			
			conn.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetch all Pictures of type public from the database
	 * then return them as an arraylist
	 * 
	 * @return ArrayList<PicturesDTO>
	 * 
	 * */
	public List<PicturesDTO> getPublicPictures() {
		
		List<PicturesDTO> myPics = new ArrayList<>();
		
		try {
			
			ResultSet rs = readAllPublicPstmt.executeQuery();
			
			while (rs.next()) {
				
				PicturesDTO pic = new PicturesDTO();
				
				pic.setId(rs.getInt("picid"));
				pic.setType(rs.getString("type"));
				pic.setByteData(rs.getString("bytedata"));
				pic.setName(rs.getString("name"));
				pic.setInfo(rs.getString("info"));
				myPics.add(pic);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public pictures.");
		}
		
		return myPics;
	}
	
	/**
	 * Insert/Upload picture byte String and other variables
	 * to the database into the Pictures Table
	 * 
	 * @param List<PicturesDTO>
	 * 
	 * */
	public void insertPublicPictures(PicturesDTO pic) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicPstmt != null) {
				
				insertPublicPstmt.setString(1, pic.getType());
				insertPublicPstmt.setString(2, pic.getByteData());
				insertPublicPstmt.setString(3, pic.getName());
				insertPublicPstmt.setString(4, pic.getInfo());
					
				insertPublicPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public pictures.");
		}
		
	}
}








