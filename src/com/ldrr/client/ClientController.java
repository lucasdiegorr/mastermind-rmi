package com.ldrr.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import com.ldrr.client.custom.ClientChat;
import com.ldrr.client.custom.ClientGame;
import com.ldrr.graphic.GameFrame;
import com.ldrr.server.generic.Commands;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-rmi
 * branch: master
 */

/**
 * @author Lucas Diego
 * 
 */
public class ClientController {

	private ClientChat clientChat;
	private ClientGame clientGame;
	private GameFrame gameFrame;
	private boolean connectedChat;

	public ClientController(GameFrame chatFrame) {
		this.gameFrame = chatFrame;
	}

	public void initChat() {
		try {
			this.clientChat = new ClientChat(this);
			Naming.rebind("rmi://127.0.0.1:5000/MasterChat", this.clientChat);
			new Thread(clientChat).start();
			this.setConnectedChat(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initChat(String address) {
		try {
			this.clientChat = new ClientChat(address, this);
			Naming.rebind("rmi://"+address+":5000/ChallengerChat", this.clientChat);
			this.clientChat.getOtherClient().searchEnemy(address, "ChallengerChat");
			new Thread(clientChat).start();
			this.setConnectedChat(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initGame() {
		try {
			this.clientGame = new ClientGame(this);
			Naming.rebind("rmi://127.0.0.1:5000/MasterGame", this.clientGame);
			new Thread(clientGame).start();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void initGame(String address) {
		try {
			this.clientGame = new ClientGame(address, this);
			Naming.rebind("rmi://"+address+":5000/ChallengerGame", this.clientGame);
			System.out.println("Registrado o challenger");
			this.clientGame.getOtherClient().searchEnemy(this.clientGame.getAddress(), "ChallengerGame");
			new Thread(clientGame).start();
			alert(Commands.INIT_GAME);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void sendMessageChat(String message) {
		this.clientChat.sendMessageChat(message);
	}

	public void sendSequenceColors(int[] sequence) {
		this.clientGame.sendSequence(sequence);
	}

	public void receivedMessageChat(String message) {
		if (isConnectedChat()) {
			this.gameFrame.setMessageToTextAreaChat(message);
		}
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
		this.clientChat.sendAlertDisconnect();
		this.setConnectedChat(false);
	}

	public void reconnectChat() {
		this.setConnectedChat(true);
	}

	public void disconnectFromGame() {
		this.clientGame.sendAlert(Commands.DISCONNECT);
	}

	public void alert(Commands command) {
		this.gameFrame.Alert(command);
	}

	public void resetGame() {
		this.clientGame.sendAlert(Commands.RESET_GAME);
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

	public boolean isConnect() {
		return ((this.clientChat.getOtherClient() != null) && (this.clientGame.getOtherClient() != null))?true:false;
	}

	/**
	 * @return the connectedChat
	 */
	public boolean isConnectedChat() {
		return connectedChat;
	}

	/**
	 * @param connectedChat the connectedChat to set
	 */
	public void setConnectedChat(boolean connectedChat) {
		this.connectedChat = connectedChat;
	}

}
