package com.nju;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
public abstract class GameObject {
	//图片
	public Image img;
	//坐标
	public int x;
	public int y;
	//界面
	public GamePanel gamePanel;
	
	public GameObject(String img,int x,int y,GamePanel gamePanel) {
		this.img=Toolkit.getDefaultToolkit().getImage(img);
		this.x=x;
		this.y=y;
		this.gamePanel=gamePanel;
		
	}
	public abstract void paintSelf(Graphics g) ;//画布绘图
	
	public abstract Rectangle getRec();//返回自矩阵
	
}

