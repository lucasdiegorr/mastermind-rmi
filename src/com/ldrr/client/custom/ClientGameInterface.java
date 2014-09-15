package com.ldrr.client.custom;
/**
 *https://github.com/lucasdiegorr/mastermind-rmi
 * branch: master
 * */
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ldrr.server.generic.Commands;

public interface ClientGameInterface extends Remote{

	/**
	 * The server sends a sequence
	 * Envia uma sequencia ao servidor
	 * 
	 * @param sequence
	 */
	public void sendSequence(int[] sequence) throws RemoteException;

	public void searchEnemy(String address, String enemy) throws RemoteException;

	public void receivedAlert(Commands alert) throws RemoteException;

	public void receivedSequenceGame(int[] sequence) throws RemoteException;
	
	public void sendAlert(Commands alert) throws RemoteException;
	
}