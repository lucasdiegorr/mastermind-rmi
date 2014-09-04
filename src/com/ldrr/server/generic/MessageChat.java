package com.ldrr.server.generic;

import java.io.Serializable;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class MessageChat implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nickName;
	private int emoticon;
	private String message;

	public MessageChat(String nickName, int emoticon, String message) {
		super();
		this.nickName = nickName;
		this.emoticon = emoticon;
		this.message = message;
	}
	
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the emoticon
	 */
	public int getEmoticon() {
		return emoticon;
	}
	/**
	 * @param emoticon the emoticon to set
	 */
	public void setEmoticon(int emoticon) {
		this.emoticon = emoticon;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
