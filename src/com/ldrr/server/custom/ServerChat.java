package com.ldrr.server.custom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.ldrr.client.custom.ClientChatInterface;
import com.ldrr.server.generic.MessageChat;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class ServerChat extends UnicastRemoteObject implements Runnable, ServerChatInterface {

	private static final long serialVersionUID = 1L;
	private List<ClientChatInterface> listClient;

	public ServerChat() throws RemoteException{
		super();
		setListClient(new ArrayList<ClientChatInterface>());
	}

//	public ServerChat(int port) {
//		super(port);
//	}

	/* (non-Javadoc)
	 * @see com.ldrr.server.custom.ServerChatInterface#sendMessageChat(java.lang.String, com.ldrr.server.custom.ThreadServerChat)
	 */
	@Override
	public void sendMessageChat(String message) {
		sendMessage(message);
	}


	@Override
	public void run() {
		while (true);
	}

	/* (non-Javadoc)
	 * @see com.ldrr.server.custom.ServerChatInterface#sendAlertDisconnect(com.ldrr.server.custom.ThreadServerChat)
	 */
	@Override
	public void sendAlertDisconnect(ClientChatInterface client) {
		MessageChat message = new MessageChat(null, 8, "O outro usuário saiu do chat\n");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			new ObjectOutputStream(baos).writeObject(message);
			sendAlert(Base64.encodeBase64String(baos.toByteArray()));
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
	public void sendMessage(String messageToAll) {
		System.out.println("Fui chamado.");
		for (ClientChatInterface othreClient : getListClient()) {
				try {
					othreClient.receivedMessageChat(messageToAll);
				} catch (RemoteException e) {
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
		for (ClientChatInterface clients : getListClient()) {
			try {
				clients.receivedMessageChat(ALERT);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the listClient
	 */
	public List<ClientChatInterface> getListClient() {
		return listClient;
	}

	/**
	 * @param listClient the listClient to set
	 */
	public void setListClient(List<ClientChatInterface> listClient) {
		this.listClient = listClient;
	}

	@Override
	public void registry(ClientChatInterface client) throws RemoteException {
		getListClient().add(client);
	}
}
