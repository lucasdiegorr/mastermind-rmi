package com.ldrr.server.custom;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.ldrr.client.custom.ClientChatInterface;

public interface ServerChatInterface extends Remote{

	public void registry(ClientChatInterface client) throws RemoteException;
	/**
	 * Send a message to all registered except the server to the client that sent
	 * the message to server customers
	 * Envia uma mensagem a todos os clientes cadastrados no servidor exceto ao 
	 * cliente que enviou a mensagem ao servidor
	 * @param message - message to be sent - mensagem a ser enviada
	 * @param thread - Thread responsible for the clientChat that sent the message 
	 * - Thread responsavel pelo clienteChat que enviou a mensagem
	 */
	public void sendMessageChat(String message) throws RemoteException;

	/**
	 * Send a message to disconnect a client from the server Chat
	 * Envia uma mensagem informando a desconexão de um client do servidor de Chat
	 * @param threadServerChat - Thread responsible for the clientChat
	 */
	public void sendAlertDisconnect(ClientChatInterface client) throws RemoteException;

}