package com.filemanager.dao;

import java.util.List;

import com.filemanager.model.PicturesDTO;

public interface PublicUserDao {

	public List<PicturesDTO> getPublicPictures();
	
	public void insertPublicPictures(List<PicturesDTO> pics);
	
}
