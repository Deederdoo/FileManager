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

	private static final String READ_PIC_BY_ID = "Select * From Pictures Where PicID = (?);";
	private static final String READ_ALL_PUBLIC_PICTURES = "Select * From Pictures Where Type = 'Public';";
	private static final String INSERT_PUBLIC_PICTURES = "Insert Into Pictures (type, bytedata, name, info) "
			+ "Values (?,?,?,?);";
	
	private static final String READ_DOC_BY_ID = "Select * From Documents Where DocID = (?);";
	private static final String READ_ALL_PUBLIC_DOCUMENTS = "Select * From Documents Where Type = 'Public';";
	private static final String INSERT_PUBLIC_DOCUMENTS = "Insert Into Documents (type, bytedata, name, info) "
			+ "Values (?,?,?,?);";
	
	protected Connection conn;
	
	//Pictures
	protected PreparedStatement readPicByIDPstmt;
	protected PreparedStatement readAllPublicPicPstmt;
	protected PreparedStatement insertPublicPicPstmt;
	
	//Documents
	protected PreparedStatement readDocByIDPstmt;
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
			
			//Pictures
			readPicByIDPstmt = conn.prepareStatement(READ_PIC_BY_ID);
			readAllPublicPicPstmt = conn.prepareStatement(READ_ALL_PUBLIC_PICTURES);
			insertPublicPicPstmt = conn.prepareStatement(INSERT_PUBLIC_PICTURES);
			
			//Documents
			readDocByIDPstmt = conn.prepareStatement(READ_DOC_BY_ID);
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
			
			//Pictures
			readPicByIDPstmt.close();
			readAllPublicPicPstmt.close();
			insertPublicPicPstmt.close();
			
			//Documents
			readDocByIDPstmt.close();
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
	public byte[] getPictureBytesByID(int id) {
		
		byte[] picBytes = null;
		Blob myBlob;
		
		try {
			
			readPicByIDPstmt.setInt(1, id);
			ResultSet rs = readPicByIDPstmt.executeQuery();
			
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
					
				insertPublicPicPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public pictures.");
		}
		
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
	public byte[] getDocumentBytesByID(int id) {
		
		byte[] docBytes = null;
		Blob myBlob;
		
		try {
			
			readDocByIDPstmt.setInt(1, id);
			ResultSet rs = readDocByIDPstmt.executeQuery();
			
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
				
				insertPublicDocPstmt.executeUpdate();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public documents.");
		}
	}
}








