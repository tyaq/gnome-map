package roadMapGnomes;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import roadMap.Graph;
import roadMap.Road;
import roadMap.Village;
import roadMapGUI.GUI;

public class Gnome implements Runnable{
	private String name;
	private Village location;
	private LinkedList<Road> path;
	private static final int TIMINGFACTOR=100;//Used for delaying treads
	private int traveled;
    private int myNumber;
    private String state=name+" is a traveling gnome. Currently at"+location+".";
    private static int personNumber=1;
    
    public Gnome(Graph g) {
    		name="Gnome "+personNumber;
    		personNumber++;
    		
    		//Begin to add Gnome to random village in graph
    		Object[] array = g.vertices().values().toArray();
    		
    		location=(Village) array[(int)(Math.random()*array.length)];
    		
    		path=new LinkedList<Road>();
    		
    		addToMenu();
    		
    		
    		Thread t = new Thread(this);
        t.setName(name);
        t.start();
    }//End Constructor Method
    
    public void run() {
    		Village last=null;
    		long start =System.nanoTime();
    		while(last!=location){
    			last=location;
    			state=name+" is a traveling gnome. Currently at Village "+location+".";
    			travel();
    		}//End travel While
    		long end =System.nanoTime();
    		String time =String.format("%.2f",((end-start)/1000000000.))+" seconds";
    		if (path.size()==0){
    			state=name +" hit the end of the line,"
    					+ " they started at "+location+" and could not find anywhere else to go, taking "+time+".";
    		} else if (path.size()==1) {
    			state="In "+time+" the adventure has ended as quickly as it started for "+name+
    					" they started at "+path.getFirst().tail()+" and went to "
        				+ location + " traveling\npath: "+path+".";
    		} else {
    		state=name + " started at "+path.getFirst().tail()+" and went to "
    				+ location + " traveling for "+time+" on\npath: "+path+".";}
    }
    
    private void addToMenu(){
    		JMenuItem gnome = new JMenuItem(name);
    		class gno implements ActionListener{
            	
        		public void actionPerformed (ActionEvent e){
        			JTextArea area = new JTextArea(state);
        			area.setEditable(true);
        			area.setLineWrap(true);
        			area.setWrapStyleWord(true);
        			area.setMargin(new Insets(5,5,5,5));
        			area.setBounds(0, 0, 500, 600);
        			JScrollPane scroll = new JScrollPane();
        			scroll.getViewport().setView(area);
        			JOptionPane.showMessageDialog(null, scroll);
        			
        			}
            }//end class
            gnome.addActionListener(new gno());
            roadMapGUI.MenuBar.gnoMen.add(gnome);
    }
    
    public synchronized void travel(){
    		if (location.edges().size()!=0){
    			Road nextRoad =location.edges().get((int)(Math.random()*location.edges().size()));
	    		path.add(nextRoad);
	    		traveled+=nextRoad.weight();
	    		try {
					this.wait(nextRoad.weight()*TIMINGFACTOR);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("interuption");
					Thread.currentThread().interrupt();
				}
	    		Village nextVill=nextRoad.tip();//Go to tip of any connected city
	    		location=nextVill;
    		}//End if
    }//End travel method
    
    public int traveled(){
    		return traveled;
    }//End GetTraveled
    
    public Village location(){
    		return location;
    }//End getLocation
    
    public LinkedList<Road> path(){
    		return path;
    }//end getPath
}//End Class
