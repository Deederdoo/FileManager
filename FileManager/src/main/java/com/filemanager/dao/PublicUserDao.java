package com.filemanager.dao;

import java.util.List;

import com.filemanager.model.DocumentsDTO;
import com.filemanager.model.MiscDTO;
import com.filemanager.model.MusicDTO;
import com.filemanager.model.PicturesDTO;
import com.filemanager.model.VideosDTO;

public interface PublicUserDao {

	//Universal
	public int insertPublicGlobalAndReturnID(String type, String name);
	
	//Pictures
	public List<PicturesDTO> getPublicPictures();
	public void insertPublicPictures(PicturesDTO pic);
	public byte[] getPictureBytesByGID(int id);
	
	//Documents
	public List<DocumentsDTO> getPublicDocuments();
	public void insertPublicDocuments(DocumentsDTO doc);
	public byte[] getDocumentBytesByGID(int id);
	
	//Videos
	public List<VideosDTO> getPublicVideos();
	public void insertPublicVideos(VideosDTO vid);
	public byte[] getVideosBytesByGID(int id);
	
	//Music
	public List<MusicDTO> getPublicMusic();
	public void insertPublicMusic(MusicDTO music);
	public byte[] getMusicBytesByGID(int id);
	
	//Misc
	public List<MiscDTO> getPublicMisc();
	public void insertPublicMisc(MiscDTO misc);
	public byte[] getMiscBytesByGID(int id);
}
