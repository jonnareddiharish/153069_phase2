package com.cg.mypaymentapp.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButil {
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection con = null;

		Class.forName("oracle.jdbc.driver.OracleDriver");
		//Connection con = new Connection();  cannot write like this
		 con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
		//System.out.println(con.getMetaData().getDatabaseProductName());
	return con;
	}
}
