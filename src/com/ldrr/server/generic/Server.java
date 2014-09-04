package com.ldrr.server.generic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class Server {

	private ServerSocket server;
	private List<Socket> listClient = new ArrayList<Socket>();

	public Server(int port) {
		requestConnect(port);
	}

	/**
	 * Creates a new ServerSocket last port as parameter
	 * Cria um novo Serversocket na porta passada como parâmetro
	 * 
	 * @param port - port on which the server will wait for communication 
	 * - porta em que o servidor irá esperar comunicação
	 */
	private void requestConnect(int port) {
		try {
			this.setServer(new ServerSocket(port));
		} catch (IOException e) {
			requestConnect(port++);
			e.printStackTrace();
		}
	}

	/**
	 * Removes the last socket as parameter from the list of clients and the server 
	 * closes the connection from this socket
	 * Retira o socket passado como parâmetro da lista de clientes do servidor e 
	 * fecha a conexão deste socket
	 * 
	 * @param client - client to be removed from the list - cliente a ser retirado da lista
	 */
	public void disconnect(Socket client) {

		getListClient().remove(client);
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send a message to all registered except the server to the client that sent
	 *  the message to server customers
	 * Envia uma mensagem a todos os clientes cadastrados no servidor exceto ao 
	 * cliente que enviou a mensagem ao servidor
	 * 
	 * @param messageToAll - message to be sent - mensagem a ser enviada
	 * @param client - client that sent the message - Cliente que enviou a mensagem
	 * */
	public void sendMessage(String messageToAll, Socket client) {
		DataOutputStream writer = null;
		for (Socket othreClient : getListClient()) {
			try {
				if (!othreClient.equals(client)) {
					writer = new DataOutputStream(othreClient.getOutputStream());
					writer.writeUTF(messageToAll);
					writer.flush();
				}
			} catch (IOException e) {
				System.out.println("Erro ao enviar mensagem.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends an alert to all registered customers at server
	 * Envia um alerta para todos os clientes cadastrados no servidor
	 * @param Alert
	 * */
	public void sendAlert(String ALERT) {
		DataOutputStream writer = null;
		for (Socket clients : getListClient()) {
			try {
				writer = new DataOutputStream(clients.getOutputStream());
				writer.writeUTF(ALERT);
				writer.flush();
			} catch (IOException e) {
				System.out.println("Erro ao enviar mensagem.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the server
	 */
	public ServerSocket getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(ServerSocket server) {
		this.server = server;
	}

	/**
	 * @return the listClient
	 */
	public List<Socket> getListClient() {
		return listClient;
	}

	/**
	 * @param listClient the listClient to set
	 */
	public void setListClient(List<Socket> listClient) {
		this.listClient = listClient;
	}
}
