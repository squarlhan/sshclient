package ga.graph;

public class Graph {
	public Node[] nodes;
	public Edge[] edges;
	
	private boolean findCycle=false;
	
	
	public boolean ifCycleExist(boolean[] list){
		if(list.length!=nodes.length){
			System.out.println("传入序列长度与节点数不符");
			System.exit(0);
		}
		recover();
		initilize(list);
		Node root=findRoot();
		while(!root.visited){
			if(depthFirst(root))
				return true;
			root=findRoot();
			
		}
		return false;
	}
	
	
	private Node findRoot(){
		for(int i=0;i<nodes.length;i++){
			if(!nodes[i].visited)
				return nodes[i];
		}
		return nodes[0];
	}
	private void initilize(boolean[] list){
		for(int i=0;i<list.length;i++){
			if(list[i]){
				nodes[i].delete();
			}
		}
	}
	
	private void recover(){
		for(int i=0;i<nodes.length;i++){
			nodes[i].visited=false;
		}
		for(int i=0;i<edges.length;i++){
			edges[i].visited=false;
		}
	}
	public boolean depthFirst(Node root){
		if(root.visited){
			return true;
		}
		root.visited=true;
		for(int i=0;i<root.edges.length;i++){
			if(!root.edges[i].visited){
				root.edges[i].visited=true;
				findCycle=depthFirst(root.edges[i].getOpposite(root));
				if(findCycle)
					return true;
			}
		}
		return false;
	}

}
