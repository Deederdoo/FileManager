package com.filemanager.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.filemanager.dao.PublicUserDao;
import com.filemanager.model.ItemDTO;
import com.filemanager.model.PicturesDTO;

@Named("publicFileUpload")
@SessionScoped
public class PublicFileUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected List<String> categories;
	protected String category;
	
	private List<ItemDTO> myItems;
	private ItemDTO item;
	
	@Inject
	protected ExternalContext externalContext;
	
	protected PublicUserDao dao;
	
	@Inject
	public PublicFileUpload(PublicUserDao pubDao) {
		
		this.dao = pubDao;
	}
	
	/**
	 * Pulls data from the given directory and
	 * adds the directory items to a String list
	 * 
	 * */
	public List<ItemDTO> showList() {
		
		myItems = new ArrayList<>();
		
		if(category != null && !category.contains("Please Select")) {
			
			File folder = new File("C:\\Users\\Deeder\\Home-Workspace\\FileManager\\Storage\\" + category + "\\");
			
			int count = 1;
			
			for(File s : folder.listFiles()) {
				
				item = new ItemDTO();
				
				item.setId(count);
				item.setImage(s.getPath());
				item.setName(s.getName());
				
				myItems.add(item);
				
				count++;
			}
		}
		
		return myItems;
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
				
				//Create a switch case for 'Category'
				
				switch(category) {
				
				case "Pictures":
					
					//code here
					PicturesDTO pic = new PicturesDTO();
					byte[] picArray = upFile.getContent();
					
					pic.setType("Public");
					pic.setByteData(pic.toString());
					pic.setName(upFile.getFileName());
					pic.setInfo("");
					
					dao.insertPublicPictures(pic);
					
					break;
					
				case "Documents":
					
					//code here
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
				
				String realPath = "C:\\Users\\Deeder\\Home-Workspace\\FileManager\\Storage\\" + category + "\\";
				
				byte[] fContents = upFile.getContent();
				String fName = realPath + upFile.getFileName();
				FileOutputStream out = new FileOutputStream(fName);
				
				out.write(fContents);
				out.close();
				
			}catch(Exception e) {
				
				e.printStackTrace();
			}
		}
		
		return "main_menu.xhtml?faces-redirect=true";
	}

	public List<ItemDTO> getMyItems() {
		return myItems;
	}

	public void setMyItems(List<ItemDTO> myItems) {
		this.myItems = myItems;
	}

	public ItemDTO getItem() {
		return item;
	}

	public void setItem(ItemDTO item) {
		this.item = item;
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
}
