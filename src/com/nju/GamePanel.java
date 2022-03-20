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
	//ͨ��˫���漼�������˸����,����˫����ͼƬ
	Image offScreenImage=null;
	
	//�������ڳ���
	int width=800;
	int height=610;
	//��������
	//ָ��ͼƬ
	Image select=Toolkit.getDefaultToolkit().getImage("images/pointer.jpg");
	//��ʼ������
	int y=150;
	//��Ϸģʽ 0:��Ϸδ��ʼ 1:����ģʽ 2:˫��ģʽ 5.��Ϸʤ��
	int state=0;
	int a=1;
	//�ػ����
	int count=0;
	//�����ɵ�������
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
		//����
		setTitle("�������ش�ս˹�����ֻ���");
		//���ڳ�ʼ����С
		setSize(width,height);
		//��Ļ����
		setLocationRelativeTo(null);
		//��ӹر��¼�
		setDefaultCloseOperation(3);
		//�û����ܵ�����С
		setResizable(false);
		//ʹ���ڿɼ�
		setVisible(true);
		this.addKeyListener(new GamePanel.KeyMonitor());//���ü�����
		//���Χǽ
		for(int i=0;i<12;i++) {
			wallList.add(new Wall("images/wall.jpg",i*60,170,this));
		}
		wallList.add(new Wall("images/wall.jpg",305,560,this));
		wallList.add(new Wall("images/wall.jpg",305,500,this));
		wallList.add(new Wall("images/wall.jpg",365,500,this));
		wallList.add(new Wall("images/wall.jpg",425,500,this));
		wallList.add(new Wall("images/wall.jpg",425,560,this));
		//��ӻ���
		baseList.add(base);
		while(true) {
			//��Ϸʤ���ж�
			if(botList.size()==0&&enemyCount==10) {
				state=5;
			}
			//��Ϸʧ���ж�
			if((playerList.size()==0&&(state==1||state==2))||baseList.size()==0) {
				state=4;
			}
			//��ӵ���̹��
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
		//�����ʹ���һ����С��ͼƬ
		if(offScreenImage==null) {
			offScreenImage=this.createImage(width,height);
		}
		Graphics gImage=offScreenImage.getGraphics();
		//��ø�ͼƬ�Ļ���
		
		//���ñ�����ɫ
		gImage.setColor(Color.gray);
		gImage.fillRect(0, 0, width, height);
		gImage.setColor(Color.white);
		gImage.setFont(new Font("����",Font.BOLD,50));
		if(state==0) {
			gImage.drawString("����1��2ѡ����Ϸģʽ", 220, 100);
			gImage.drawString("����ģʽ", 220, 200);
			gImage.drawString("˫��ģʽ", 220, 300);
			gImage.drawImage(select, 120,y,100,50,null);
		}
		//state==0/1, ��Ϸ��ʼ
		else if(state==1||state==2) {
			
			gImage.setFont(new Font("����",Font.BOLD,30));
			gImage.setColor(Color.red);
			gImage.drawString("ʣ�����:"+botList.size(),0,50);
			//������ϷԪ��
			for(Tank player:playerList) {
				player.paintSelf(gImage);
			}
			for(Bullet bullet:bulletList) {
				bullet.paintSelf(gImage);
			}
			bulletList.removeAll(removeList);
			
			for(Bot bot:botList) {
				//���Ƶ���
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
			gImage.drawString("��Ϸ��ͣ",220,200);
		}
		else if(state==5) {
			gImage.drawString("��Ϸʤ��",220,200);
		}
		else if(state==4) {
			gImage.drawString("��Ϸʧ��", 220, 200);
		}
		//�����������ƺõ�ͼƬ���Ƶ������Ļ�����
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
	//���̼�����
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
				//������2
				if(state==2) {
					playerList.add(playerTwo);
					playerTwo.alive=true;
				}
				playerOne.alive=true;
				break;
			case KeyEvent.VK_P:
				//ʵ����Ϸ��ͣ�������л�
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
	
	
	//ʹ���ڿɼ�
	

}
