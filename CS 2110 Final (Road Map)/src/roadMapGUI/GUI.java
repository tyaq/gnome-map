package roadMapGUI;

import roadMap.Graph;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JMenu;


public class GUI extends JFrame{
	
	public static Graph g =new Graph();
	public static PaintGraph pg=new PaintGraph(g);
	public static MenuBar menu = new MenuBar();
	
	public static void main(String[] args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		//Create and set up the window.
	    JFrame frame = new JFrame("Gnome Graph");
	    frame.setPreferredSize(new Dimension(900,700));
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	    //Create and set up the content pane.
	    
	    frame.add(pg);
	    frame.setJMenuBar(menu.createMenuBar());
	    //frame.setContentPane(demo.createContentPane());
	    
	    //Display the window.
	    frame.setSize(900, 700);
	    frame.setVisible(true);
	}//End main method
	
	public GUI(){
		
	}
	
	
		

	 
	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     */
	    private static void createAndShowGUI(Graph g) {
	    		JFrame.setDefaultLookAndFeelDecorated(true);
	    		
	    		//Create and set up the window.
	        JFrame frame = new JFrame("Gnome Graph");
	        frame.setPreferredSize(new Dimension(900,700));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 
	        //Create and set up the content pane.
	        SandBox demo = new SandBox();
	        frame.setJMenuBar(demo.createMenuBar());
	        //frame.setContentPane(demo.createContentPane());
	        PaintGraph pg = new PaintGraph(g);
	        
	        frame.add(pg);
	 
	        //Display the window.
	        frame.setSize(900, 700);
	        frame.setVisible(true);
	    }
	
	
	

}//End Class
