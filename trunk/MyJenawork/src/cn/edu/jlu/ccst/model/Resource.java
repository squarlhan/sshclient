package cn.edu.jlu.ccst.model;

public class Resource {
	
	private String dataset;
	private String id;
	private String link;
	
	
	public Resource(String dataset, String id, String link) {
		super();
		this.dataset = dataset;
		this.id = id;
		this.link = link;
	}
	
	
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	

}
