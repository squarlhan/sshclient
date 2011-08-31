package ga.graph;

import java.util.ArrayList;

public class Node {
	public Edge[] edges;
	public int id;
	
//	public boolean deleted=false;
	public boolean visited=false;
	public ArrayList<Edge> tempEdge=new ArrayList<Edge>();
	
	public void delete(){
		visited=true;
		for(int i=0;i<edges.length;i++){
			edges[i].visited=true;
		}
	}
	
	public void copyEdge(){
		edges=new Edge[tempEdge.size()];
		for(int i=0;i<edges.length;i++){
			edges[i]=tempEdge.get(i);
		}
	}

}
