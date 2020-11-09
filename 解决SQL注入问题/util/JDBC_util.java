package com.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;



public class JDBC_util {

	public Connection connectionMysql() throws Exception {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("db.properties");
		
		Properties properties = new Properties();
		properties.load(is);
		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String classDriver = properties.getProperty("classDriver");
		
		Class.forName(classDriver);
		Connection conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	public void closeRecource(Statement statement, Connection conn) {
		try {
			if (statement != null) 
				statement.close();
			
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
