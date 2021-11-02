package com.nju;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class PlayerTwo extends Tank{
	boolean up=false;
	boolean left=false;
	boolean right=false;
	boolean down=false;

	public PlayerTwo(String img, int x, int y, GamePanel gamePanel, String upImg, String leftImg, String rightImg,
			String downImg) {
		super(img, x, y, gamePanel, upImg, leftImg, rightImg, downImg);
	}
	
	public void move() {
		if(left) {
			leftward();
		}
		else if(right) {
			rightward();
		}
		else if(up) {
			upward();
		}
		else if(down) {
			downward();
		}
	}
	@Override
	public void paintSelf(Graphics g) {
		g.drawImage(img, x, y,50,50,null);
		move();
	}
	@Override
	public Rectangle getRec() {
		return new Rectangle(x,y,width,height);
	}
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			left=true;
			break;
		case KeyEvent.VK_DOWN:
			down=true;
			break;
		case KeyEvent.VK_UP:
			up=true;
			break;
		case KeyEvent.VK_RIGHT:
			right=true;
			break;
		case KeyEvent.VK_K:
			attack();
		default:
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			left=false;
			break;
		case KeyEvent.VK_DOWN:
			down=false;
			break;
		case KeyEvent.VK_RIGHT:
			right=false;
			break;
		case KeyEvent.VK_UP:
			up=false;
			break;
		default:
			break;
		}
	}
	
}