package ga.graph;

public class Edge {
	public Node left;
	public Node right;
	public int id;
	
//	public boolean deleted=false;
	public boolean visited=false;
	
	public Node getOpposite(Node node){
		if(node.id==left.id)
			return right;
		return left;
		
	}
	
	

}
