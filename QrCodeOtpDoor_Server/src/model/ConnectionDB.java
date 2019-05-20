package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
	public Connection getConnection() throws Exception{
		Connection conn=null;
		String jdbcUrl = "jdbc:mysql://localhost:3306/qrdoor";
		String dbId = "root";
		String dbPass = "";
		Class.forName("com.mysql.jdbc.Driver");	
		conn = DriverManager.getConnection(jdbcUrl,dbId,dbPass);
	
		return conn;
		
	}
}
