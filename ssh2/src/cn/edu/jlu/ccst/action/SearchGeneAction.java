package cn.edu.jlu.ccst.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.jlu.ccst.service.GetGene;
import cn.edu.jlu.ccst.service.GetTaxonomy;
import cn.edu.jlu.ccst.service.SimpleExample;

@Component("searchGeneAction")
public class SearchGeneAction extends ActionSupport {

	private SimpleExample se;
	private GetTaxonomy gt;
	private GetGene gg;
	private String tip;
	private List<String> resultlist_Gene;
	private List<String> resultlist_Gene_name;
	private List<String> resultlist_Gene_id;

	private String Taxonomy;

	public String getTaxonomy() {
		return Taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		Taxonomy = taxonomy;
	}

	public String SearchGene() throws ClassNotFoundException {

		List<String> Gene = gg.Query_GeneByTax(Taxonomy);
		if (Gene.size() == 0) {
			setTip("There is no Gene for this taxonomy!");
			return ERROR;
		} else {
			setResultlist_Gene(Gene);
			setResultlist_Gene_name(gg.Query_Gene_name(Gene));
			setResultlist_Gene_id(gg.Query_Gene_id(Gene));
			return SUCCESS;
		}
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

	public GetTaxonomy getGt() {
		return gt;
	}

	@Resource
	public void setGt(GetTaxonomy gt) {
		this.gt = gt;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	public void setResultlist_Gene(List<String> resultlist_Gene) {
		this.resultlist_Gene = resultlist_Gene;
	}

	public List<String> getResultlist_Gene() {
		return resultlist_Gene;
	}

	public void setResultlist_Gene_name(List<String> resultlist_Gene_name) {
		this.resultlist_Gene_name = resultlist_Gene_name;
	}

	public List<String> getResultlist_Gene_name() {
		return resultlist_Gene_name;
	}

	public List<String> getResultlist_Gene_id() {
		return resultlist_Gene_id;
	}

	public void setResultlist_Gene_id(List<String> resultlist_Gene_id) {
		this.resultlist_Gene_id = resultlist_Gene_id;
	}

	public GetGene getGg() {
		return gg;
	}

	@Resource
	public void setGg(GetGene gg) {
		this.gg = gg;
	}
}
