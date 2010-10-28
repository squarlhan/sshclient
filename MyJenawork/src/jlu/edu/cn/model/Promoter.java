package jlu.edu.cn.model;

import java.util.List;

public class Promoter {
	
	private String id;
	private String name;
	private Homology homology;
	private Gene gene;
	private List<Reference> references;
	private List<Resource> resources;
	private List<Keyword> keywords;
	private List<mRNA> mrnas;
	
	public Promoter(String id, String name, Homology homology, Gene gene,
			List<Reference> references, List<Resource> resources,
			List<Keyword> keywords, List<mRNA> mrnas) {
		super();
		this.id = id;
		this.name = name;
		this.homology = homology;
		this.gene = gene;
		this.references = references;
		this.resources = resources;
		this.keywords = keywords;
		this.mrnas = mrnas;
	}
	
	public Promoter(String id, String name, Homology homology, Gene gene) {
		super();
		this.id = id;
		this.name = name;
		this.homology = homology;
		this.gene = gene;
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
	public Homology getHomology() {
		return homology;
	}
	public void setHomology(Homology homology) {
		this.homology = homology;
	}
	public Gene getGene() {
		return gene;
	}
	public void setGene(Gene gene) {
		this.gene = gene;
	}
	public List<Reference> getReferences() {
		return references;
	}
	public void setReferences(List<Reference> references) {
		this.references = references;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public List<Keyword> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	public List<mRNA> getMrnas() {
		return mrnas;
	}
	public void setMrnas(List<mRNA> mrnas) {
		this.mrnas = mrnas;
	}
	
	
		

}
