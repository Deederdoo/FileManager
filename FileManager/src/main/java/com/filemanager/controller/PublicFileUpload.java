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
import com.filemanager.model.PicturesDTO;

@Named("publicFileUpload")
@SessionScoped
public class PublicFileUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected List<String> categories;
	protected String category;
	
	protected StreamedContent dynamicImage;
	protected int dyID;
	
	@Inject
	protected ExternalContext externalContext;
	
	protected PublicUserDao dao;
	
	protected List<PicturesDTO> pics;
	protected List<DocumentsDTO> docs;

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
				
				//code here
				break;
				
			case "Videos":
				
				//code here
				break;
				
			case "Misc":
				
				//code here
				break;
				
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
					
					//code here
					break;
					
				case "Videos":
					
					//code here
					break;
					
				case "Misc":
					
					//code here
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
				
				System.out.println("Music");
				
				break;
				
			case "Videos":
				
				System.out.println("Videos");
				
				break;
				
			case "Misc":
				
				System.out.println("Misc");
				
				break;
				
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

	public int getDyID() {
		return dyID;
	}

	public void setDyID(int dyID) {
		this.dyID = dyID;
	}

	public List<DocumentsDTO> getDocs() {
		return docs;
	}

	public void setDocs(List<DocumentsDTO> docs) {
		this.docs = docs;
	}
}
