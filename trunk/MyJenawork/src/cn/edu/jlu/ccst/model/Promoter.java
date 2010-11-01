package cn.edu.jlu.ccst.model;

import java.util.ArrayList;
import java.util.List;

public class Promoter {
	
	private String name;
	private Homology homology;
	private List<Gene> genes;
	private List<Reference> references;
	private List<Resource> resources;
	private List<Keyword> keywords;
	private List<mRNA> mrnas;
	
	
	
	public Promoter() {
		super();
		// TODO Auto-generated constructor stub
		name = "";
		homology = new Homology();
		genes = new ArrayList();
		references = new ArrayList();
		resources = new ArrayList();
		keywords = new ArrayList();
		mrnas = new ArrayList();
	}

	public Promoter(String name, Homology homology, List<Gene> genes,
			List<Reference> references, List<Resource> resources,
			List<Keyword> keywords, List<mRNA> mrnas) {
		super();
		this.name = name;
		this.homology = homology;
		this.genes = genes;
		this.references = references;
		this.resources = resources;
		this.keywords = keywords;
		this.mrnas = mrnas;
	}
	
	public Promoter( String name, Homology homology) {
		super();
		this.name = name;
		this.homology = homology;
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
	public List<Gene> getGenes() {
		return genes;
	}
	public void setGenes(List<Gene> genes) {
		this.genes = genes;
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
