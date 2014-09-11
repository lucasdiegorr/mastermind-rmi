package com.ldrr.client.custom;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.ldrr.client.ClientController;
import com.ldrr.server.generic.Commands;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class ClientGame extends UnicastRemoteObject implements Runnable, ClientGameInterface {

	private static final long serialVersionUID = 1L;
	private ClientController controller;
	private ClientGameInterface otherClient;

	// CONSTRUCTORS
	public ClientGame(ClientController controller) throws RemoteException {
		this.setController(controller);
	}

	public ClientGame(String address, ClientController controller) throws RemoteException {
		try {
			this.setController(controller);
			setOtherClient((ClientGameInterface)Naming.lookup("rmi://"+address+":5000/MasterGame"));
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void searchEnemy(String address, String enemy) throws RemoteException {
		try {
			setOtherClient((ClientGameInterface)Naming.lookup("rmi://127.0.0.1:5000/"+enemy));
			getOtherClient().sendAlert(Commands.INIT_GAME);
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
	 * @see com.ldrr.client.custom.ClientGameInterface#sendSequence(int[])
	 */
	@Override
	public void sendSequence(int[] sequence) {
		try {
			getOtherClient().receivedSequenceGame(sequence);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void receivedSequenceGame(int[] sequence) throws RemoteException {
		getController().receivedSequenceGame(sequence);
	}
	
	/**
	 * Checks whether the sending of a message by the server occurred. If so alerts the controller, otherwise does nothing
	 * Verifica se ocorreu o envio de uma mensagem por parte do servidor. Caso positivo alerta o controller, caso contrario nao faz nada
	 * 
	 * @param messageFromServer
	 * @return
	 */
	public void receivedAlert(Commands alert) {
		this.getController().alert(alert);
	}

	@Override
	public void sendAlert(Commands alert) {
		try {
			if (getOtherClient() != null) {
				getOtherClient().receivedAlert(alert);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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

	/**
	 * @return the otherClient
	 */
	public ClientGameInterface getOtherClient() {
		return otherClient;
	}

	/**
	 * @param otherClient the otherClient to set
	 */
	public void setOtherClient(ClientGameInterface otherClient) {
		this.otherClient = otherClient;
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

}
