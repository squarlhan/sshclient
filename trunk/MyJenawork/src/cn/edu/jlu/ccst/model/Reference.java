package cn.edu.jlu.ccst.model;

public class Reference {
	
	private String pubmed;
	private String auther;
	private String title;
	private String location;
	
	public Reference() {
		super();
		// TODO Auto-generated constructor stub
		pubmed = "";
		auther = "";
		title = "";
		location = "";
	}
		
	public Reference(String pubmed, String auther, String title, String location) {
		super();
		this.pubmed = pubmed;
		this.auther = auther;
		this.title = title;
		this.location = location;
	}
	public String getPubmed() {
		return pubmed;
	}
	public void setPubmed(String pubmed) {
		this.pubmed = pubmed;
	}
	public String getAuther() {
		return auther;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
