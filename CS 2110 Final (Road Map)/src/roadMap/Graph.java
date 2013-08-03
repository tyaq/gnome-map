package roadMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Graph {

	private HashMap<String,Village> stVertices;//Storage of String parsing in Graph
	private HashMap<Village,LinkedList<Road>> vertices;//For storage of  Vertices and all edges related to them
	private LinkedList<Road> edges;//Storage of edges in graph
	
	/**
	 * Constructor creates vertices and edges storage.
	 * As those are the two things a graph should know about itself.
	 */
	public Graph(){
		this(new HashMap<String,Village>(),new HashMap<Village,LinkedList<Road>>(),new LinkedList<Road>());
	}//End Default Constructor
	
	public Graph(HashMap<String,Village> stv,HashMap<Village,LinkedList<Road>> v,LinkedList<Road> e){
		this.stVertices=stv;
		this.vertices=v;
		this.edges=e;
	}//End Constructor
	
	public Graph getGraph(){
		return this;
	}
	
	/**
	 * Parses Strings input to edge format
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	public void addEdge(String tail,String tip,String weight,boolean undirected) {
		Village tailV=null, tipV=null;//Used for converting String to Node
		
		//Create new vertices if they do not exist or else fetch them
		if (stVertices==null || !(stVertices.containsKey(tail))){
			tailV = new Village(tail);
			stVertices.put(tail, tailV);//Put new vertex in know vertices
		}//End if
		else {
			tailV = stVertices.get(tail);
		}//End else
		if (!(stVertices.containsKey(tip))){
			tipV =new Village(tip);
			stVertices.put(tip, tipV);//Put new vertex in know vertices
		}//End if
		else {
			tipV = stVertices.get(tip);
		}//End else
		
		int w=Integer.parseInt(weight);
		
		if (undirected) {
			Road r  = addEdge(tipV,tailV,w,null);
			Road s =addEdge(tailV,tipV,w,r);//Call actual add Method
			r.setPair(s);
		} else {
		
		addEdge(tipV,tailV,w,null);//Call actual add Method
		}//end else
	}//End addEdge
	
	/**
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	private Road addEdge(Village tip, Village tail, int weight,Road p) {
		// TODO Auto-generated method stub
		
		//Enter parsed data into data structures
		Road e = new Road(tail,tip,weight,p);//Create new edge
		verticesAdd(tip,e);//Record the tip
		verticesAdd(tail,e);//Record the tail
		edges.add(e);//Record for legacy
		return e;
	}//End addEdge
	
	private void verticesAdd(Village v,Road e){
		if (vertices.get(v)==null) {//In the event that a vertex does not already have a linklist made
			LinkedList<Road> referencingRoads = new LinkedList<Road>();//Create one
			referencingRoads.add(e);//Add the road to it
			vertices.put(v, referencingRoads);//Put list in hash
		}//End if null
		else {
			LinkedList<Road> temp = vertices.get(v);//Get list from map
			temp.add(e);//Add to it the road;
			vertices.put(v, temp);//Overwrite data in map
		}//End else
	}//End Method
	
	private void verticesRemove(Village v,Road e, Village target){
		LinkedList<Road> temp = vertices.get(v);//Get list from map
		if (temp.size()==1&&v!=target) {
			//create new path
			Village successor=vertices.keySet().iterator().next();
			System.out.println(e+" is the last road to or from "+v);
			if (successor==target){
				Iterator<Village> i = vertices.keySet().iterator();
				i.next();
				successor=i.next();
			}//End if
			System.out.println("Would you like to join it back to the map? At point " + successor);
			Scanner scn =new Scanner(System.in);
			String input = scn.nextLine();
			scn.close();
			if (input.equalsIgnoreCase("yes")) {
				addEdge(successor,v,e.weight(),null);
			}//End if modify
			else {
			stVertices.remove(e.tip().toString());
			}
			//System.out.println(e+" is the last road to or from "+v);
		}
		temp.remove(e);//remove road from list;
		vertices.put(v, temp);//Overwrite data in map
	}//End Method
	
	/**
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	private void addEdge(Road edge) {
		// TODO Auto-generated method stub

		//Enter parsed data into data structures
		Road e = edge;
		verticesAdd(edge.tip(),e);//Record the tip
		verticesAdd(edge.tail(),e);//Record the tail
		edges.add(e);
	}//End addEdge
	
	public void removeEdge(String tail,String tip) {
		Village tailV=null, tipV=null;//Used for converting String to Node
		
		//Check that entered vertices exist
		if (!(stVertices.containsKey(tail) && stVertices.containsKey(tip))) {
			System.out.println("One of the entered Villages does not exist.");
			return;
		}//end if
		
		tailV = stVertices.get(tail);
		tipV = stVertices.get(tip);
		
		removeEdge(tailV,tipV);
	}//End string parsing method
	
	private void removeEdge(Village tail, Village tip) {//Very ineffiecnt
		for(Road e:edges) {//We do not know if it exists yet
		//tail.edges().indexOf(e.findEdge(tail,tip));
		if (e.findEdge(tail, tip)!=null){//Found it
			if (e.findEdge(tail,tip).pair()!=null) {
				tip.edges().remove(tip.edges().indexOf(e.findEdge(tip,tail)));
				verticesRemove(tip,e.findEdge(tip, tail),tail);//Remove for tip
				verticesRemove(tail,e.findEdge(tip, tail),tail);//Remove pair for tail
			}//End if to remove pair
			tail.edges().remove(tail.edges().indexOf(e.findEdge(tail,tip)));
			verticesRemove(tip,e.findEdge(tail, tip),tip);//Remove for tip
			verticesRemove(tail,e.findEdge(tail, tip),tip);//Remove pair for tail
		}//End if
		
		}//End for
	}//End Method
	
	private void removeEdge(Road e,Village target) {
		if (e!=null){
			System.out.println(e);
			if (e.pair()!=null) {
				e.tip().edges().remove(e.tip().edges().indexOf(e.findEdge(e.tip(),e.tail())));
				edges.remove(e.pair());
				verticesRemove(e.tip(),e.pair(),target);//Remove for tip
				verticesRemove(e.tail(),e.pair(),target);//Remove pair for tail
			}//End if to remove pair
			
			//if (e.findEdge(e.tail(),e.tip()).tip().edges().size()==1 && e.findEdge(e.tail(),e.tip()).tip().edges().contains(e)) {
			//	System.out.println("Final edge before"+ e.tip()+"Becomes an island");
			//}
			e.tail().edges().remove(e.tail().edges().indexOf(e.findEdge(e.tail(),e.tip())));
			edges.remove(e);
			verticesRemove(e.tip(),e,target);//Remove for tip
			verticesRemove(e.tail(),e,target);//Remove pair for tail
			System.out.println("Done: "+edges);
		}//End if
	}//End Method
	
	public void addVertex(String v){
		if ((stVertices.containsKey(v))) {
			System.out.println("The entered Villages does not exist.");
			return;
		}//End if
		
		Village vill = new Village(v);
		verticesAdd(vill,null);
		stVertices.put(v,vill);
	}
	
	public void removeVertex(String v){
		Village vert=null;
		
		//Check that entered vertices exist
		if (!(stVertices.containsKey(v))) {
			System.out.println("The entered Villages does not exist.");
			return;
		}//end if
				
		vert= stVertices.get(v);
		
		removeVertex(vert);
	}//End remove Vertex
	
	private void removeVertex(Village v){
		System.out.println("Linked verts" + vertices.get(v));
		/**
		for(int i=0;i<v.edges().size();i++) {
			removeEdge(v.edges().get(i));
		}//End for
		int j=0;
		while (j<edges.size()){
			if(edges.get(j).tip()==v){
				removeEdge(edges.get(j));
				j--;//refreses for loop to prevent skipping after deletion
			}//End if
			j++;
			System.out.println(j +"Edges size: "+ edges.size());
		}//end while
		*/
		LinkedList<Road> roads = vertices.get(v);
		
		while(0< roads.size()){
			removeEdge(roads.get(0),v);
			
			System.out.println("Linked verts"+roads+ "\nroads="+roads.size());
			System.out.println(0<roads.size());
		}//end for
		System.out.println("while done");
		/*for (int j=0;j<edges.size();j++) {
			if(edges.get(j).tip()==v){
				removeEdge(edges.get(j));
				j--;//refreses for loop to prevent skipping after deletion
			}//End if
		}//End for
		*/
		
		stVertices.remove(v.toString());
		
		v=null;
	}//End remove Vertex
	
	/**
	 * Gets the Max Flow to a vertex given a starting vertex.
	 * Calls calculation method.
	 * 
	 * @param start Origin Village
	 * @param end Destination Village
	 * @return A number with the maximum flow between those points.
	 */
	public int maxFlow(Object start,Object end) {
		//Cast the objects to strings
		start=start.toString();
		end=end.toString();
		
		//Create a flow and get its flow, then pass that into finding the max flow
		HashMap<Road,Integer> flow = getFlow(vertices().get(start),vertices().get(end));
		return maxFlow(flow, stVertices.get(start));
	}//End maxFlow method
	
	/**
	 * Uses a search and gets the shortest path between two vertices.
	 * @param start Origin Village
	 * @param end Destination Village
	 * @return A List of edges to follow
	 */
	public LinkedList<Road> getPath(Object start,Object end) {
		//Cast the objects to strings
		start=start.toString();
		end=end.toString();
		
		//Create a dummy flow with dummy values for edges
		HashMap<Road,Integer> dummyFlow = new HashMap<Road,Integer>();
		for(Road e:edges) {
			dummyFlow.put(e,0);
		}//End for
		
		//Get the shortest path using Breadth First Search
		LinkedList<Road> path = bfSearch(vertices().get(start),vertices().get(end),dummyFlow);
		
		//Tell user that there is no path if one could not be found
		if (path==null) {
			System.out.println("Sorry there is no path between "+start+" and "+ end);
			return path;
		} else {
		return bfSearch(vertices().get(start),vertices().get(end),dummyFlow);
		}//End if else
	}//End getPAth
	
	/**
	 * Follows different paths and calculates
	 * which ones can contribute to max flow without overloading edge capacity.
	 * @param flow Flow for given paths to a point
	 * @param start The vertex at which all the flows begin from
	 * @return A number with the maximum flow from all flows.
	 */
	private int maxFlow(HashMap<Road,Integer> flow, Village start) {
		int maxFlow= 0;//There must be zero flow to begin with
		
		//Add the maximum flow from traversing any edge with a path to maxFlow
		for (int i=0;i<start.edges().size();i++) {
			//System.out.println(flow.get(start.edges().get(i)));
			maxFlow += flow.get(start.edges().get(i));
		}//end for
		return maxFlow;
	}//End maxFlow method
	
	/**
	 * Calculates the flow from all paths from start to end using Ford-Fulkerson Flow Algorithm
	 * @param start Origin Village
	 * @param end Destination Village
	 * @return Paths with corresponding flows
	 */
	private HashMap<Road, Integer> getFlow(Village start,Village end){
		LinkedList<Road> path;//Keeps track of path being followed
		HashMap<Road, Integer> flow = new HashMap<Road,Integer>();//Keeps track of how much capacity has been used
		//if (start.equals(end)) return path;
		
		//Populate flow data structure, assume any path has no flow,
		//because it may not connect with the end vertex
		for(Road e:edges) {
			flow.put(e,0);
		}//End for
		
		//Calculates the flow for any path leading to END using Ford-Fulkerson.
		
		//This BFSearch uses flows to get alternative paths
		//every loop, until it can't find any.
		while ((path = bfSearch(start,end,flow)) != null) {
			//System.out.println(path);
			int minCapacity = Integer.MAX_VALUE;//If its ridiculously high, everything is smaller than it.
			Village lastNode = start;
			
			//Finds bottleneck of flow along the path
			for (Road edge : path) {
				int c;
				if (edge.tail().equals(lastNode)) {
					c=edge.weight() - flow.get(edge);
					lastNode=edge.tip();
				} else {
					c=flow.get(edge);
					lastNode= edge.tail();
				}//end if else
				if (c<minCapacity) {
					minCapacity=c;
				}//end if
			}//End for
			
			//Places the flow associated with an edge coming off of the START in HashMap flow
			//this is where the algo optimizes from the one in class
			lastNode = start;
			for (Road edge : path) {
				if (edge.tail().equals(lastNode)) {
					//flow is incremented in edges returning to start
					flow.put(edge, flow.get(edge) + minCapacity);
					lastNode = edge.tip();
				}//End if
				else {
					//flow is removed from every other edge
					flow.put(edge, flow.get(edge) - minCapacity);
					lastNode = edge.tail();
				}//end if else
			}//End for
			//System.out.println(flow.get(path.get(0))+" Max flow in path: "+path);
		}//End while
		return flow;
	}//End getPath
	
	/**
	 * Breadth First Search from vertex Start to Village End.
	 * @param start Origin Village
	 * @param end Destination Village
	 * @param flow Used to target path to search, useful for finding alternate paths
	 * @return A path of edges from Village Start to End
	 */
	private LinkedList<Road> bfSearch(Village start,Village end, HashMap<Road,Integer> flow) {
		HashMap<Village,Road> parent = new HashMap<Village,Road>();//For recreating graph as it searches
		LinkedList<Village> siblings = new LinkedList<Village>();//For storing where its been
		
		//Enter in the start
		parent.put(start, null);
		siblings.add(start);
		
		//Travels until it can search no more, all label is used when end is found
		all: while(!siblings.isEmpty()) {
			LinkedList<Village> newSibling = new LinkedList<Village>();
			for(Village vertexID : siblings) {
				for(int i=0;i<vertexID.edges().size();i++){
					Road e = vertexID.edges().get(i);
			
					if (e.tail().equals(vertexID) && !parent.containsKey(e.tip()) && flow.get(e) < e.weight()) {
						parent.put(e.tip(), e);
						if (e.tip().equals(end)) {
							break all;
						}//End if
						newSibling.add(e.tip());
					}//End if
					else if (e.tip().equals(vertexID) && !parent.containsKey(e.tail()) && flow.get(e) > 0) {
						parent.put(e.tail(), e);
						if (e.tail().equals(end)) {
							break all;
						}//end if
						newSibling.add(e.tail());
					}//end else if
				}//End for
			}///End for
			
			//Stores where it traveled
			siblings = newSibling;
		}//End while
		
		if (siblings.isEmpty()) {
			return null;
		}//End if
		
		//Recreates the paths and stores them 
		Village node = end;
		LinkedList<Road> path = new LinkedList<Road>();
		while (!node.equals(start)) {
			Road e = parent.get(node);
			path.addFirst(e);
			if (e.tail().equals(node)) {
				node= e.tip();
			} else {
				node= e.tail();
			}//End if else
		}//End while
		
		return path;
	}//End BFSearch
	/**
	 * 
	 * @return Copies graph and turns directed edges into undirected edges
	 */
	public Graph toUndirected(){
		Graph undirected = new Graph(stVertices,vertices,edges);//Copy digraph
		
		//Adds reverse edges to graph
		for (Road e:edges) {
			Village tip=e.tail();
			Village tail=e.tip();
			int weight=e.weight();
			undirected.addEdge(tip, tail, weight,e);
		}//end For
		
		return undirected;
	}//end to undirected
	
	public Graph kruskal() {
		//Graph udg=toUndirected();
		
		LinkedList<Road> sorted = new LinkedList<Road>(edges);
		Collections.sort(sorted);
		HashMap<String,Village> cVertices=new HashMap<String,Village>(stVertices);
		HashMap<Village,Set<Village>> forest = new HashMap<Village,Set<Village>>();
		
		for (Village v:cVertices.values()) {
			Set<Village> vs = new HashSet<Village>();
			vs.add(v);
			forest.put(v, vs);
		}//Populate forest
		
		Graph mst = new Graph(cVertices,new HashMap<Village, LinkedList<Road>>(),new LinkedList<Road>());
		
		while(true) {
			Road temp=sorted.remove(0);
			
			Set<Village> visted1 = forest.get(temp.tail());
			Set<Village> visted2 = forest.get(temp.tip());
			if (visted1.equals(visted2)){
				continue;
			}//End if
			mst.addEdge(temp);
			visted1.addAll(visted2);
			for (Village v:visted1) {
				forest.put(v, visted1);
			}//End for
			if (visted1.size()==cVertices.size()) {
				break;
			}//End if
		}//End While
		return mst;
	}//End Kruskal
	
	private LinkedList<Road> quickSort(LinkedList<Road> list){
		int pivot = list.size()/2;
		System.out.println(pivot);
		LinkedList<Road> less,more;
		less=new LinkedList<Road>();
		more=new LinkedList<Road>();
		if (list.size()==1) {
			return list;
		}
		for(Road e:edges) {
			if (e.compareTo(list.get(pivot))<0) {
				less.add(e);
			}//End if
			else {
				more.add(e);
			}//End else
		}//end for
		less= quickSort(less);
		more=quickSort(more);
		list=addLists(less,more);
		return list;
	}//End method
	
	private LinkedList<Road> addLists(LinkedList<Road> one, LinkedList<Road> two) {
		for (int i = 0;i<two.size();i++) {
			one.add(two.get(i));//add it up
		}//end for
		return one;
	}//End method
	
	public int size(){
		return stVertices.size();
	}
	
	/**
	 * 
	 * @return List of vertices in graph
	 */
	public HashMap<String,Village> vertices(){
		return stVertices;
	}//End getstVertacies
	
	public HashMap<Village,LinkedList<Road>> verticesConnections(){
		return vertices;
	}//End getVertices
	
	/**
	 * 
	 * @return A list of edges in graph
	 */
	public LinkedList<Road> edges(){
		return edges;
	}//End getEdges
	
	/**
	 * For printing a graph
	 */
	public String toString() {
		String s = "Graph:\n";
		 Iterator<Village> v = stVertices.values().iterator();
		for(int i=0;i<stVertices.size();i++){
			Village vertex=v.next();
			s+=vertex+": ";
			for(int j=0;j<vertex.edges().size();j++)
					s+=vertex.edges().get(j).tip()+" ";
			s+="\n";
		}//End for
		return s;
	}//End toString
}
