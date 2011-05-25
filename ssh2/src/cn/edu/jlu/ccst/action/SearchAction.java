package cn.edu.jlu.ccst.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.model.User;
import cn.edu.jlu.ccst.service.GetGene;
import cn.edu.jlu.ccst.service.GetTaxonomy;
import cn.edu.jlu.ccst.service.SimpleExample;
import cn.edu.jlu.ccst.service.UserService;

import com.hp.hpl.jena.ontology.OntModel;
import com.opensymphony.xwork2.ActionSupport;

@Component("searchAction")
public class SearchAction extends ActionSupport {
	public static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	public static final String DB_USER = "root";
	public static final String DB_PASSWD = "root";
	public static final String DB_TYPE = "Mysql";
	public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String MODEL_NAME = "user";
	private String Promoter_name;
	private String Gene_name;
	private String Taxonomy_name;
	private List<String> Keywords;
	private OntModel onmo;

	private String tip;

	private UserService userService;
	private User user;
	private SimpleExample se;
	private GetTaxonomy gt;
	private GetGene gg;

	private List<String> resultlist_Taxonomy;
	private List<String> resultlist_Tax_label;
	private String resultlist_Tax_id;
	//private List<String> result;
	private List<User> userlist;

	public String Search() throws ClassNotFoundException {
		List<String> Taxonomy = gt.Query_Taxonomy(Taxonomy_name);
		if (Taxonomy.size() == 0) {
			tip = "wrong Taxonomy name!";
			return ERROR;
		} else {
			setResultlist_Taxonomy(Taxonomy);
			List<String> name = gt.Query_Taxonomy_label(Taxonomy);
			setResultlist_Tax_label(name);		
			return SUCCESS;
		}

	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public SimpleExample getSe() {
		return se;
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPromoter_name() {
		return Promoter_name;
	}

	public void setPromoter_name(String Promoter_name) {
		this.Promoter_name = Promoter_name;
	}

	public void setGene_name(String gene_name) {
		Gene_name = gene_name;
	}

	public String getGene_name() {
		return Gene_name;
	}

	public void setTaxonomy_name(String taxonomy_name) {
		Taxonomy_name = taxonomy_name;
	}

	public String getTaxonomy_name() {
		return Taxonomy_name;
	}

	public void setResultlist_Taxonomy(List<String> resultlist_Taxonomy) {
		this.resultlist_Taxonomy = resultlist_Taxonomy;
	}

	public List<String> getResultlist_Taxonomy() {
		return resultlist_Taxonomy;
	}

	public void setResultlist_Tax_label(List<String> resultlist_Tax_label) {
		this.resultlist_Tax_label = resultlist_Tax_label;
	}

	public List<String> getResultlist_Tax_label() {
		return resultlist_Tax_label;
	}


	public List<String> getKeywords() {
		return Keywords;
	}

	public void setKeywords(List<String> keywords) {
		Keywords = keywords;
	}

	public void setOnmo(OntModel onmo) {
		this.onmo = onmo;
	}

	public OntModel getOnmo() {
		return onmo;
	}

	public GetTaxonomy getGt() {
		return gt;
	}

	@Resource
	public void setGt(GetTaxonomy gt) {
		this.gt = gt;
	}

	public void setResultlist_Tax_id(String resultlist_Tax_id) {
		this.resultlist_Tax_id = resultlist_Tax_id;
	}

	public String getResultlist_Tax_id() {
		return resultlist_Tax_id;
	}

	public GetGene getGg() {
		return gg;
	}

	@Resource
	public void setGg(GetGene gg) {
		this.gg = gg;
	}


}
