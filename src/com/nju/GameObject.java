package com.nju;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
public abstract class GameObject {
	//ͼƬ
	public Image img;
	//����
	public int x;
	public int y;
	//����
	public GamePanel gamePanel;
	
	public GameObject(String img,int x,int y,GamePanel gamePanel) {
		this.img=Toolkit.getDefaultToolkit().getImage(img);
		this.x=x;
		this.y=y;
		this.gamePanel=gamePanel;
		
	}
	public abstract void paintSelf(Graphics g) ;//������ͼ
	
	public abstract Rectangle getRec();//�����Ծ���
	
}

