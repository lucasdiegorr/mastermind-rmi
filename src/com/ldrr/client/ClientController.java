package com.ldrr.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.ldrr.client.custom.ClientChat;
import com.ldrr.client.custom.ClientGame;
import com.ldrr.graphic.GameFrame;
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
public class ClientController {

	private ClientChat clientChat;
	private ClientGame clientGame;
	private GameFrame gameFrame;

	public ClientController(GameFrame chatFrame) {
		this.gameFrame = chatFrame;
	}

	public void initChat() {
		try {
			LocateRegistry.createRegistry(5000);
			this.clientChat = new ClientChat(this);
			Naming.rebind("rmi://127.0.0.1:5000/Master", this.clientChat);
			System.out.println("Registrado o master");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		new Thread(clientChat).start();
	}

	public void initChat(String address, int port) {
		try {
			LocateRegistry.createRegistry(5500);
			this.clientChat = new ClientChat(address, port, this);
			Naming.rebind("rmi://"+address+":5500/Challenger", this.clientChat);
			System.out.println("Registrado o challeger");
			this.clientChat.getOtherClient().searchEnemy(address);
			new Thread(clientChat).start();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initChat(String address, int port, String nickName) {
		try {
			LocateRegistry.createRegistry(5500);
			this.clientChat = new ClientChat(address, port, this);
			this.clientChat.setClientName(nickName);
			Naming.rebind("rmi://"+address+":5500/Challenger", this.clientChat);
			System.out.println("Registrado o challeger");
			new Thread(clientChat).start();
			this.clientChat.getOtherClient().searchEnemy(address);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void initGame() {
		this.clientGame = new ClientGame(this);
		new Thread(clientGame).start();
	}

	public void initGame(String address, int port) {
		this.clientGame = new ClientGame(address, port, this);
		new Thread(clientGame).start();
	}

	public void sendMessageChat(String message) {
		this.clientChat.sendMessageChat(message);
	}

	public void sendSequenceColors(int[] sequence) {
		this.clientGame.sendSequence(sequence);
	}

	public void receivedMessageChat(String message) {
		this.gameFrame.setMessageToTextAreaChat(message);
	}

	public void receivedSequenceGame(int[] colorResponse) {
		this.gameFrame.setSequenceToGameView(colorResponse);
	}

	public void setNickName(String nickName) {
		this.clientChat.setClientName(nickName);
	}

	public String getNickName() {
		return this.clientChat.getClientName();
	}

	public void disconnectFromChat() {
		//		try {
		//			this.clientChat.getReader().close();
		//			this.clientChat.getWriter().close();
		//			this.clientChat.getSocket().close();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
	}

	public void disconnectFromGame() {
		try {
			this.clientGame.sendMessage(Commands.DISCONNECT.toString());
			this.clientGame.getReader().close();
			this.clientGame.getWriter().close();
			this.clientGame.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Alert(Commands command) {
		this.gameFrame.Alert(command);
	}

	public void resetGame() {
		this.clientGame.sendMessage(Commands.RESET_GAME.toString());
	}

	public void setEmoticon(int emoticon) {
		this.clientChat.setEmoticon(emoticon);
	}

	public void setEnemyNick(String nickName) {
		this.gameFrame.setEnemyNick(nickName);
	}

	public void setEnemyEmoticon(int avatar) {
		this.gameFrame.setEnemyAvatar(avatar);
	}

	public String addressChat() {
		return this.clientChat.getAddress();
	}

	public String addressGame() {
		return this.clientChat.getAddress();
	}

	public int portGame() {
		return this.clientGame.getPort();
	}

	public boolean isConnect() {
		return ((this.clientChat != null) && (this.clientGame != null))?true:false;
	}
}
