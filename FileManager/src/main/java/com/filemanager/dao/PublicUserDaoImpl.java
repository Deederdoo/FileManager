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
import com.filemanager.model.MiscDTO;
import com.filemanager.model.MusicDTO;
import com.filemanager.model.PicturesDTO;
import com.filemanager.model.VideosDTO;

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
	
	//Videos
	private static final String READ_VID_BY_GID = "Select * From Videos Where g_id = (?);";
	private static final String READ_ALL_PUBLIC_VIDEOS = "Select * From Videos Where Type = 'Public';";
	private static final String INSERT_PUBLIC_VIDEOS = "Insert Into Videos (type, bytedata, name, info, g_id) "
			+ "Values (?,?,?,?,?);";
	
	//Music
	private static final String READ_MUSIC_BY_GID = "Select * From Music Where g_id = (?);";
	private static final String READ_ALL_PUBLIC_MUSIC = "Select * From Music Where Type = 'Public';";
	private static final String INSERT_PUBLIC_MUSIC = "Insert Into Music (type, bytedata, name, info, g_id) "
			+ "Values (?,?,?,?,?);";
	
	//Misc
	private static final String READ_MISC_BY_GID = "Select * From Misc Where g_id = (?);";
	private static final String READ_ALL_PUBLIC_MISC = "Select * From Misc Where Type = 'Public';";
	private static final String INSERT_PUBLIC_MISC = "Insert Into Misc (type, bytedata, name, info, g_id) "
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
	
	//Videos
	protected PreparedStatement readVidByGIDPstmt;
	protected PreparedStatement readAllPublicVidPstmt;
	protected PreparedStatement insertPublicVidPstmt;
	
	//Music
	protected PreparedStatement readMusicByGIDPstmt;
	protected PreparedStatement readAllPublicMusicPstmt;
	protected PreparedStatement insertPublicMusicPstmt;
	
	//Misc
	protected PreparedStatement readMiscByGIDPstmt;
	protected PreparedStatement readAllPublicMiscPstmt;
	protected PreparedStatement insertPublicMiscPstmt;
	
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
			
			//Videos
			readVidByGIDPstmt = conn.prepareStatement(READ_VID_BY_GID);
			readAllPublicVidPstmt = conn.prepareStatement(READ_ALL_PUBLIC_VIDEOS);
			insertPublicVidPstmt = conn.prepareStatement(INSERT_PUBLIC_VIDEOS);
			
			//Music
			readMusicByGIDPstmt = conn.prepareStatement(READ_MUSIC_BY_GID);
			readAllPublicMusicPstmt = conn.prepareStatement(READ_ALL_PUBLIC_MUSIC);
			insertPublicMusicPstmt = conn.prepareStatement(INSERT_PUBLIC_MUSIC);
			
			//Misc
			readMiscByGIDPstmt = conn.prepareStatement(READ_MISC_BY_GID);
			readAllPublicMiscPstmt = conn.prepareStatement(READ_ALL_PUBLIC_MISC);
			insertPublicMiscPstmt = conn.prepareStatement(INSERT_PUBLIC_MISC);
			
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
			
			//Videos
			readVidByGIDPstmt.close();
			readAllPublicVidPstmt.close();
			insertPublicVidPstmt.close();
			
			//Music
			readMusicByGIDPstmt.close();
			readAllPublicMusicPstmt.close();
			insertPublicMusicPstmt.close();
			
			//Misc
			readMiscByGIDPstmt.close();
			readAllPublicMiscPstmt.close();
			insertPublicMiscPstmt.close();
			
			conn.close();
			System.out.println("Connection Closed...");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("PreDestroy Error.");
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
	 * @return byte[] picBytes
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
	 * @return byte[] docBytes
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

	/**
	 * 
	 *
	 * @return List<VideosDTO> myVids
	 *
	 */
	public List<VideosDTO> getPublicVideos() {
		
		List<VideosDTO> myVids = new ArrayList<>();
		
		try {
			
			ResultSet rs = readAllPublicVidPstmt.executeQuery();
			
			while (rs.next()) {
				
				VideosDTO vid = new VideosDTO();
				
				vid.setId(rs.getInt("videoid"));
				vid.setType(rs.getString("type"));
				vid.setByteData(rs.getString("bytedata"));
				vid.setName(rs.getString("name"));
				vid.setInfo(rs.getString("info"));
				vid.setgID(rs.getInt("g_id"));
				myVids.add(vid);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public videos.");
		}
		
		return myVids;
	}

	/**
	 *
	 * 
	 * @param VideosDTO vid
	 *
	 */
	public void insertPublicVideos(VideosDTO vid) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicVidPstmt != null) {
				
				insertPublicVidPstmt.setString(1, vid.getType());
				insertPublicVidPstmt.setString(2, vid.getByteData());
				insertPublicVidPstmt.setString(3, vid.getName());
				insertPublicVidPstmt.setString(4, vid.getInfo());
				insertPublicVidPstmt.setInt(5, vid.getgID());
					
				insertPublicVidPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public videos.");
		}
	}

	/**
	 * 
	 * @param int id
	 * @return byte[] vidBytes
	 *
	 */
	public byte[] getVideosBytesByGID(int id) {
		
		byte[] vidBytes = null;
		Blob myBlob;
		
		try {
			
			readVidByGIDPstmt.setInt(1, id);
			ResultSet rs = readVidByGIDPstmt.executeQuery();
			
			while(rs.next()) {
				
				myBlob = rs.getBlob("bytedata");
				int blobLength = (int) myBlob.length();
				vidBytes = myBlob.getBytes(1, blobLength);
				myBlob.free();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Caught at getVideosBytesByID");
		}
		
		return vidBytes;
	}

	/**
	 *
	 * @return List<MusicDTO> myMusic
	 *
	 */
	public List<MusicDTO> getPublicMusic() {
		
		List<MusicDTO> myMusic = new ArrayList<>();
		
		try {
			
			ResultSet rs = readAllPublicMusicPstmt.executeQuery();
			
			while (rs.next()) {
				
				MusicDTO music = new MusicDTO();
				
				music.setId(rs.getInt("musicid"));
				music.setType(rs.getString("type"));
				music.setByteData(rs.getString("bytedata"));
				music.setName(rs.getString("name"));
				music.setInfo(rs.getString("info"));
				music.setgID(rs.getInt("g_id"));
				myMusic.add(music);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public videos.");
		}
		
		return myMusic;
	}
	
	/**
	 *
	 * @param MusicDTO music
	 *
	 */
	public void insertPublicMusic(MusicDTO music) {
	
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicMusicPstmt != null) {
				
				insertPublicMusicPstmt.setString(1, music.getType());
				insertPublicMusicPstmt.setString(2, music.getByteData());
				insertPublicMusicPstmt.setString(3, music.getName());
				insertPublicMusicPstmt.setString(4, music.getInfo());
				insertPublicMusicPstmt.setInt(5, music.getgID());
					
				insertPublicMusicPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public music.");
		}
	}

	/**
	 *
	 * @param int id
	 * @return byte[] musicBytes
	 *
	 */
	public byte[] getMusicBytesByGID(int id) {
		
		byte[] musicBytes = null;
		Blob myBlob;
		
		try {
			
			readMusicByGIDPstmt.setInt(1, id);
			ResultSet rs = readMusicByGIDPstmt.executeQuery();
			
			while(rs.next()) {
				
				myBlob = rs.getBlob("bytedata");
				int blobLength = (int) myBlob.length();
				musicBytes = myBlob.getBytes(1, blobLength);
				myBlob.free();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Caught at getMusicBytesByID");
		}
		
		return musicBytes;
	}

	/**
	 *
	 * @return List<MiscDTO> myMisc
	 *
	 */
	public List<MiscDTO> getPublicMisc() {
		
		List<MiscDTO> myMisc = new ArrayList<>();
		
		try {
			
			ResultSet rs = readAllPublicMiscPstmt.executeQuery();
			
			while (rs.next()) {
				
				MiscDTO misc = new MiscDTO();
				
				misc.setId(rs.getInt("miscid"));
				misc.setType(rs.getString("type"));
				misc.setByteData(rs.getString("bytedata"));
				misc.setName(rs.getString("name"));
				misc.setInfo(rs.getString("info"));
				misc.setgID(rs.getInt("g_id"));
				myMisc.add(misc);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Error reading all public misc.");
		}
		
		return myMisc;
	}

	/**
	 *
	 * @param MiscDTO misc
	 *
	 */
	public void insertPublicMisc(MiscDTO misc) {
		
		try {
			
			buildConnectionAndStatements();
			
			if(insertPublicMiscPstmt != null) {
				
				insertPublicMiscPstmt.setString(1, misc.getType());
				insertPublicMiscPstmt.setString(2, misc.getByteData());
				insertPublicMiscPstmt.setString(3, misc.getName());
				insertPublicMiscPstmt.setString(4, misc.getInfo());
				insertPublicMiscPstmt.setInt(5, misc.getgID());
					
				insertPublicMiscPstmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Error inserting public misc.");
		}
	}

	/**
	 *
	 * @param int id
	 * @return byte[] miscBytes
	 *
	 */
	public byte[] getMiscBytesByGID(int id) {
		
		byte[] miscBytes = null;
		Blob myBlob;
		
		try {
			
			readMiscByGIDPstmt.setInt(1, id);
			ResultSet rs = readMiscByGIDPstmt.executeQuery();
			
			while(rs.next()) {
				
				myBlob = rs.getBlob("bytedata");
				int blobLength = (int) myBlob.length();
				miscBytes = myBlob.getBytes(1, blobLength);
				myBlob.free();
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			System.out.println("Caught at getMusicBytesByID");
		}
		
		return miscBytes;
	}
}








