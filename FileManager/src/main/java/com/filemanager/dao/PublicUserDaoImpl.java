package com.filemanager.dao;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.filemanager.controller.ConnectionManager;
import com.filemanager.model.DocumentsDTO;
import com.filemanager.model.PicturesDTO;

public class PublicUserDaoImpl implements PublicUserDao, Serializable {
	private static final long serialVersionUID = 1L;

	//Universal
	private static final String READ_GLOBAL_ID = "Select g_id From Global Where type = (?) And name = (?);";
	private static final String INSERT_PUBLIC_GLOBAL = "Insert Into Global (type, name) Values (?,?);";
	
	//Pictures
	private static final String READ_PIC_BY_GID = "Select * From Pictures Where g_id = (?);";
	private static final String READ_ALL_PUBLIC_PICTURES = "Select * From Pictures Where Type = 'Public';";
	private static final String INSERT_PUBLIC_PICTURES = "Insert Into Pictures (type, bytedata, name, info, g_id) "
			+ "Values (?,?,?,?,?);";
	
	//Documents
	private static final String READ_DOC_BY_GID = "Select * From Documents Where g_id = (?);";
	private static final String READ_ALL_PUBLIC_DOCUMENTS = "Select * From Documents Where Type = 'Public';";
	private static final String INSERT_PUBLIC_DOCUMENTS = "Insert Into Documents (type, bytedata, name, info, g_id) "
			+ "Values (?,?,?,?,?);";
	
	protected Connection conn;
	
	//Universal
	protected PreparedStatement readGlobalIDPstmt;
	protected PreparedStatement insertPublicGloPstmt;
	
	//Pictures
	protected PreparedStatement readPicByGIDPstmt;
	protected PreparedStatement readAllPublicPicPstmt;
	protected PreparedStatement insertPublicPicPstmt;
	
	//Documents
	protected PreparedStatement readDocByGIDPstmt;
	protected PreparedStatement readAllPublicDocPstmt;
	protected PreparedStatement insertPublicDocPstmt;
	
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
			System.out.println("Connection Started...");
			
			//Universal
			readGlobalIDPstmt = conn.prepareStatement(READ_GLOBAL_ID);
			insertPublicGloPstmt = conn.prepareStatement(INSERT_PUBLIC_GLOBAL);
			
			//Pictures
			readPicByGIDPstmt = conn.prepareStatement(READ_PIC_BY_GID);
			readAllPublicPicPstmt = conn.prepareStatement(READ_ALL_PUBLIC_PICTURES);
			insertPublicPicPstmt = conn.prepareStatement(INSERT_PUBLIC_PICTURES);
			
			//Documents
			readDocByGIDPstmt = conn.prepareStatement(READ_DOC_BY_GID);
			readAllPublicDocPstmt = conn.prepareStatement(READ_ALL_PUBLIC_DOCUMENTS);
			insertPublicDocPstmt = conn.prepareStatement(INSERT_PUBLIC_DOCUMENTS);
			
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
			
			//Universal
			readGlobalIDPstmt.close();
			insertPublicGloPstmt.close();
			
			//Pictures
			readPicByGIDPstmt.close();
			readAllPublicPicPstmt.close();
			insertPublicPicPstmt.close();
			
			//Documents
			readDocByGIDPstmt.close();
			readAllPublicDocPstmt.close();
			insertPublicDocPstmt.close();
			
			conn.close();
			System.out.println("Connection Closed...");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("PreDestroy Error.");
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
			
			ResultSet rs = readAllPublicPicPstmt.executeQuery();
			
			while (rs.next()) {
				
				PicturesDTO pic = new PicturesDTO();
				
				pic.setId(rs.getInt("picid"));
				pic.setType(rs.getString("type"));
				pic.setByteData(rs.getString("bytedata"));
				pic.setName(rs.getString("name"));
				pic.setInfo(rs.getString("info"));
				pic.setgID(rs.getInt("g_id"));
				myPics.add(pic);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public pictures.");
		}
		
		return myPics;
	}
	
	/**
	 * Finds the Picture from database based
	 * on given id
	 * 
	 * @param id
	 * 
	 */
	public byte[] getPictureBytesByGID(int id) {
		
		byte[] picBytes = null;
		Blob myBlob;
		
		try {
			
			readPicByGIDPstmt.setInt(1, id);
			ResultSet rs = readPicByGIDPstmt.executeQuery();
			
			while(rs.next()) {
				
				myBlob = rs.getBlob("bytedata");
				int blobLength = (int) myBlob.length();
				picBytes = myBlob.getBytes(1, blobLength);
				myBlob.free();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Caught at getPictureBytesByID");
		}
		
		return picBytes;
	}
	
	/**
	 * Insert/Upload picture byte String and other variables
	 * to the database into the Pictures Table
	 * 
	 * @param PicturesDTO
	 * 
	 * */
	public void insertPublicPictures(PicturesDTO pic) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicPicPstmt != null) {
				
				insertPublicPicPstmt.setString(1, pic.getType());
				insertPublicPicPstmt.setString(2, pic.getByteData());
				insertPublicPicPstmt.setString(3, pic.getName());
				insertPublicPicPstmt.setString(4, pic.getInfo());
				insertPublicPicPstmt.setInt(5, pic.getgID());
					
				insertPublicPicPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public pictures.");
		}
		
	}
	
	/**
	 * Insert global item into the Global table
	 *
	 * @param String type, String name
	 *
	 */
	public int insertPublicGlobalAndReturnID(String type, String name) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicGloPstmt != null) {
				
				insertPublicGloPstmt.setString(1, type);
				insertPublicGloPstmt.setString(2, name);
				
				insertPublicGloPstmt.executeUpdate();
				
				readGlobalIDPstmt.setString(1, type);
				readGlobalIDPstmt.setString(2, name);
				ResultSet rs = readGlobalIDPstmt.executeQuery();
				
				int tempInt = -2;
				
				while(rs.next()) {
					
					tempInt = rs.getInt("g_id");
				}
				
				return tempInt;
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public global.");
		}
		
		return -1; //failed
	}
	
	/**
	 * Gets all documents from Documents table
	 * of public type
	 *
	 * @return List<DocumentsDTO.
	 *
	 */
	public List<DocumentsDTO> getPublicDocuments() {
		
		List<DocumentsDTO> myDocs = new ArrayList<>();
		
		try {
			
			ResultSet rs = readAllPublicDocPstmt.executeQuery();
			
			while (rs.next()) {
				
				DocumentsDTO doc = new DocumentsDTO();
				
				doc.setId(rs.getInt("docid"));
				doc.setType(rs.getString("type"));
				doc.setByteData(rs.getString("bytedata"));
				doc.setName(rs.getString("name"));
				doc.setInfo(rs.getString("info"));
				doc.setgID(rs.getInt("g_id"));
				myDocs.add(doc);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public documents.");
		}
		
		return myDocs;
	}

	/**
	 * Finds the documents from database based
	 * on given id
	 *
	 *
	 * @param id
	 *
	 */
	public byte[] getDocumentBytesByGID(int id) {
		
		byte[] docBytes = null;
		Blob myBlob;
		
		try {
			
			readDocByGIDPstmt.setInt(1, id);
			ResultSet rs = readDocByGIDPstmt.executeQuery();
			
			while(rs.next()) {
				
				myBlob = rs.getBlob("bytedata");
				int blobLength = (int) myBlob.length();
				docBytes = myBlob.getBytes(1, blobLength);
				myBlob.free();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Caught at getDocumentBytesByID");
		}
		
		return docBytes;
	}
	
	/**
	 * Insert/Upload document byte String and other Variables
	 * to the database into the Documents Table
	 *
	 * @param DocumentsDTO
	 * 
	 */
	public void insertPublicDocuments(DocumentsDTO doc) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicDocPstmt != null) {
				
				insertPublicDocPstmt.setString(1, doc.getType());
				insertPublicDocPstmt.setString(2, doc.getByteData());
				insertPublicDocPstmt.setString(3, doc.getName());
				insertPublicDocPstmt.setString(4, doc.getInfo());
				insertPublicDocPstmt.setInt(5, doc.getgID());
				
				insertPublicDocPstmt.executeUpdate();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public documents.");
		}
	}
}








