package com.ldrr.client.custom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.ldrr.client.ClientController;
import com.ldrr.client.generic.Client;
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
public class ClientGame extends Client implements Runnable {

	// CONSTRUCTORS
	public ClientGame(ClientController controller) {
		super("127.0.0.1", 6000, controller);
	}

	public ClientGame(String address, int port, ClientController controller) {
		super(address, port, controller);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String messageFromServer;
		try {
			while (((messageFromServer = getReader().readUTF()) != null)) {
				if (isAlert(messageFromServer)) {
				} else {
					this.getController().receivedSequenceGame(convertToIntArray(messageFromServer));
				}
			}
		} catch (IOException e) {
			disconnect();
			this.getController().Alert(Commands.DISCONNECT);
			e.printStackTrace();
		}
	}


	/**
	 * The server sends a sequence
	 * Envia uma sequencia ao servidor
	 * 
	 * @param sequence
	 */
	public void sendSequence(int[] sequence) {
		String stringSequence = convertToString(sequence);
		sendMessage(stringSequence);
	}

	/**
	 * Checks whether the sending of a message by the server occurred. If so alerts the controller, otherwise does nothing
	 * Verifica se ocorreu o envio de uma mensagem por parte do servidor. Caso positivo alerta o controller, caso contrario nao faz nada
	 * 
	 * @param messageFromServer
	 * @return
	 */
	private boolean isAlert(String messageFromServer) {

		if (Commands.DISCONNECT.toString().equals(messageFromServer)) {
			this.getController().Alert(Commands.DISCONNECT);
			return true;
		} else if (Commands.RESET_GAME.toString().equals(messageFromServer)) {
			this.getController().Alert(Commands.RESET_GAME);
			return true;
		}else if (Commands.INIT_GAME.toString().equals(messageFromServer)) {
			this.getController().Alert(Commands.INIT_GAME);
			return true;
		}

		return false;
	}

	/**
	 * Converts an int array into a string through the Base64 algorithm
	 * Converte um array de int em uma string atraves do algoritmo Base64
	 * @param sequence 
	 * @return sequence - int array containing the Base64 algorithm converted by Base64 
	 * - contendo o array de int convertido pelo algoritmo Base64
	 */
	private String convertToString(int[] sequence) {
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		try {
			new ObjectOutputStream(byteArrayOutput).writeObject(sequence);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(byteArrayOutput.toByteArray());
	}

	/**
	 * Converts a string to an int array
	 * Converte uma string em um array de int
	 * @param messageFromServer - a string containing an int array to be converted 
	 * - uma string contendo um array de int a ser convertida
	 * @return int[] - an array containing the values ​​of the string passed as parameter 
	 * - um array contendo os valores da string passada como parametro
 	 */
	private int[] convertToIntArray(String messageFromServer) {
		ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(Base64.decodeBase64(messageFromServer));
		int[] sequence = null;
		try {
			sequence = (int[]) new ObjectInputStream(byteArrayInput).readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return sequence;
	}

}
