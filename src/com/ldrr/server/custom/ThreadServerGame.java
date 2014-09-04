package com.ldrr.server.custom;

import java.io.IOException;
import java.net.Socket;

import com.ldrr.server.generic.Commands;
import com.ldrr.server.generic.ThreadServer;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class ThreadServerGame extends ThreadServer implements Runnable {

	private ServerGame server;

	public ThreadServerGame(Socket client, ServerGame server) {
		super(client);
		this.setServer(server); 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String fromClient = null;
		try {
			while (!this.getClient().isClosed()	&& ((fromClient = getReader().readUTF()) != null)) {
				this.getServer().sendMessageGame(fromClient, this);
			}
		} catch (IOException e) {
			this.getServer().disconnect(this.getClient());
			this.getServer().sendAlert(Commands.DISCONNECT.toString());
		}
	}

	
	/**
	 * @return the server
	 */
	private ServerGame getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	private void setServer(ServerGame server) {
		this.server = server;
	}
}
