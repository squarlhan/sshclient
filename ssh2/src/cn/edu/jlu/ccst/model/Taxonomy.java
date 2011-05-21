package cn.edu.jlu.ccst.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component("taxonomy")
public class Taxonomy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -558502523424301952L;
	@Id
	@GeneratedValue
	private String ID;
	private String Name;
	private String Unique;
	private int tid;
	
	public void setName(String name) {
		Name = name;
	}
	public String getName() {
		return Name;
	}
	public void setUnique(String unique) {
		Unique = unique;
	}
	public String getUnique() {
		return Unique;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getID() {
		return ID;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getTid() {
		return tid;
	}
	
	
	
	

}
