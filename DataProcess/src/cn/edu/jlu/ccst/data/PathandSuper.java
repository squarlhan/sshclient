package cn.edu.jlu.ccst.data;

import java.util.HashSet;
import java.util.Set;

public class PathandSuper {

	Pathway pathway;
	Set<Supercoiling> supercoilings;
	
	
	
	public PathandSuper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PathandSuper(Pathway pathway) {
		super();
		this.pathway = pathway;
		this.supercoilings = new HashSet();
	}
	public PathandSuper(Pathway pathway, Set<Supercoiling> supercoilings) {
		super();
		this.pathway = pathway;
		this.supercoilings = supercoilings;
	}
	public Pathway getPathway() {
		return pathway;
	}
	public void setPathway(Pathway pathway) {
		this.pathway = pathway;
	}
	public Set<Supercoiling> getSupercoilings() {
		return supercoilings;
	}
	public void setSupercoilings(Set<Supercoiling> supercoilings) {
		this.supercoilings = supercoilings;
	}
	
	public String toString(){
		String re = this.getPathway().getId();
		for(Supercoiling sc : this.getSupercoilings()){
			re = re+" "+sc.getId();
		}
		return re;
	}
	
}
