package com.filemanager.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("miscDTO")
@SessionScoped
public class MiscDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String type;
	private String byteData;
	private String name;
	private String info;
	private int userId;
	private int gID;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getByteData() {
		return byteData;
	}

	public void setByteData(String byteData) {
		this.byteData = byteData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getgID() {
		return gID;
	}

	public void setgID(int gID) {
		this.gID = gID;
	}
}