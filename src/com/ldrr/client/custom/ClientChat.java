package com.ldrr.client.custom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.codec.binary.Base64;

import com.ldrr.client.ClientController;
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
public class ClientChat extends UnicastRemoteObject implements Runnable, ClientChatInterface {

	private static final long serialVersionUID = 1L;
	private String clientName = null;
	private int emoticon;
	private ClientController controller;
	private ClientChatInterface otherClient;
	// CONSTRUCTORS
	public ClientChat(ClientController controller) throws RemoteException{
		this.setController(controller);
		this.setEmoticon(0);
	}

	public ClientChat(String address, ClientController controller, String nickname) throws RemoteException{
		try {
			this.setClientName(nickname);
			this.setController(controller);
			this.setEmoticon(0);
			setOtherClient((ClientChatInterface)Naming.lookup("rmi://"+address+":5000/MasterChat"));
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public ClientChat(String address, ClientController controller) throws RemoteException{
		try {
			this.setController(controller);
			this.setEmoticon(0);
			setOtherClient((ClientChatInterface)Naming.lookup("rmi://"+address+":5000/MasterChat"));
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void searchEnemy(String address, String enemy) {
		try {
			setOtherClient((ClientChatInterface)Naming.lookup("rmi://"+address+":5000/"+enemy));
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(true);
	}

	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#receivedMessageChat(java.lang.String)
	 */
	@Override
	public void receivedMessageChat(String messageFromServer) {
		ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(Base64.decodeBase64(messageFromServer));
		MessageChat message = null; 

		try {
			message = (MessageChat) new ObjectInputStream(byteArrayInput).readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		if (message.getNickName() == null) {
			this.getController().setEnemyNick("An�nimo");
			this.getController().receivedMessageChat(message.getMessage());
		}else {
			this.getController().setEnemyNick(message.getNickName());				
			this.getController().receivedMessageChat(message.getNickName() + ": " + message.getMessage());
		}

		this.getController().setEnemyEmoticon(message.getEmoticon());
	}

	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#sendMessageChat(java.lang.String)
	 */
	@Override
	public void sendMessageChat(String stringMessage) {
		MessageChat message = new MessageChat(this.getClientName(), this.getEmoticon(), stringMessage);
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();

		try {
			new ObjectOutputStream(byteArrayOutput).writeObject(message);
			getOtherClient().receivedMessageChat(Base64.encodeBase64String(byteArrayOutput.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendAlertDisconnect() {
		MessageChat message = new MessageChat("An�nimo", 8, "O outro usu�rio saiu.");
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();

		try {
			new ObjectOutputStream(byteArrayOutput).writeObject(message);
			getOtherClient().receivedMessageChat(Base64.encodeBase64String(byteArrayOutput.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#setClientName(java.lang.String)
	 */
	@Override
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#getClientName()
	 */
	@Override
	public String getClientName() {
		return clientName;
	}

	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#setEmoticon(int)
	 */
	@Override
	public void setEmoticon(int emoticon) {
		this.emoticon = emoticon;
	}

	/* (non-Javadoc)
	 * @see com.ldrr.client.custom.ClientChatInterface#getEmoticon()
	 */
	@Override
	public int getEmoticon() {
		return emoticon;
	}

	/**
	 * @return the controller
	 */
	public ClientController getController() {
		return controller;
	}

	/**
	 * @param controller the controller to set
	 */
	public void setController(ClientController controller) {
		this.controller = controller;
	}

	public String getAddress() {
		String address = null;
		try {
			address = ""+ InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return address;
	}

	/**
	 * @return the otherClient
	 */
	public ClientChatInterface getOtherClient() {
		return otherClient;
	}

	/**
	 * @param otherClient the otherClient to set
	 */
	public void setOtherClient(ClientChatInterface otherClient) {
		this.otherClient = otherClient;
	}
}
