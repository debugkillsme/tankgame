package com.nju;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bullet extends GameObject{
	
	public Bullet(String img, int x, int y, GamePanel gamePanel,Direction direction) {
		super(img, x, y, gamePanel);
		this.direction=direction;
		
	}

	int width=10;
	int height=10;
	int speed=7;
	Direction direction;
	
	public void leftward() {
		x-=speed;
	}
	public void rightward() {
		x+=speed;
	}
	
	public void upward() {
		y-=speed;
	}
	public void downward() {
		y+=speed;
	}
	
	public void go() {
		switch(direction) {
		case LEFT:
			leftward();
			break;
		case RIGHT:
			rightward();
			break;
		case UP:
			upward();
			break;
		case DOWN:
			downward();				
		}
		this.hitWall();
		this.moveToBorder();
		this.hitBase();
	}
	public void hitBot() {
		ArrayList<Bot> bots=this.gamePanel.botList;
		//��ײ�������з�̹��
		for(Bot bot:bots) {
			if(this.getRec().intersects(bot.getRec())) {
				//��ӱ�ը������Ч
				this.gamePanel.blastList.add(new Blast("",bot.x-34,bot.y-14,this.gamePanel));
				this.gamePanel.botList.remove(bot);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
	}
	public void hitBase() {
		ArrayList<Base> baseList=this.gamePanel.baseList;
		//��ײ�������з�̹��
		for(Base base:baseList) {
			if(this.getRec().intersects(base.getRec())) {
				this.gamePanel.baseList.remove(base);
				this.gamePanel.removeList.add(this);
				break;
			}
		}
	}
	public void hitWall() {
		//Χǽ�б�
		ArrayList<Wall> walls=this.gamePanel.wallList;
		//�����б�
		for(Wall wall:walls) {
			//��ÿһ��Χǽ������ײ���
			if(this.getRec().intersects(wall.getRec())){
				//ɾ��Χǽ���ӵ�
				this.gamePanel.wallList.remove(wall);
				this.gamePanel.removeList.add(this);
				//ֹͣѭ��
				break;
			}
		}
	}
	//�������ӵ����ڴ������
	public void moveToBorder() {
		if(x<0||x+width>this.gamePanel.getWidth()) {
			this.gamePanel.removeList.add(this);
		}
		if(y<0||y+height>this.gamePanel.getHeight()) {
			this.gamePanel.removeList.add(this);
		}
	}
	@Override
	public void paintSelf(Graphics g) {
		g.drawImage(img,x,y,10,10,null);
		this.go();
		this.hitBot();
	}
	@Override
	public Rectangle getRec() {
		return new Rectangle(x,y,width,height);
	}

}
