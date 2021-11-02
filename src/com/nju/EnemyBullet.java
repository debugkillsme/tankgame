package com.nju;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{

	public EnemyBullet(String img, int x, int y, GamePanel gamePanel, Direction direction) {
		super(img, x, y, gamePanel, direction);
	}
	
	public void hitPlayer() {
		ArrayList<Tank> players=this.gamePanel.playerList;
		//碰撞检测消灭敌方坦克
		for(Tank player:players) {
			if(this.getRec().intersects(player.getRec())) {
				//添加爆炸特效
				this.gamePanel.blastList.add(new Blast("",player.x-34,player.y-14,this.gamePanel));
				this.gamePanel.playerList.remove(player);
				this.gamePanel.removeList.add(this);
				player.alive=false;
				break;
			}
		}
	}
	public void paintSelf(Graphics g) {
		g.drawImage(img, x, y,10,10,null);
		this.go();
		this.hitPlayer();
	}

	
	public Rectangle getRec() {
		return new Rectangle(x,y,width,height);
	}
}
