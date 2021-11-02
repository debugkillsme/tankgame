package com.nju;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Blast extends GameObject{
	public Blast(String img, int x, int y, GamePanel gamePanel) {
		super(img, x, y, gamePanel);
	}
	static Image[] imgs=new Image[3];
	int explodeCount=0;
	static {
		for(int i=0;i<3;i++) {
			imgs[i]=Toolkit.getDefaultToolkit().getImage("images/boom/b"+(i+1)+".png");
		}
	}
	@Override
	public void paintSelf(Graphics g) {
		if(explodeCount<3) {
			g.drawImage(imgs[explodeCount], x, y, 50, 50,null);
			explodeCount++;
		}
	}
	@Override
	public Rectangle getRec() {
		return null;
	}
	

}
