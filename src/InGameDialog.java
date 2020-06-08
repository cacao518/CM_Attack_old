import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class InGameDialog  extends JFrame implements ActionListener, Runnable {

	Client client;
	
	Toolkit kit;
	Container cont;
	int screenWidth;
	int screenHeigth;
	JButton Btn1;
	JButton Btn2;
	JButton Btn3;
	JButton Btn4;
	JPanel panel1;
	JLabel label;
	JTextField nameText;

	Image cursorimage;
	Image BGimg, FPGunimg1, OtherPlayer_idle_img;
	Image buffImage;
	Graphics buffg;
	Cursor cursor;
	Point point;
	Thread Game = null;
    Graphics bufferGraphics;

	int BGposX = -300;
	int OPposX = 395;
	long millis = System.currentTimeMillis();
	int FireMotionTime = 0;
	
	InGameDialog (Client c)
	{
		client = c;
		
		BGimg = new ImageIcon("img/backGround.png").getImage();
		FPGunimg1 = new ImageIcon("img/FirstPersonGun1.png").getImage();
		OtherPlayer_idle_img = new ImageIcon("img/OtherPlayer_idle.png").getImage();
		cont = this.getContentPane();
		kit = this.getToolkit();
		panel1 = new JPanel();
		
		Dimension screenSize = kit.getScreenSize();
		this.screenWidth = screenSize.width;
		this.screenHeigth = screenSize.height;
		this.setSize(800, 600);
		this.setLocation(screenWidth/2 -400 , screenHeigth/2 -300);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 종료시 프로그램 종료
		this.setTitle("CMAttack");
		
		this.setResizable(false); // 창 크기 조절 못하게
		
		cursorimage=kit.getImage("img/CrossHair.png");//커서로 사용할 이미지
		point=new Point(15,15);
		cursor=kit.createCustomCursor(cursorimage, point, "haha");
		cont.setCursor(cursor); 
		
		addKeyListener(new KeyListener());
		addMouseListener(new MouseListener());
		
		DrawStart();
	}
	
	public void DrawStart()
    {
		if(Game == null)
        {
            Game = new Thread(this);
            Game.start();
            
        	buffImage = createImage(800, 600); 
    		buffg = buffImage.getGraphics();
        }
    }

    public void paint(Graphics g)
    {
		buffg.clearRect(0, 0, 800, 600);
		buffg.drawImage(BGimg, BGposX, 0, null);//background를 그려줌
		buffg.drawImage(FPGunimg1, 550, 370, null);
		buffg.drawImage(OtherPlayer_idle_img, OPposX, 305, null);
		
		g.drawImage(buffImage, 0, 0, this); 
    }
    
    public void update(Graphics g)
    {
        paint(g);
    }

    public void run() {
        while(true)
            try {
            	this.revalidate();
                this.repaint();
                
                if(FireMotionTime > 0)
                {
                	FireMotionTime--;
                	FPGunimg1 = new ImageIcon("img/FirstPersonGun2.png").getImage();
                	cursorimage=kit.getImage("img/CrossHair_dist.png");
                	point=new Point(15,15);
            		cursor=kit.createCustomCursor(cursorimage, point, "haha");
            		cont.setCursor(cursor); 
                }
                else
                {
                	FireMotionTime = 0;
                	FPGunimg1 = new ImageIcon("img/FirstPersonGun1.png").getImage();
                	cursorimage=kit.getImage("img/CrossHair.png");
                	point=new Point(15,15);
            		cursor=kit.createCustomCursor(cursorimage, point, "haha");
            		cont.setCursor(cursor); 
                }
                Game.sleep(33);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }

    }
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == Btn1)
		{

		}
	}
	public class KeyListener extends KeyAdapter{
	      public void keyPressed(KeyEvent e) {
	         switch(e.getKeyChar()) { // 입력된 키 문자
	            case 'a': // <Enter> 키 입력
	               System.out.println("pressA");
	               BGposX+=8;
	               OPposX+=8;
	               sendKey("a");
	               break;
	            case 'd':
	               System.out.println("pressD");
	               BGposX-=8;
	               OPposX-=8;
	               sendKey("d");
	               break;
	         }
	      }
	}
	public class MouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
	        long time = System.currentTimeMillis();
			long subs = millis - time;
	         if(subs<100) {
	            int x = e.getX();
	            int y = e.getY();
	            double dValue1 = Math.random();
	            double dValue2 = Math.random();
	             int dx = (int)(dValue1 * 10)-5;
	             int dy = (int)(dValue2 * 10)-5;
	            System.out.println(x);
	            System.out.println(y);
	            System.out.println(dx);
	            System.out.println(dy);
	            sendmouse(x + dx ,y + dy);
	            millis = time;
	         }
	         FireMotionTime = 5;
	      }
	}
	public void sendmouse(int x, int y) {
      CMUserEvent ue = new CMUserEvent();
      ue.setStringID("move");
      ue.setEventField(CMInfo.CM_INT, "x", String.valueOf(x));
      ue.setEventField(CMInfo.CM_INT, "y", String.valueOf(y));
      client.m_clientStub.send(ue,"SERVER");
   }
   public void sendKey(String x) {
      CMUserEvent ue = new CMUserEvent();
      ue.setStringID("move");
      ue.setEventField(CMInfo.CM_STR, "x", x);
      client.m_clientStub.send(ue,"SERVER");
   }
}