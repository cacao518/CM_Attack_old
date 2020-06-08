
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MatchDialog extends JDialog implements ActionListener{
	int screenWidth;
	int screenHeigth;
	
	Client parent;
	
	JPanel root;
	
	JPanel panel1, panel2;
	JButton Btn1, Btn2, Btn3;

	CardLayout card;
	
	JButton Pbt;
	
	public void init() {
		panel1 = new JPanel();
		Btn1 = new JButton("All");
		Btn2 = new JButton("My");
		Btn3 = new JButton("Wrong");
		
		Pbt = new JButton("�ܾ��߰�");
		
		Btn1.addActionListener(this);
		Btn2.addActionListener(this);
		Btn3.addActionListener(this);
		
		panel1.add(Btn1);
		panel1.add(Btn2);
		panel1.add(Btn3);


		Pbt.addActionListener(this);
		
		this.add(panel1, BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(Pbt, BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter() {
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
			super.windowClosing(e);
			parent.dlg1 = null; // �θ��� dlg�� �η� ����� �ٽ� ���̾�α�â �����ְ� �س��⶧��
			dispose(); // �ڽŸ� �����
		}});
	}
	
	MatchDialog(Client client, String title, boolean modal)
	{
		super(client, title, modal);
		init();
		parent = client;
		
		Toolkit kit = this.getToolkit();
		Dimension screenSize = kit.getScreenSize();
		this.screenWidth = screenSize.width;
		this.screenHeigth = screenSize.height;
		this.setTitle(title);
		this.setSize(300, 500);
		this.setLocation(screenWidth/2 -150 , screenHeigth/2 -250);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // x��ư ������ ���Ұž� (���̾˷α�â�� ������)
		this.setVisible(true);
		
		this.setResizable(false); // â ũ�� ���� ���ϰ�
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	

	

}
