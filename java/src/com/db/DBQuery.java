package com.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DBQuery {
	
	private DBConnect conn = null;
	private Statement stmt = null;
	private ResultSet rset = null;
	
	public DBQuery(DBConnect connect){
		conn = connect;
	}

	private void openStmt() throws SQLException{
		if( null == stmt ){
			stmt = conn.getConnect().createStatement();
		}
	}
	
	private void closeStmt() throws SQLException{
		if( null == stmt )
			return;
		stmt.close();
		stmt = null;
	}

	public boolean execute(String sql) throws SQLException{
		openStmt();
		boolean ret = stmt.execute(sql);
		closeStmt();
		return ret;
	}
	
	/*
	 * @1:columnName
	 * @2:columnSize
	 * 
	 * */
	public Map<String, Integer> getMetaData() {
		Map<String, Integer> ret = new HashMap<String, Integer>();
		try {
			if( null == rset && rset.isClosed() )
				return ret;
			ResultSetMetaData metaData = rset.getMetaData();
			for(int i=1; i <= metaData.getColumnCount(); i++ ){
				ret.put(metaData.getColumnName(i), metaData.getColumnDisplaySize(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public Vector<Vector<Object>> getResultSet(){
		Vector<Vector<Object>> ret = new Vector<Vector<Object>>();
		try {
			if( null == rset && rset.isClosed() )
				return ret;
			
			Map<String, Integer> mmeta = getMetaData();
			Vector<Object> vColName = new Vector<>();
			Vector<Object> vColSize = new Vector<>();
			
			int nColumn = mmeta.size();
			for(String key : mmeta.keySet() ){
				vColName.add(key);
				vColSize.add(mmeta.get(key));
			}
			
			ret.add(vColName);
			ret.add(vColSize);
			
			while( rset.next() ){
				Vector<Object> vrow = new Vector<>();
				for( int i=1; i <= nColumn; ++i ){
					vrow.add(rset.getString(i).trim());
				}
				ret.add(vrow);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if( rset != null ){
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rset = null;
				}	
			}
		}
		
		return ret;
	}
	
	public Vector<Vector<Object>> query(String sql){
		
		Vector<Vector<Object>> ret = null;
		try{
			
			openStmt();
			rset = stmt.executeQuery(sql);
			ret = getResultSet();
			closeStmt();
			
		}catch ( SQLException e ) {
			e.printStackTrace();
		}
		
		return ret;
	}
	

	public static void main(String[] args) {
		
	}

}
