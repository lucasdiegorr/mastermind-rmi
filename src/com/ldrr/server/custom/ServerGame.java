package com.ldrr.server.custom;

import java.net.Socket;

import com.ldrr.server.generic.Commands;
import com.ldrr.server.generic.Server;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class ServerGame extends Server implements Runnable {

	public ServerGame() {
		super(6000);
	}

	public ServerGame(int port) {
		super(port);
	}

	/**
	 * Send a message to all registered except the server to the client that sent
	 * the message to server customers
	 * Envia uma mensagem a todos os clientes cadastrados no servidor exceto ao 
	 * cliente que enviou a mensagem ao servidor
	 * @param message - message to be sent - mensagem a ser enviada
	 * @param thread - Thread responsible for the clientGame that sent the message 
	 * - Thread responsavel pelo clientGame que enviou a mensagem
	 */
	public void sendMessageGame(String fromClient, ThreadServerGame thread) {
		sendMessage(fromClient, thread.getClient());
	}

	@Override
	public void run() {
		while (true) {
			while (this.getListClient().size() < 2) {
				Socket client;
				try {
					client = this.getServer().accept();
					getListClient().add(client);
					new Thread(new ThreadServerGame(client, this)).start();
					if (this.getListClient().size() == 2) {
						sendAlert(Commands.INIT_GAME.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
