package cn.edu.jlu.ccst.model;

import java.util.List;

public class Gene {
	
	private String id;
	private String name;
	private GO go;
	private Taxonomy taxonomy;
	private List<mRNA> mrnas;
	private List<Protein> proteins;
	private List<Promoter> promoters;
	
	public Gene(String id, String name, GO go, Taxonomy taxonomy) {
		super();
		this.id = id;
		this.name = name;
		this.go = go;
		this.taxonomy = taxonomy;
	}
	
	
	public Gene() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Gene(String id, String name, GO go,
			Taxonomy taxonomy, List<mRNA> mrnas, List<Protein> proteins,
			List<Promoter> promoters) {
		super();
		this.id = id;
		this.name = name;
		this.go = go;
		this.taxonomy = taxonomy;
		this.mrnas = mrnas;
		this.proteins = proteins;
		this.promoters = promoters;
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
	public GO getGo() {
		return go;
	}
	public void setGo(GO go) {
		this.go = go;
	}
	public Taxonomy getTaxonomy() {
		return taxonomy;
	}
	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}
	public List<mRNA> getMrnas() {
		return mrnas;
	}
	public void setMrnas(List<mRNA> mrnas) {
		this.mrnas = mrnas;
	}
	public List<Protein> getProteins() {
		return proteins;
	}
	public void setProteins(List<Protein> proteins) {
		this.proteins = proteins;
	}
	public List<Promoter> getPromoters() {
		return promoters;
	}
	public void setPromoters(List<Promoter> promoters) {
		this.promoters = promoters;
	}

}
