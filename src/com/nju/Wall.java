package com.nju;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends GameObject {
	int length=60;

	public Wall(String img, int x, int y, GamePanel gamePanel) {
		super(img, x, y, gamePanel);
	}

	@Override
	public void paintSelf(Graphics g) {
		g.drawImage(img, x, y,length,length, gamePanel);
	}

	@Override
	public Rectangle getRec() {
		return new Rectangle(x,y,length,length);
	}

}
