package cn.edu.jlu.ccst.model;

public class Taxonomy {
	
	public Taxonomy() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String id;
	private String name;
	public Taxonomy(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
