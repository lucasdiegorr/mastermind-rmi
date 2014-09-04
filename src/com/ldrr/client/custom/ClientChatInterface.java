package com.ldrr.client.custom;

public interface ClientChatInterface {

	/**
	 * Treatment makes the messages received by the server
	 * Faz o tratamento as mensagens recebidas pelo servidor
	 * @param messageFromServer - message received by the server 
	 * - mensagem recebida pelo servidor
	 */
	public void receivedMessageChat(String messageFromServer);

	/**
	 * Send a message to the server
	 * Envia uma mensagem ao servidor
	 * @param stringMessage - message to be sent - mensagem a ser enviada
	 */
	public void sendMessageChat(String stringMessage);

	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName);

	/**
	 * @return the clientName
	 */
	public String getClientName();

	/**
	 * @param emoticon the emoticon to set
	 */
	public void setEmoticon(int emoticon);

	/**
	 * @return the emoticon
	 */
	public int getEmoticon();

}