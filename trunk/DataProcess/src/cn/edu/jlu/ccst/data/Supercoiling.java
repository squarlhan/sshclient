package cn.edu.jlu.ccst.data;

import java.util.HashSet;
import java.util.Set;

public class Supercoiling {
	
	int id;
	Set<String> operons ;
	
	
	
	public Supercoiling() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Supercoiling(int id) {
		super();
		this.id = id;
		this.operons = new HashSet();
	}
	public Supercoiling(int id, Set<String> operons) {
		super();
		this.id = id;
		this.operons = operons;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<String> getOperons() {
		return operons;
	}
	public void setOperons(Set<String> operons) {
		this.operons = operons;
	}
	

	
}
