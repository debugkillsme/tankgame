package com.nju;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class GamePanel extends JFrame{
	private static final long serialVersionUID = 1L;
	//通过双缓存技术解决闪烁问题,定义双缓存图片
	Image offScreenImage=null;
	
	//建立窗口长度
	int width=800;
	int height=610;
	//启动方法
	//指针图片
	Image select=Toolkit.getDefaultToolkit().getImage("images/pointer.jpg");
	//初始纵坐标
	int y=150;
	//游戏模式 0:游戏未开始 1:单人模式 2:双人模式 5.游戏胜利
	int state=0;
	int a=1;
	//重绘次数
	int count=0;
	//已生成敌人数量
	int enemyCount=0;
	ArrayList<Bullet> bulletList =new ArrayList<Bullet>();
	ArrayList<Bot> botList=new ArrayList<Bot>();
	ArrayList<Bullet> removeList=new ArrayList<Bullet>();
	ArrayList<Tank> playerList=new ArrayList<Tank>();
	ArrayList<Wall> wallList=new ArrayList<Wall>();
	ArrayList<Base> baseList=new ArrayList<Base>();
	ArrayList<Blast> blastList=new ArrayList<Blast>();
	
	PlayerOne playerOne=new PlayerOne("images/player1/player1up.jpg",125,510,this,"images/player1/player1up.jpg","images/player1/player1left.jpg",
			"images/player1/player1right.jpg","images/player1/player1down.jpg");
	
	PlayerTwo playerTwo=new PlayerTwo("images/player2/player2up.jpg",625,510,this,"images/player2/player2up.jpg","images/player2/player2left.jpg",
			"images/player2/player2right.jpg","images/player2/player2down.jpg");
	Base base=new Base("images/base.jpg",365,560,this);
	
	public void launch() {
		//标题
		setTitle("哈利波特大战斯莱特林坏蛋");
		//窗口初始化大小
		setSize(width,height);
		//屏幕居中
		setLocationRelativeTo(null);
		//添加关闭事件
		setDefaultCloseOperation(3);
		//用户不能调整大小
		setResizable(false);
		//使窗口可见
		setVisible(true);
		this.addKeyListener(new GamePanel.KeyMonitor());//设置监听器
		//添加围墙
		for(int i=0;i<12;i++) {
			wallList.add(new Wall("images/wall.jpg",i*60,170,this));
		}
		wallList.add(new Wall("images/wall.jpg",305,560,this));
		wallList.add(new Wall("images/wall.jpg",305,500,this));
		wallList.add(new Wall("images/wall.jpg",365,500,this));
		wallList.add(new Wall("images/wall.jpg",425,500,this));
		wallList.add(new Wall("images/wall.jpg",425,560,this));
		//添加基地
		baseList.add(base);
		while(true) {
			//游戏胜利判定
			if(botList.size()==0&&enemyCount==10) {
				state=5;
			}
			//游戏失败判定
			if((playerList.size()==0&&(state==1||state==2))||baseList.size()==0) {
				state=4;
			}
			//添加电脑坦克
			if(count%100==1&&enemyCount<10&&(state==1||state==2)) {
				Random random=new Random();
				int rnum=random.nextInt(600);
				botList.add(new Bot("images/enemy/enemy1U.jpg",rnum,110,this,
						"images/enemy/enemy1U.jpg","images/enemy/enemy1L.jpg",
						"images/enemy/enemy1R.jpg","images/enemy/enemy1D.jpg"));
				enemyCount++;
			}
			repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//创建和窗口一样大小的图片
		if(offScreenImage==null) {
			offScreenImage=this.createImage(width,height);
		}
		Graphics gImage=offScreenImage.getGraphics();
		//获得该图片的画布
		
		//设置背景颜色
		gImage.setColor(Color.gray);
		gImage.fillRect(0, 0, width, height);
		gImage.setColor(Color.white);
		gImage.setFont(new Font("仿宋",Font.BOLD,50));
		if(state==0) {
			gImage.drawString("按键1、2选择游戏模式", 220, 100);
			gImage.drawString("单人模式", 220, 200);
			gImage.drawString("双人模式", 220, 300);
			gImage.drawImage(select, 120,y,100,50,null);
		}
		//state==0/1, 游戏开始
		else if(state==1||state==2) {
			
			gImage.setFont(new Font("仿宋",Font.BOLD,30));
			gImage.setColor(Color.red);
			gImage.drawString("剩余敌人:"+botList.size(),0,50);
			//绘制游戏元素
			for(Tank player:playerList) {
				player.paintSelf(gImage);
			}
			for(Bullet bullet:bulletList) {
				bullet.paintSelf(gImage);
			}
			bulletList.removeAll(removeList);
			
			for(Bot bot:botList) {
				//绘制敌人
				bot.paintSelf(gImage);
			}
			for(Wall wall:wallList) {
				wall.paintSelf(gImage);
			}
			for(Base base:baseList) {
				base.paintSelf(gImage);
			}
			for(Blast blast:blastList) {
				blast.paintSelf(gImage);
			}
			count++;
		}
		else if(state==3) {
			gImage.drawString("游戏暂停",220,200);
		}
		else if(state==5) {
			gImage.drawString("游戏胜利",220,200);
		}
		else if(state==4) {
			gImage.drawString("游戏失败", 220, 200);
		}
		//将缓存区绘制好的图片绘制到容器的画布中
		g.drawImage(offScreenImage,0,0,null);
		
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	//键盘监视器
	class KeyMonitor extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_1:
				a=1;
				y=150;
				break;
			case KeyEvent.VK_2:
				a=2;
				y=250;
				break;
			case KeyEvent.VK_ENTER:
				state=a;
				playerList.add(playerOne);
				//添加玩家2
				if(state==2) {
					playerList.add(playerTwo);
					playerTwo.alive=true;
				}
				playerOne.alive=true;
				break;
			case KeyEvent.VK_P:
				//实现游戏暂停的来回切换
				if(state!=3) {
					a=state;
					state=3;
				}
				else {
					state=1;
					if(a==0) {
						a=1;
					}
				}
			default:
				playerOne.keyPressed(e);
				playerTwo.keyPressed(e);
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			playerOne.keyReleased(e);
			playerTwo.keyReleased(e);
		}
	}
	public static void main(String[] args) {
		GamePanel gp=new GamePanel();
		gp.launch();
	}
	
	
	//使窗口可见
	

}
