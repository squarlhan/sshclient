package ga.graph;

import ga.graph.modeltool.BenchMark;

import java.util.ArrayList;


public class GraphFactory {
	
	public static Graph createGraph(String fileName){
		BenchMark bm=BenchMark.createBenchMark(fileName);
		Graph graph=new Graph();

		int varnum = bm.varNum;
		int cstnum = bm.conNum;
		graph.nodes = new Node[varnum];
		graph.edges = new Edge[cstnum];
		for (int i = 0; i < varnum; i++) {
			graph.nodes[i] = new Node();
			graph.nodes[i].id = bm.variables[i].id;
			
		}
		for (int i = 0; i < cstnum; i++) {
			graph.edges[i] = new Edge();
			graph.edges[i].id=bm.constraints[i].id;
			graph.edges[i].left=graph.nodes[bm.constraints[i].varIds[0]];
			graph.nodes[bm.constraints[i].varIds[0]].tempEdge.add(graph.edges[i]);
			graph.edges[i].right=graph.nodes[bm.constraints[i].varIds[1]];
			graph.nodes[bm.constraints[i].varIds[1]].tempEdge.add(graph.edges[i]);
		}
		for (int i = 0; i < graph.nodes.length; i++) {
			graph.nodes[i].copyEdge();
		}
		
		return graph;
	}

}
