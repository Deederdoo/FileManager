package com.filemanager.filefunctions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.filemanager.model.ItemDTO;

@Named("fileUpload")
@SessionScoped
public class FileUpload implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected List<String> categories;
	protected String category;
	
	private List<ItemDTO> myItems;
	private ItemDTO item;
	
	/*
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
	
	/*
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
	
	/*
	 * Instantiates the Categories List
	 * 
	 * */
	public List<String> generateCategories(){
		
		categories = new ArrayList<>();
		categories.add("Please Select");
		categories.add("Documents");
		categories.add("Pictures");
		categories.add("Videos");
		categories.add("Misc");
		
		return categories;
	}
	
	/*
	 * Takes the uploaded files and sends them to their
	 * respective directory
	 * 
	 * */
	public String uploadMultipleFiles(FileUploadEvent event) {
		
		UploadedFile upFile = event.getFile();
		
		if(upFile != null) {
			
			try {
				
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
