package roadMapGnomes;

import java.util.LinkedList;

import roadMap.Graph;
import roadMap.Road;
import roadMap.Village;

public class Gnome {
	private String name;
	private Village location;
	private LinkedList<Road> path;
	private int traveled;
    private int myNumber;
    private static int personNumber=1;
    
    public Gnome(Graph g){
    		name="Gnome "+personNumber;
    		personNumber++;
    		
    		//Begin to add Gnome to random village in graph
    		Object[] array = g.vertices().values().toArray();
    		
    		location=(Village) array[(int)(Math.random()*array.length)];
    		
    		path=new LinkedList<Road>();
    		System.out.println(location);
    }//End Constructor Method
    
    public void travel(){
    		if (location.edges().size()!=0){
    			Road nextRoad =location.edges().get((int)(Math.random()*location.edges().size()));
	    		path.add(nextRoad);
	    		traveled+=nextRoad.weight();
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
