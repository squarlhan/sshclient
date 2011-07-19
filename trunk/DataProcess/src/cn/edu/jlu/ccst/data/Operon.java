package cn.edu.jlu.ccst.data;

import java.util.Set;

public class Operon {

	private String id;
	private Set<String> geneid;
	private String pm;
	
	
	
	public Operon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public Operon(String id, Set<String> geneid) {
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
