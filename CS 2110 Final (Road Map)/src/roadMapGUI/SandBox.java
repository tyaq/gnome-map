package roadMapGUI;

import roadMap.Graph;

public class SandBox {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		
		g.addEdge("A", "B", "1000");
		g.addEdge("A", "C", "1000");
		g.addEdge("B", "C", "1");
		g.addEdge("B", "D", "1000");
		g.addEdge("C", "Island", "2");
		g.addEdge("C", "D", "1000");
		
		System.out.println(g);
		
		System.out.println(g.kruskal().edges());
		
		g.removeVertex("C");
		
		System.out.println(g);
		
		System.out.println(g.maxFlow("A", "D"));
		
		System.out.println(g.kruskal().edges());
	}
}
