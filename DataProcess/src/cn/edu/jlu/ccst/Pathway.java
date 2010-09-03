package cn.edu.jlu.ccst;

import java.util.Set;

public class Pathway {

	private String id;
	private Set<String> geneid;
	
	
	
	public Pathway() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pathway(String id, Set<String> geneid) {
		super();
		this.id = id;
		this.geneid = geneid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<String> getGeneid() {
		return geneid;
	}
	public void setGeneid(Set<String> geneid) {
		this.geneid = geneid;
	}
	
}
