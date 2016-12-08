package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class server {
	
	private static int port = 8088;
	public static BlockingQueue<Socket> m_netQueue = 
			new ArrayBlockingQueue<Socket>(100);
	
	public void startNetServer() {
		try {
			ServerSocket server = new ServerSocket(port);
			while( true ) {
				Socket client = server.accept();
				try {
					m_netQueue.put(client);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		
	}

}
