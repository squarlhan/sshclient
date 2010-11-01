package cn.edu.jlu.ccst.model;

public class Homology {

	private String name;
	private String id;
	
	public Homology() {
		super();
		// TODO Auto-generated constructor stub
		name = "";
		id = "";
	}

	public Homology(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
