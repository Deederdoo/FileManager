package com.filemanager.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.filemanager.dao.PublicUserDao;
import com.filemanager.model.DocumentsDTO;
import com.filemanager.model.MiscDTO;
import com.filemanager.model.MusicDTO;
import com.filemanager.model.PicturesDTO;
import com.filemanager.model.VideosDTO;

@Named("publicFileUpload")
@SessionScoped
public class PublicFileUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected List<String> categories;
	protected String category;
	
	protected StreamedContent dynamicImage;
	
	@Inject
	protected ExternalContext externalContext;
	
	protected PublicUserDao dao;
	
	protected List<PicturesDTO> pics;
	protected List<DocumentsDTO> docs;
	protected List<MusicDTO> music;
	protected List<VideosDTO> videos;
	protected List<MiscDTO> misc;

	@Inject
	protected PicturesDTO pic;
	
	@Inject
	public PublicFileUpload(PublicUserDao pubDao) {
		
		this.dao = pubDao;
	}
	
	/**
	 * Pulls data from the given directory and
	 * adds the directory items to a String list
	 * 
	 * */
	public List<?> showList() {
		
		if(category != null && !category.contains("Please Select")) {
			
			switch(category) {
			
			case "Pictures":
				
				if(pics != null) {
					
					pics.clear();
				}
				
				setPics(dao.getPublicPictures());
				
				return pics;
				
			case "Documents":
				
				if(docs != null) {
					
					docs.clear();
				}
				
				setDocs(dao.getPublicDocuments());
				
				return docs;
				
			case "Music":
				
				if(music != null) {
					
					music.clear();
				}
				
				setMusic(dao.getPublicMusic());
				
				return music;
				
			case "Videos":

				if(videos != null) {
					
					videos.clear();
				}
				
				setVideos(dao.getPublicVideos());
				
				return videos;
				
			case "Misc":
				
				if(misc != null) {
					
					misc.clear();
				}
				
				setMisc(dao.getPublicMisc());
				
				return misc;
				
			case "Please Select":
				
				//Send error message
				break;
			}
		}
		
		return null;
	}
	
	/**
	 * Display the currently selected Directory
	 * 
	 * Note: Used to filter out the 
	 * "Please Select" category
	 * 
	 * */
	public String showCurrentFolder() {
		
		if(category != null) {
			
			if(category.contains("Please Select")) {
				
				return "";
				
			}else {
				
				return category;
			}
		}
		
		return "";
	}
	
	/**
	 * Instantiates the Categories List
	 * 
	 * */
	public List<String> generateCategories(){
		
		categories = new ArrayList<>();
		categories.add("Please Select");
		categories.add("Documents");
		categories.add("Pictures");
		categories.add("Videos");
		categories.add("Music");
		categories.add("Misc");
		
		return categories;
	}
	
	/**
	 * Takes the uploaded files and sends them to their
	 * respective directory
	 * 
	 * */
	public String uploadMultipleFiles(FileUploadEvent event) {
		
		UploadedFile upFile = event.getFile();
		
		if(upFile != null) {
			
			try {
				
				switch(category) {
				
				case "Pictures":
					
					PicturesDTO pic = new PicturesDTO();
					byte[] picArray = upFile.getContent();
					String picString = Base64.getEncoder().encodeToString(picArray);
					
					int gPicID = dao.insertPublicGlobalAndReturnID("Public", upFile.getFileName());
					
					pic.setType("Public");
					pic.setByteData(picString);
					pic.setName(upFile.getFileName());
					pic.setInfo("");
					pic.setgID(gPicID);
					
					dao.insertPublicPictures(pic);
					
					break;
					
				case "Documents":
					
					DocumentsDTO doc = new DocumentsDTO();
					byte[] docArray = upFile.getContent();
					String docString = Base64.getEncoder().encodeToString(docArray);
					
					int gDocID = dao.insertPublicGlobalAndReturnID("Public", upFile.getFileName());
					
					doc.setType("Public");
					doc.setByteData(docString);
					doc.setName(upFile.getFileName());
					doc.setInfo("");
					doc.setgID(gDocID);

					dao.insertPublicDocuments(doc);
					
					break;
					
				case "Music":
					
					MusicDTO music = new MusicDTO();
					byte[] musicArray = upFile.getContent();
					String musicString = Base64.getEncoder().encodeToString(musicArray);
					
					int gMusicID = dao.insertPublicGlobalAndReturnID("Public", upFile.getFileName());
					
					music.setType("Public");
					music.setByteData(musicString);
					music.setName(upFile.getFileName());
					music.setInfo("");
					music.setgID(gMusicID);

					dao.insertPublicMusic(music);
					
					break;
					
				case "Videos":
					
					VideosDTO vids = new VideosDTO();
					byte[] vidsArray = upFile.getContent();
					String vidsString = Base64.getEncoder().encodeToString(vidsArray);
					
					int gVideoID = dao.insertPublicGlobalAndReturnID("Public", upFile.getFileName());
					
					vids.setType("Public");
					vids.setByteData(vidsString);
					vids.setName(upFile.getFileName());
					vids.setInfo("");
					vids.setgID(gVideoID);

					dao.insertPublicVideos(vids);
					
					break;
					
				case "Misc":
					
					MiscDTO misc = new MiscDTO();
					byte[] miscArray = upFile.getContent();
					String miscString = Base64.getEncoder().encodeToString(miscArray);
					
					int gMiscID = dao.insertPublicGlobalAndReturnID("Public", upFile.getFileName());
					
					misc.setType("Public");
					misc.setByteData(miscString);
					misc.setName(upFile.getFileName());
					misc.setInfo("");
					misc.setgID(gMiscID);

					dao.insertPublicMisc(misc);
					
					break;
					
				case "Please Select":
					
					//Send error message
					break;
				
				}
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		}
		
		return "main_menu.xhtml?faces-redirect=true";
	}
	
	/**
	 * 
	 *  http://javaonlineguide.net/2016/01/how-to-display-images-in-datatable-using-pgraphicimage-in-primefaces.html
	 *  
	 *  I used the guide above to get the images to render, I've added my own twist but most of the work
	 *  was taken from this
	 *
	 */
	public StreamedContent getDynamicImage() throws SQLException, IOException {
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			
			return new DefaultStreamedContent();
			
		}else {
			
			String id = context.getExternalContext().getRequestParameterMap().get("pid");
			
			switch(category) {
			
			case "Pictures":
				
				byte[] picByteArray = dao.getPictureBytesByGID(Integer.parseInt(id));
				
				if(picByteArray != null) {
					
					picByteArray = Base64.getDecoder().decode(picByteArray);
					
					InputStream dbStream = new ByteArrayInputStream(picByteArray);
					
					return new DefaultStreamedContent(dbStream);
				}
				
			case "Documents":
				
				byte[] docByteArray = dao.getDocumentBytesByGID(Integer.parseInt(id));
				
				if(docByteArray != null) {
					
					docByteArray = Base64.getDecoder().decode(docByteArray);
					
					InputStream dbStream = new ByteArrayInputStream(docByteArray);
					
					return new DefaultStreamedContent(dbStream);
				}
				
			case "Music":
				
				byte[] musicByteArray = dao.getMusicBytesByGID(Integer.parseInt(id));
				
				if(musicByteArray != null) {
					
					musicByteArray = Base64.getDecoder().decode(musicByteArray);
					
					InputStream dbStream = new ByteArrayInputStream(musicByteArray);
					
					return new DefaultStreamedContent(dbStream);
				}
				
			case "Videos":
				
				byte[] videoByteArray = dao.getVideosBytesByGID(Integer.parseInt(id));
				
				if(videoByteArray != null) {
					
					videoByteArray = Base64.getDecoder().decode(videoByteArray);
					
					InputStream dbStream = new ByteArrayInputStream(videoByteArray);
					
					return new DefaultStreamedContent(dbStream);
				}
				
			case "Misc":
				
				byte[] miscByteArray = dao.getMiscBytesByGID(Integer.parseInt(id));
				
				if(miscByteArray != null) {
					
					miscByteArray = Base64.getDecoder().decode(miscByteArray);
					
					InputStream dbStream = new ByteArrayInputStream(miscByteArray);
					
					return new DefaultStreamedContent(dbStream);
				}
				
			case "Please Select":
				
				System.out.println("Please Select");
				
				break;
			}
		}
		
		return null;
	}

	public void setDynamicImage(StreamedContent dynamicImage) {
		this.dynamicImage = dynamicImage;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public PicturesDTO getPic() {
		return pic;
	}

	public void setPic(PicturesDTO pic) {
		this.pic = pic;
	}

	public List<PicturesDTO> getPics() {
		return pics;
	}

	public void setPics(List<PicturesDTO> pics) {
		this.pics = pics;
	}

	public List<DocumentsDTO> getDocs() {
		return docs;
	}

	public void setDocs(List<DocumentsDTO> docs) {
		this.docs = docs;
	}

	public List<MusicDTO> getMusic() {
		return music;
	}

	public void setMusic(List<MusicDTO> music) {
		this.music = music;
	}

	public List<VideosDTO> getVideos() {
		return videos;
	}

	public void setVideos(List<VideosDTO> videos) {
		this.videos = videos;
	}

	public List<MiscDTO> getMisc() {
		return misc;
	}

	public void setMisc(List<MiscDTO> misc) {
		this.misc = misc;
	}
}
