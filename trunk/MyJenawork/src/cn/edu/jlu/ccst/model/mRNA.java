package cn.edu.jlu.ccst.model;

public class mRNA {
	
	private String id;
	private String name;
	private Protein protein;
	
	public mRNA(String id, String name, Protein protein) {
		super();
		this.id = id;
		this.name = name;
		this.protein = protein;
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
	public Protein getProtein() {
		return protein;
	}
	public void setProtein(Protein protein) {
		this.protein = protein;
	}
	

}
