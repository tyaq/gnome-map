package roadMapGUI;

import roadMap.Graph;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JMenu;


public class GUI {
	
	private JFrame f;
	private JPanel p,p2;
	private JButton b;
	private JLabel lab;
	
	public GUI(){
		
		gui();
	}
	
	public void gui(){
		
		f=new JFrame("Creativity");
		f.setVisible(true);
		f.setSize(600,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p=new JPanel();
		p.setBackground(Color.YELLOW);
		
		p2=new JPanel();
		p2.setBackground(Color.BLUE);
		
		JButton b1 = new JButton("Test");
		lab=new JLabel("");
		
		p.add(b1);
		p.add(lab);
		
		f.add(p,BorderLayout.EAST);
		f.add(p2);
	}
	
	public static void main(String[] args){
		new GUI();
	}
	
	
	

}
