package com.filemanager.dao;

import java.util.List;

import com.filemanager.model.DocumentsDTO;
import com.filemanager.model.PicturesDTO;

public interface PublicUserDao {

	//Pictures
	public List<PicturesDTO> getPublicPictures();
	public void insertPublicPictures(PicturesDTO pic);
	public byte[] getPictureBytesByID(int id);
	
	//Documents
	public List<DocumentsDTO> getPublicDocuments();
	public void insertPublicDocuments(DocumentsDTO doc);
	public byte[] getDocumentBytesByID(int id);
}
