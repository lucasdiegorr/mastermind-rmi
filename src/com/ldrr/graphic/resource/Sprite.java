/**
 * 
 */
package com.ldrr.graphic.resource;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * All source code and required libraries are found at the following link:
 * https://github.com/lucasdiegorr/mastermind-socket 
 * branch: beta
 */

/**
 * @author Lucas Diego
 * 
 */
public class Sprite {

	private String[] colors = { "/red.png", "/green.png", "/orange.png", "/yellow.png","/blue.png", "/dark_blue.png","/black.png","/white.png", "/blanck_ball.png", "/Eval_Black.png", "/Eval_White.png", "/Eval_Blank.png", "/init_ball.png", "/secret_ball.png"};
	private String[] emoticons = {"/smile.png", "/large_smile.png", "/glass.png", "/sun_glass.png", "/clow.png", "/surprise.png", "/fun.png", "/rage.png", "/cancel.png"};
	
	public static int RED = 0;
	public static int GREEN = 1;
	public static int ORANGE = 2;
	public static int YELLOW = 3;
	public static int BLUE = 4;
	public static int DARK_BLUE = 5;
	public static int BLACK = 6;
	public static int WHITE = 7;
	public static int BLANCK_BALL = 8;
	public static int EVAL_BLACK = 9;
	public static int EVAL_WHITE = 10;
	public static int EVAL_BLANCK = 11;
	public static int INIT_BALL = 12;
	public static int SECRET_BALL = 13;
	
	private ArrayList<Icon> arrayColors = new ArrayList<Icon>();
	
	public Sprite() {
		for (int i = 0; i < this.colors.length; i++) {
			arrayColors.add(new ImageIcon(getClass().getResource(colors[i])));
		}
	}
	
	public Icon getColorByIndex(int index) {
		return arrayColors.get(index);
	}
	
	public Icon getButton() {
		return new ImageIcon(getClass().getResource("/accept.png"));
	}
	
	public int getIndexByColor(Icon color) {
		return this.arrayColors.indexOf(color);
	}
	
	public Icon getEmoticonByIndex(int index_emoticon) {
		return new ImageIcon(getClass().getResource(emoticons[index_emoticon]));
	}

	public Icon getAnimation() {
		return new ImageIcon(getClass().getResource("/gif-balls.gif"));
	}
	
	public Icon getImage() {
		return new ImageIcon(getClass().getResource("/gray.png"));
	}
}
