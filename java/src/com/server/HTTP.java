package com.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HTTP {
	private Socket socket = null;
	private Map<String, String> m_dataSet = new HashMap<String, String>();
	
	public HTTP(Socket sock) throws Exception {
		socket = sock;
		parser();
	}
	
	public void Show(){
		System.out.println(m_dataSet);
	}
	
	public void parser() throws Exception{
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		line = br.readLine();
		//HEAD 1
		String [] strs = line.split(" ");
		m_dataSet.put("Mode",strs[0]);
		m_dataSet.put("Source", strs[1]);
		m_dataSet.put("Protocol", strs[2]);
		//HEAD 2
		line = br.readLine();
		while( "" != line ){
			strs = line.split(": ");
			if( strs.length > 1 ){
				m_dataSet.put(strs[0], strs[1]);
			}
			line = br.readLine();
		}
		
		//BODY
		line = br.readLine();
		m_dataSet.put("Data", line);
		br.close();
		is.close();
	}
	
	public String get( String key ){
		return m_dataSet.get(key);
	}
	
}
