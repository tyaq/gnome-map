package roadMapGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import roadMap.Graph;

public class MenuBar {
	JTextArea output;
    JScrollPane scrollPane;
    
    private Graph g;
    private PaintGraph pg;
    private JMenuBar m;
    
    public MenuBar(Graph g,PaintGraph pg){
    		this.g=g;
    		this.pg=pg;
    		m = createMenuBar();
    }
    
    public Graph graph(){
    		return g;
    }//End get graph
    
    public JMenuBar menu(){
    		return m;
    }//End get Menu
    
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
 
        //Create the menu bar.
        menuBar = new JMenuBar();
 
        //Build the first menu.
        menu = new JMenu("Village");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Add Village",
                                 KeyEvent.VK_A);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        class addv implements ActionListener{
        	
        		public void actionPerformed (ActionEvent e){
        			JTextField vName = new JTextField();
        			JPanel panel = new JPanel(new GridLayout(2,0));
        			panel.add(new JLabel("Village Name:"));
        			panel.add(vName);
        			int result = JOptionPane.showConfirmDialog(null, panel, "Add Village",
    		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    		        if (result == JOptionPane.OK_OPTION) {
    		            g.addVertex(vName.getText());
    		            pg.setGraph(g);
    		            pg.validate();
    		            pg.repaint();
    		            pg.validate();
    		        } else {
    		            System.out.println("Cancelled");
    		        }//End else
        		    
        		}
        }//end class
        menuItem.addActionListener(new addv());
        menu.add(menuItem);
        	
        
        			//delete
        menuItem = new JMenuItem("Delete Village");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);
 
        //Build second menu in the menu bar.
        menu = new JMenu("Roads");
        menu.setMnemonic(KeyEvent.VK_R);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);
        
        //2nd menu items
        menuItem= new JMenuItem("Add Road");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menu.add(menuItem);
        
        menuItem=new JMenuItem("Delete Road");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);
 
        return menuBar;
    }
 
    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
 
        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);
 
        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);
 
        return contentPane;
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = GUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
