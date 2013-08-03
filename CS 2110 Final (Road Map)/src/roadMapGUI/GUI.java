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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JMenu;


public class GUI extends JFrame{
	
	public static Graph g =new Graph();
	public static PaintGraph pg=new PaintGraph(g);
	public static MenuBar menu = new MenuBar();
	
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	//For working on existing catalog
	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void importCountry() throws IOException, ClassNotFoundException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		//Ask user for either a file path of existing library
					System.out.println("Give the path of a file to import your library or leave it empty.");
					String inputFilePath=br.readLine();
					
				//Checks if file exists	
					File file = new File(inputFilePath);
					if (file.exists() && file.canRead()) {
							FileInputStream fis = new FileInputStream(inputFilePath);
					        ObjectInputStream ois = new ObjectInputStream(fis);
					        
					        g = (Graph) (ois.readObject());
					        pg=(PaintGraph) (ois.readObject());
					        menu =(MenuBar) ois.readObject();
					        fis.close();
					        System.out.println("Your library has been loaded.\n");
						}
		
	}
	
	//For saving out catalog
	/**
	 * 
	 * @throws IOException
	 */
	public void exportCountry() throws IOException {
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader(isr);
			System.out.println("Write File path to save your library.");
			String outputFileName=br.readLine();
			File file = new File(outputFileName);
			file.createNewFile();
			FileOutputStream fos = null;
		    ObjectOutputStream oos = null;
				
				try {
			        fos = new FileOutputStream(outputFileName,false);
			        oos = new ObjectOutputStream(fos);
			        oos.writeObject(g);
			        oos.writeObject(pg);
			        oos.writeObject(menu);
			        oos.close();
			        System.out.println("Library saved. Thank you for using Omish Music Cataloger");
			    } catch (IOException ex) {
			        ex.printStackTrace();
			    }
				System.out.println("You can find it encryped at " + outputFileName + ". You will need it to import your library later.");
		
	}

}//End Class
