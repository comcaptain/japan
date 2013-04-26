package com.base.word.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static Connection conn;
	public static Connection getConnection() {
		if (conn == null) {
			String driver = "org.apache.derby.jdbc.EmbeddedDriver";
			String dbName="derbydb/jpdb";
			String connectionURL = "jdbc:derby:" + dbName;
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(connectionURL);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return conn;
	}
	public static String getDerbyArray(String[] strList) {
		if (strList == null || strList.length == 0) return null;
		StringBuffer strBuffer = new StringBuffer("(");
		for (String str: strList) {
			strBuffer.append("'" + str + "',");
		}
		String result = strBuffer.toString();
		return result.substring(0, result.length() - 1) + ")";
	}
}
