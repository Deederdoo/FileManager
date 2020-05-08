package com.filemanager.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("publicUploadController")
@SessionScoped
public class public_upload_controller implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
