package roadMapGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
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
import roadMap.Road;
import roadMapGnomes.GnomeGenerator;

public class MenuBar {
	JTextArea output;
    JScrollPane scrollPane;
    
    
    public MenuBar(){
    	
    }
    
//    public Graph graph(){
//    		return g;
//    }//End get graph
    
//    public JMenuBar menu(){
//    		return m;
//    }//End get Menu
    public static JMenu gnoMen = new JMenu("Gnomes");
    
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
                KeyEvent.VK_V, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        class addv implements ActionListener{
        	
        		public void actionPerformed (ActionEvent e){
        			String guess = roadMap.Village.numberOfVillages+"";
        			JTextField vName = new JTextField(guess);
        			JPanel panel = new JPanel(new GridLayout(2,0));
        			panel.add(new JLabel("Village Name:"));
        			panel.add(vName);
        			int result = JOptionPane.showConfirmDialog(null, panel, "Add Village",
    		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    		        if (result == JOptionPane.OK_OPTION) {
    		            GUI.g.addVertex(vName.getText());
    		            GUI.pg.setGraph(GUI.g);
    		            GUI.pg.repaint();
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
        class deletev implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField vName = new JTextField();
    			JPanel panel = new JPanel(new GridLayout(2,0));
    			panel.add(new JLabel("Village Name:"));
    			panel.add(vName);
    			int result = JOptionPane.showConfirmDialog(null, panel, "Add Village",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		            GUI.g.removeVertex(vName.getText());
		            GUI.pg.setGraph(GUI.g);
		            GUI.pg.repaint();
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    		}
    }//end class
    menuItem.addActionListener(new deletev());
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
        class addr implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField tail = new JTextField();
    			JTextField tip = new JTextField();
    			JTextField weight = new JTextField();
    			JCheckBox twoWay =new JCheckBox();
    			JPanel panel = new JPanel(new GridLayout(1,3));
    			panel.add(new JLabel("Edge:"));
    			panel.add(tail);
    			panel.add(new JLabel("------->"));
    			panel.add(tip);
    			panel.add(new JLabel("Weight:"));
    			panel.add(weight);
    			panel.add(new JLabel("Two Way:"));
    			panel.add(twoWay);
    			int result = JOptionPane.showConfirmDialog(null, panel, "Add Road",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		GUI.g.addEdge(tail.getText(), tip.getText(), weight.getText(), twoWay.isSelected());
		            GUI.pg.setGraph(GUI.g);
		            GUI.pg.repaint();
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new addr());
        menu.add(menuItem);
        
        menuItem=new JMenuItem("Delete Road");
        menuItem.setMnemonic(KeyEvent.VK_D);
        class deleter implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField tail = new JTextField();
    			JTextField tip = new JTextField();
    			JPanel panel = new JPanel(new GridLayout(1,3));
    			panel.add(new JLabel("Edge:"));
    			panel.add(tail);
    			panel.add(new JLabel("------->"));
    			panel.add(tip);
    			int result = JOptionPane.showConfirmDialog(null, panel, "Remove Road",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		GUI.g.removeEdge(tail.getText(), tip.getText());
		            GUI.pg.setGraph(GUI.g);
		            GUI.pg.repaint();
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new deleter());
        menu.add(menuItem);
 
        //Third subMenu
        menu = new JMenu("Network");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "Use functions which are done over the current graph");
        menuBar.add(menu);
        
        menuItem=new JMenuItem("Maximum Flow");
        menuItem.setMnemonic(KeyEvent.VK_F);
        class maxflow implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField source = new JTextField();
    			JTextField sink = new JTextField();
    			JPanel panel = new JPanel(new GridLayout(1,3));
    			panel.add(new JLabel("Source Village:"));
    			panel.add(source);
    			panel.add(new JLabel("Sink Village:"));
    			panel.add(sink);
    			int result = JOptionPane.showConfirmDialog(null, panel, "Maximum Flow(Ford-Fulkerson)",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		int flow = GUI.g.maxFlow(source.getText(), sink.getText());
		        		JOptionPane.showMessageDialog(null, "The maximum flow from "+source.getText()+" to "+sink.getText()+" with this road network is "+flow+".");
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new maxflow());
        menu.add(menuItem);
        
        menuItem=new JMenuItem("Shortest Path");
        menuItem.setMnemonic(KeyEvent.VK_P);
        class path implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField start = new JTextField();
    			JTextField end = new JTextField();
    			JPanel panel = new JPanel(new GridLayout(2,2));
    			panel.add(new JLabel("Starting Village:"));
    			panel.add(start);
    			panel.add(new JLabel("Destination Village:"));
    			panel.add(end);
    			int result = JOptionPane.showConfirmDialog(null, panel, "Maximum Flow(Ford-Fulkerson)",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		LinkedList<Road> p = GUI.g.getPath(start.getText(), end.getText());
		        		JOptionPane.showMessageDialog(null, "The shortest path from "+start.getText()+" to "+end.getText()+" with this road network is following "+p+".");
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new path());
        menu.add(menuItem);
        
        menuItem=new JMenuItem("Austerity");
        menuItem.setMnemonic(KeyEvent.VK_A);
        class mst implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			int result = JOptionPane.showConfirmDialog(null, "Austerity measures will be taken within this kingdom.\n"
    					+ " Any roads deemed no essential to the network will be bulldozed, for resources,\n"
    					+ " Also all remaining roads will become two-way roads.\n"
    					+ " Gnomes may be replaced, but probably not.", "Austerity(Kruskal MST)",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		GUI.g=GUI.g.kruskal().toUndirected();
		        		GUI.pg.setGraph(GUI.g);
			        GUI.pg.repaint();
		        		
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new mst());
        menu.add(menuItem);
        
        //Fourth subMenu
        menu = new JMenu("Gnome");
        menu.setMnemonic(KeyEvent.VK_G);
        menu.getAccessibleContext().setAccessibleDescription(
                "Use functions which relate to Gnomes");
        menuBar.add(menu);
        
      //Fourth subMenu subMenu
        
        gnoMen.setMnemonic(KeyEvent.VK_G);
        gnoMen.getAccessibleContext().setAccessibleDescription(
                "Veiw all of your Gnomes");
        menu.add(gnoMen);
        
        menuItem=new JMenuItem("Generate Gnomes");
        menuItem.setMnemonic(KeyEvent.VK_F);
        class gg implements ActionListener{
        	
    		public void actionPerformed (ActionEvent e){
    			JTextField numGnomes = new JTextField();
    			JPanel panel = new JPanel(new GridLayout(1,3));
    			panel.add(new JLabel("Generate"));
    			panel.add(numGnomes);
    			panel.add(new JLabel("Gnome(s)"));
    			int result = JOptionPane.showConfirmDialog(null, panel, "Gnome Generator",
		        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		        if (result == JOptionPane.OK_OPTION) {
		        		GnomeGenerator gg =new GnomeGenerator(Integer.parseInt(numGnomes.getText()),GUI.g);
		        		JOptionPane.showMessageDialog(null, "Check the Gnomes list to see your Gnomes");
		        } else {
		            System.out.println("Cancelled");
		        }//End else
    		    
    			}
        }//end class
        menuItem.addActionListener(new gg());
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
