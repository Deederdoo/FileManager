package com.filemanager.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public enum ConnectionManager {

	INSTANCE;

	private DataSource ds;
	private Lock connectionLock = new ReentrantLock();

	ConnectionManager() {

		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/filemanagerdb");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		
		Connection conn = null;
		connectionLock.lock();
		try {
			conn = ds.getConnection();
		} finally {
			connectionLock.unlock();
		}

		return conn;
	}
}
