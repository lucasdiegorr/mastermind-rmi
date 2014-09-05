package com.ldrr.client.custom;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ClientChatInterface extends Remote{

	/**
	 * Treatment makes the messages received by the server
	 * Faz o tratamento as mensagens recebidas pelo servidor
	 * @param messageFromServer - message received by the server 
	 * - mensagem recebida pelo servidor
	 */
	public void receivedMessageChat(String messageFromServer) throws RemoteException;

	/**
	 * Send a message to the server
	 * Envia uma mensagem ao servidor
	 * @param stringMessage - message to be sent - mensagem a ser enviada
	 */
	public void sendMessageChat(String stringMessage) throws RemoteException;

	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) throws RemoteException;

	/**
	 * @return the clientName
	 */
	public String getClientName() throws RemoteException;

	/**
	 * @param emoticon the emoticon to set
	 */
	public void setEmoticon(int emoticon) throws RemoteException;

	/**
	 * @return the emoticon
	 */
	public int getEmoticon() throws RemoteException;
		
	public void searchEnemy(String address) throws RemoteException;
}