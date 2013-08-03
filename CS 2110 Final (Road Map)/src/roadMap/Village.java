package roadMap;

import java.util.ArrayList;


public class Village {
	
	static int numberOfVillages = 1;
	private String data;//Holds that actual data, does not need to be String Could have been generic
	private ArrayList<Road> edges;//Adjacency List
	
	public Village(String d){
		numberOfVillages++;
		data=d;
		edges = new ArrayList<Road>();
	}//End constructor
	
	public void add(Road v){
		if (edges != null && edges.contains(v) ) return;
		edges.add(v);
	}//End add method
	
	
	public void remove(Road e){
		edges.remove(e);
	}
	
	public void setData(String d){
		data=d;
	}//End setData
	
	public String data(){
		return data;
	}//End getData
	
	public ArrayList<Road> edges(){
		return edges;
	}//End getEdges
	
	public String toString(){
		return data;
	}
}
