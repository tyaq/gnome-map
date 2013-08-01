package roadMap;


public class Road implements Comparable<Object> {

	private int weight;//Road Weight
	private Village tail;//Start
	private Village tip;//End
	private Road pair;//pair for undirected
	
	/**
	 * 
	 * @param ta Tailing Village
	 * @param ti Tipping Village
	 * @param w Road Weight
	 */
	public Road(Village ta,Village ti,int w,Road p){
		weight = w;
		tail = ta;
		tip = ti;
		pair=p;
		ta.add(this);//Tell the tail that it can follow this edge
		//If you wanted undirected graph you could create an opposite edge and tell the tip about it.
	}//End Constructor
	
	public int compareTo(Object o) {
        Road e1 = (Road)o;
        if(e1.weight==this.weight)
            return 0;
        return e1.weight < this.weight ? 1 : -1;
    }//End Compare
	
	public void setWeight(int w){
		weight=w;
	}//End setWeight
	
	public int weight(){
		return weight;
	}//End getWeight
	
	public Village tip(){
		return tip;
	}
	
	public Village tail(){
		return tail;
	}
	
	public Road pair(){
		return pair;
	}//end getPair
	
	public void setPair(Road p){
		pair=p;
	}
	
	public Road findEdgeToTip(Village ti){
		if (tip==ti) {
			return this;
		}//end if
		return null;
		
	}
	
	public Road findEdge(Village tailV,Village tipV) {
		if (tip==tipV && tail==tailV) {
			return this;
		}//end if
		else return null;
	}
	
	public String toString(){
		return "["+tail+"->"+tip+" Weight:"+weight+"]";
	}
}
