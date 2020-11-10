package com.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;



public class JDBCUtil {

	public static Connection getConnection() throws Exception {
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
	
	public static void closeRecource(Connection conn,Statement statement ) {
		try {
			if (statement != null) 
				statement.close();
			
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeRecource(Connection conn,Statement statement,ResultSet rs ) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (statement != null) 
				statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if (rs != null) 
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
