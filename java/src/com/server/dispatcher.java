package com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class dispatcher extends Thread{

	private static int mWorkerCount = 0;
	private static Map<String, worker> mWorkers = new HashMap<String, worker>();
	private static boolean STOP = false;
	
	@Override
	public void run() {
		while( !STOP ){
			Socket socket = null;
			try {
				socket = server.m_netQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				if( null != socket ){
					try {
						socket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					socket = null;
				}
				continue;
			}
		}
	}

	public void Stop(){
		STOP = true;
	}
	
	public dispatcher(){
		
	}
	
	private void addWorker( Socket socket ) {
		String uuid = UUID.randomUUID()+"";
		worker worker = new worker();
		worker.start();
		mWorkers.put(uuid, worker);
	}
	
	private void rmvWorker( String uuid ) {
		if( mWorkers.containsKey(uuid) ){
			mWorkers.remove(uuid);
		}
	}
	
	public dispatcher( Socket socket ){
		
	}
	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid+"");
	}

}
