package com.nju;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
public abstract class Tank extends GameObject {
	//尺寸
	public int width=50;
	public int height=50;
	public int speed=3;
	public Direction direction=Direction.UP;
	
	//定义四个方向的坦克图片
	public String upImg;
	public String leftImg;
	public String rightImg;
	public String downImg;
	//生命存活
	public boolean alive=false;
	//攻击冷却状态
	public boolean attackCoolDown=true;
	//攻击冷却时间间隔
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
		//发射子弹
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
	//围墙碰撞检测
	public boolean hitWall(int x,int y) {
		//围墙列表
		ArrayList<Wall> walls=this.gamePanel.wallList;
		//下一步矩形
		Rectangle next=new Rectangle(x,y,width,height);
		//遍历列表
		for(Wall wall:walls) {
			//与每一个围墙进行碰撞检测
			if(next.intersects(wall.getRec())) {
				//发生碰撞，返回true
				return true;
			}
		}
		return false;
	}
	//边界检测
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
	//添加新线程控制冷却时间
	class attackCD extends Thread{
		public void run() {
			//将攻击功能设置为冷却状态
			attackCoolDown=false;
			//休眠一秒
			try {
				Thread.sleep(attackCoolDownTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//将攻击功能解除为冷却状态
			attackCoolDown=true;
			//线程终止
			this.stop();
		}
	}
	@Override
	public abstract void paintSelf(Graphics g);
	@Override
	public abstract Rectangle getRec();
	
	
	//Tank类的方法直接继承即可
	
	

}
