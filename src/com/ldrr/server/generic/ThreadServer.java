package com.ldrr.server.generic;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class ThreadServer {
	
	private Socket client;
	private DataInputStream reader;
	
	public ThreadServer(Socket socket) {
		this.setClient(socket);
		try {
			this.setReader(new DataInputStream(this.getClient().getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the client
	 */
	public Socket getClient() {
		return client;
	}
	/**
	 * @param client the client to set
	 */
	public void setClient(Socket client) {
		this.client = client;
	}
	/**
	 * @return the reader
	 */
	public DataInputStream getReader() {
		return reader;
	}
	/**
	 * @param reader the reader to set
	 */
	public void setReader(DataInputStream reader) {
		this.reader = reader;
	}

}
