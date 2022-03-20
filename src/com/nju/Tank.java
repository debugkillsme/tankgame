package com.nju;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
public abstract class Tank extends GameObject {
	//�ߴ�
	public int width=50;
	public int height=50;
	public int speed=3;
	public Direction direction=Direction.UP;
	
	//�����ĸ������̹��ͼƬ
	public String upImg;
	public String leftImg;
	public String rightImg;
	public String downImg;
	//�������
	public boolean alive=false;
	//������ȴ״̬
	public boolean attackCoolDown=true;
	//������ȴʱ����
	private int attackCoolDownTime=1000;
	public Tank(String img,int x,int y,GamePanel gamePanel,String upImg, String leftImg, String rightImg, String downImg) {
		super(img,x,y,gamePanel);
		this.upImg = upImg;
		this.leftImg = leftImg;
		this.rightImg = rightImg;
		this.downImg = downImg;
	}
	
	public void leftward() {
		setImg(leftImg);
		direction=Direction.LEFT;
		if(!hitWall(x-speed,y)&&!moveToBorder(x-speed,y)) {
			this.x-=speed;
		}
	}
	public void rightward() {
		setImg(rightImg);
		direction=Direction.RIGHT;
		if(!hitWall(x+speed,y)&&!moveToBorder(x+speed,y)) {
			this.x+=speed;
		}
	}
	public void upward() {
		setImg(upImg);
		direction=Direction.UP;
		if(!hitWall(x,y-speed)&&!moveToBorder(x,y-speed)){
			this.y-=speed;
		}
	}
	public void downward() {
		setImg(downImg);
		direction=Direction.DOWN;
		if(!hitWall(x,y+speed)&&!moveToBorder(x,y+speed)){
			this.y+=speed;
		}
	}
	public void attack() {
		//�����ӵ�
		if(attackCoolDown&&alive) {
		Point p=this.getHeadPoint();
		Bullet bullet=new Bullet("images/bullet.png", p.x,p.y,this.gamePanel,this.direction);
		this.gamePanel.bulletList.add(bullet);
		new attackCD().start();
	}
	}
	
	public Point getHeadPoint() {
		switch(direction) {
		case LEFT:
			return new Point(x,y+height/2);
		case RIGHT:
			return new Point(x+width,y+height/2);
		case UP:
			return new Point(x+width/2,y);
		case DOWN:
			return new Point(x+width/2,y+height);
			default:
				 return null;
		}
		
	}
	//Χǽ��ײ���
	public boolean hitWall(int x,int y) {
		//Χǽ�б�
		ArrayList<Wall> walls=this.gamePanel.wallList;
		//��һ������
		Rectangle next=new Rectangle(x,y,width,height);
		//�����б�
		for(Wall wall:walls) {
			//��ÿһ��Χǽ������ײ���
			if(next.intersects(wall.getRec())) {
				//������ײ������true
				return true;
			}
		}
		return false;
	}
	//�߽���
	public boolean moveToBorder(int x,int y) {
		if(x<0) {
			return true;
		}
		else if(x+width>this.gamePanel.getWidth()) {
			return true;
		}
		else if(y<0) {
			return true;
		}
		else if (y+height>this.gamePanel.getHeight()) {
			return true;
		}
		return false;
	}
	
	public void setImg(String img) {
		this.img=Toolkit.getDefaultToolkit().getImage(img);
	}
	//������߳̿�����ȴʱ��
	class attackCD extends Thread{
		public void run() {
			//��������������Ϊ��ȴ״̬
			attackCoolDown=false;
			//����һ��
			try {
				Thread.sleep(attackCoolDownTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//���������ܽ��Ϊ��ȴ״̬
			attackCoolDown=true;
			//�߳���ֹ
			this.stop();
		}
	}
	@Override
	public abstract void paintSelf(Graphics g);
	@Override
	public abstract Rectangle getRec();
	
	
	//Tank��ķ���ֱ�Ӽ̳м���
	
	

}
