import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class Client extends JFrame implements ActionListener{

	CMClientStub m_clientStub;
	CMClientEventHandler m_eventHandler;
	private static boolean m_bRun;
	static Scanner m_scan = null;
	
	// Swing
	int screenWidth;
	int screenHeigth;
	JButton Btn1;
	JButton Btn2;
	JButton Btn3;
	JButton Btn4;
	JPanel panel1, panel2, panel3;
	JLabel label;
	JTextField nameText;
	
	public Client()
	{
		m_clientStub = new CMClientStub();
		m_eventHandler = new CMClientEventHandler();
		m_bRun = true;
		
		Container cont = this.getContentPane();
		Toolkit kit = this.getToolkit();
		ImageIcon image1 = new ImageIcon("img/weapon1.png");
		ImageIcon image2 = new ImageIcon("img/weapon2.png");
		ImageIcon logo = new ImageIcon("img/logo.png");
		panel1 = new JPanel();
		label = new JLabel(logo);
		panel1.add(label);
		
		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,500,15));
		ButtonGroup g = new ButtonGroup(); // ��ư �׷� ��ü ����
		JRadioButtonMenuItem rb1 = new JRadioButtonMenuItem("������", image1, true);
		JRadioButtonMenuItem rb2 = new JRadioButtonMenuItem("��������", image2, false);
		g.add(rb1);
		g.add(rb2);

		Btn1 = new JButton("LOGIN");
		Btn1.setPreferredSize(new Dimension(100, 40));
		Btn1.addActionListener(this);

		panel2.add(rb1);
		panel2.add(rb2);
		
		panel3 = new JPanel();
		nameText = new JTextField(10);
		panel3.add(new JLabel("�̸� "));
		panel3.add(nameText);		
		panel2.add(panel3);
		
		panel2.add(Btn1);
		
		cont.add(panel1, BorderLayout.NORTH);
		cont.add(panel2, BorderLayout.CENTER);
				
		Dimension screenSize = kit.getScreenSize();
		this.screenWidth = screenSize.width;
		this.screenHeigth = screenSize.height;
		this.setSize(400, 500);
		this.setLocation(screenWidth/2 -200 , screenHeigth/2 -300);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // â ����� ���α׷� ����
		this.setTitle("CMAttack Login");
		
		this.setResizable(false); // â ũ�� ���� ���ϰ�
	}
	public void testDummyEvent()
	{
//		CMDummyEvent due = new CMDummyEvent();
//		due.setDummyInfo("CurrentTime");
//		m_clientStub.send(due, "SERVER");
//		due = null;
//		System.out.println("[Client] ������ ����ð��� ��û�߽��ϴ�. ");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	public void Login(Client client)
	{
		client.m_clientStub.loginCM(nameText.getText(), "1234");
	}
	
	public void Game()
	{
		//���� ������
	}
	
	public void GameOver()
	{
		// ���� ���â ������
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client client = new Client();
		client.m_clientStub.setAppEventHandler(client.m_eventHandler);
		client.m_clientStub.startCM();
		client.Login(client);
	}

}
