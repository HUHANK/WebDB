package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

public class DBConnect {

	private final static String sForName=
			"com.ibm.db2.jcc.DB2Driver";
	
	private String url = "";
	private Connection conn = null;
	
	private String user = "";
	private String pwd = "";
	
	public DBConnect(String host, int port, String db, 
			String user, String pwd ){
		url="jdbc:db2://"+host+":"+port+"/"+db;
		this.user = user;
		this.pwd = pwd;
	}
	
	public DBConnect( String url, String user, String pwd ){
		this.url = url;
		this.pwd = pwd;
		this.user = user;
	}
	
	public boolean connect(){
		try {
			Class.forName(sForName).newInstance();
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	public void close() throws SQLException {
		conn.commit();
		conn.close();
		conn = null;
	}
	
	public void setSchema(String schema) throws SQLException{
		conn.setSchema(schema);
	}
	
	public Connection getConnect(){
		return conn;
	}
	
	public static void main(String[] args) {
		//test
		DBConnect conn = new DBConnect(
				"jdbc:db2://10.10.12.192:50000/KSDBS:currentSchema=KS;", "back", "back");
		
		System.out.println(conn.connect());
		
		DBQuery query = new DBQuery(conn);
		String sql = "SELECT TABLE_NAME FROM TRANS_DATA_CONFIG";
		Vector<Vector<Object>> ret = query.query(sql);
		
		System.out.println(ret);
	}

}
