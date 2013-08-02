package roadMapGnomes;

import java.util.LinkedList;

import roadMap.Graph;
import roadMap.Road;
import roadMap.Village;

public class Gnome implements Runnable{
	private String name;
	private Village location;
	private LinkedList<Road> path;
	private static final int TIMINGFACTOR=100;//Used for delaying treads
	private int traveled;
    private int myNumber;
    private static int personNumber=1;
    
    public Gnome(Graph g) {
    		name="Gnome "+personNumber;
    		personNumber++;
    		
    		//Begin to add Gnome to random village in graph
    		Object[] array = g.vertices().values().toArray();
    		
    		location=(Village) array[(int)(Math.random()*array.length)];
    		
    		path=new LinkedList<Road>();
    		
    		Thread t = new Thread(this);
        t.setName(name);
        t.start();
    }//End Constructor Method
    
    public void run() {
    		Village last=null;
    		while(last!=location){
    			last=location;
    			travel();
    		}//End travel While
    		if (path.size()==0){
    			System.out.println(name +" hit the end of the line,"
    					+ " they started at "+location+" and could not find anywhere else to go.");
    		} else if (path.size()==1) {
    			System.out.println("The adventure has ended as quickly as it started for "+name+
    					" they started at "+path.getFirst().tail()+" and went to "
        				+ location + " traveling\npath: "+path+".");
    		} else {
    		System.out.println(name + " started at "+path.getFirst().tail()+" and went to "
    				+ location + " traveling\npath: "+path+".");}
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
