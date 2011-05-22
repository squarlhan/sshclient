package cn.edu.jlu.ccst.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.model.User;
import cn.edu.jlu.ccst.service.GetGene;
import cn.edu.jlu.ccst.service.GetTaxonomy;
import cn.edu.jlu.ccst.service.SimpleExample;
import cn.edu.jlu.ccst.service.UserService;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
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

	private List<String> resultlist_Gene;
	private List<String> resultlist_Gene_id;
	private List<String> resultlist_Gene_name;
	private List<String> resultlist_Taxonomy;
	private List<String> resultlist_Tax_label;
	private String resultlist_Tax_id;
	private List<String> resultlist_Go;
	private List<String> resultlist_Go_item;
	private List<String> resultlist_Protein;
	private List<String> resultlist_Protein_name;
	private List<String> resultlist_Protein_id;
	private List<String> resultlist_mRNA;
	private List<String> resultlist_mRNA_name;
	private List<String> resultlist_mRNA_id;
	private List<String> resultlist_Promoter;
	private List<String> resultlist_Promoter_name;
	private List<String> resultlist_Keyword;
	private List<String> resultlist_Keyword_keywords;
	private List<String> resultlist_Resource;
	private List<String> resultlist_Resource_name;
	private List<String> resultlist_Resource_id;
	private List<String> resultlist_Resource_link;
	private List<String> resultlist_Homology_name;
	private List<String> resultlist_Homology;
	private List<String> resultlist_Reference;
	private List<String> resultlist_Reference_id;
	private List<String> resultlist_Reference_author;
	private List<String> resultlist_Reference_title;
	private List<String> resultlist_Reference_location;
	private List<User> userlist;

	/*
	 * public String Search() throws ClassNotFoundException { List<String>
	 * Taxonomy = se.Query_Taxonomy(Taxonomy_name); if (Taxonomy.size() == 0) {
	 * tip = "wrong Taxonomy name!"; return ERROR; } else { //
	 * 得到Taxonomy列表resultlist_Taxonomy setResultlist_Taxonomy(Taxonomy); //
	 * 得到Tax_label列表resultlist_Tax_label
	 * setResultlist_Tax_label(se.Query_Tax_label(Taxonomy)); //
	 * 得到Tax_id列表resultlist_Tax_id
	 * setResultlist_Tax_id(se.Query_Tax_id(Taxonomy)); // 得到Gene查询结果
	 * List<String> Gene = se.Query_GeneByTax(Taxonomy); if (Gene.size() == 0) {
	 * Gene.add("No Gene"); setResultlist_Gene(Gene); } else {
	 * setResultlist_Gene(Gene); // 得到Go查询结果 List<String> Go =
	 * se.Query_Go(Gene); if (Go.size() == 0) { Go.add("No Go");
	 * setResultlist_Go(Go); } else { // 得到Go列表Resultlist_Go
	 * setResultlist_Go(Go); // 得到GO_Item列表resultlist_Go_item
	 * setResultlist_Go_item(se.Query_Go_item(Go)); } // 得到Protein查询结果
	 * List<String> Protein = se.Query_Protein(Gene); if (Protein.size() == 0) {
	 * Protein.add("No Protein"); setResultlist_Protein(Protein); } else { //
	 * 得到Protein列表resultlist_Protein setResultlist_Protein(Protein); //
	 * 得到Protein_name列表resultlist_Protein_name
	 * setResultlist_Protein_name(se.Query_Protein_name(Protein)); } //
	 * 得到mRNA查询结果 List<String> mRNA = se.Query_mRNA(Gene); if (mRNA.size()==0) {
	 * mRNA.add("No mRNA"); setResultlist_mRNA(mRNA); } else { //
	 * 得到mRNA列表resultlist_mRNA setResultlist_mRNA(mRNA); //
	 * 得到mRNA_Name列表resultlist_mRNA_name
	 * setResultlist_mRNA_name(se.Query_mRNA_name(mRNA)); //
	 * 得到mRNA_Identifier列表resultlist_mRNA_id
	 * //setResultlist_mRNA_id(se.Query_mRNA_id(mRNA)); } // 得到Promoter查询结果
	 * List<String> Promoter = se.Query_Promoter(Gene); if (Promoter.size()== 0)
	 * { Promoter.add("No Promoter"); setResultlist_Promoter(Promoter); } else {
	 * // 得到Promoter列表resultlist_Promoter setResultlist_Promoter(Promoter); //
	 * 得到Promoter_Name列表resultlist_Promoter_name
	 * setResultlist_Promoter_name(se.Query_Promoter_name(Promoter));
	 * 
	 * // 得到Keyword查询结果 List<String> Keyword = se.Query_Keyword(Promoter); if
	 * (Keyword.size() == 0) { Keyword.add("No Keyword");
	 * setResultlist_Keyword(Keyword); } else { // 得到Keyword列表resultlist_Keyword
	 * setResultlist_Keyword(Keyword); //
	 * 得到Keyword_Keywords列表resultlist_Keyword_keywords
	 * //setResultlist_Keyword_keywords(se.Query_Keyword_Keywords(Keyword)); }
	 * // 得到Resource查询结果 List<String> Resource = se.Query_Resource(Promoter); if
	 * (Resource.size() == 0) { Resource.add("No Resouce");
	 * setResultlist_Resource(Resource); } else { //
	 * 得到Resource列表resultlist_Resource setResultlist_Resource(Resource); //
	 * 得到Resource_Name列表resultlist_Resource_name setResultlist_Resource_name(se
	 * .Query_Resource_name(Resource)); // 得到Resource_id列表resultlist_Resource_id
	 * setResultlist_Resource_id(se.Query_Resource_id(Resource)); //
	 * 得到Resource_link列表resultlist_Resource_link setResultlist_Resource_link(se
	 * .Query_Resource_link(Resource)); } // 得到Homology查询结果 List<String>
	 * Homology = se.Query_Homology(Promoter); if (Homology.size() == 0) {
	 * Homology.add("No Homology"); setResultlist_Homology(Homology); } else {
	 * // 得到Homology列表resultlist_Homology setResultlist_Homology(Homology); //
	 * 得到Homology_Name列表resultlist_Homology_name setResultlist_Homology_name(se
	 * .Query_Homology_name(Homology)); } // 得到Reference查询结果 List<String>
	 * Reference = se.Query_Reference(Promoter); if (Reference.size() == 0) {
	 * Reference.add("No Reference"); setResultlist_Reference(Reference); } else
	 * { // 得到Reference列表resultlist_Reference
	 * setResultlist_Reference(Reference); //
	 * 得到Reference_id列表resultlist_Reference_id
	 * setResultlist_Reference_id(se.Query_Reference_id(Reference)); //
	 * 得到Reference_author列表resultlist_Reference_author
	 * setResultlist_Reference_author(se .Query_Reference_author(Reference)); //
	 * 得到Reference_title列表resultlist_Reference_title
	 * setResultlist_Reference_title(se .Query_Reference_title(Reference)); //
	 * 得到Reference_location列表resultlist_Reference_location
	 * setResultlist_Reference_location(se
	 * .Query_Reference_location(Reference)); } }//Promoter }//Gene }return
	 * SUCCESS; }//Taxonomy }
	 */
	public String Search() throws ClassNotFoundException {
		List<String> Taxonomy = gt.Query_Taxonomy(Taxonomy_name);
		if (Taxonomy.size() == 0) {
			tip = "wrong Taxonomy name!";
			return ERROR;
		} else {
			List<String> name = gt.Query_Taxonomy_label(Taxonomy);
			List<String> Gene = gg.Query_GeneByTax(Taxonomy);
			setResultlist_Gene(Gene);
			setResultlist_Gene_name(gg.Query_Gene_name(Gene));
			// String gene=Gene.toString();
			// List<String> resultlist=new ArrayList();
			// resultlist.add(gene);
			// setResultlist_Gene(resultlist);

			/*
			 * if(Gene.size() == 0){ tip="No Gene!"; return ERROR; }else{
			 * setResultlist_Gene(Gene); List<String> Gene_name=new ArrayList();
			 * int size = Gene.size(); int i = 0; while(i<size){ String
			 * gene=Gene.get(i); List<String> gene_name =
			 * se.Query_Gene_name(gene); if(gene_name.size()==0){
			 * Gene_name.add(i, ""); }else if(gene_name.size()==1){ String
			 * name=gene_name.toString(); Gene_name.add(i,name); }else{ String
			 * name=gene_name.toString(); Gene_name.add(i,name); }
			 * 
			 * }
			 */
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

	public void setResultlist_Gene(List<String> resultlist_Gene) {
		this.resultlist_Gene = resultlist_Gene;
	}

	public List<String> getResultlist_Gene() {
		return resultlist_Gene;
	}

	public void setResultlist_Gene_id(List<String> resultlist_Gene_id) {
		this.resultlist_Gene_id = resultlist_Gene_id;
	}

	public List<String> getResultlist_Gene_id() {
		return resultlist_Gene_id;
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

	public void setResultlist_Go(List<String> resultlist_Go) {
		this.resultlist_Go = resultlist_Go;
	}

	public List<String> getResultlist_Go() {
		return resultlist_Go;
	}

	public void setResultlist_Go_item(List<String> resultlist_Go_item) {
		this.resultlist_Go_item = resultlist_Go_item;
	}

	public List<String> getResultlist_Go_item() {
		return resultlist_Go_item;
	}

	public void setResultlist_Protein(List<String> resultlist_Protein) {
		this.resultlist_Protein = resultlist_Protein;
	}

	public List<String> getResultlist_Protein() {
		return resultlist_Protein;
	}

	public void setResultlist_Protein_name(List<String> resultlist_Protein_name) {
		this.resultlist_Protein_name = resultlist_Protein_name;
	}

	public List<String> getResultlist_Protein_name() {
		return resultlist_Protein_name;
	}

	public void setResultlist_Protein_id(List<String> resultlist_Protein_id) {
		this.resultlist_Protein_id = resultlist_Protein_id;
	}

	public List<String> getResultlist_Protein_id() {
		return resultlist_Protein_id;
	}

	public List<String> getResultlist_mRNA() {
		return resultlist_mRNA;
	}

	public void setResultlist_mRNA(List<String> resultlist_mRNA) {
		this.resultlist_mRNA = resultlist_mRNA;
	}

	public void setResultlist_mRNA_name(List<String> resultlist_mRNA_name) {
		this.resultlist_mRNA_name = resultlist_mRNA_name;
	}

	public List<String> getResultlist_mRNA_name() {
		return resultlist_mRNA_name;
	}

	public void setResultlist_mRNA_id(List<String> resultlist_mRNA_id) {
		this.resultlist_mRNA_id = resultlist_mRNA_id;
	}

	public List<String> getResultlist_mRNA_id() {
		return resultlist_mRNA_id;
	}

	public void setResultlist_Promoter_name(
			List<String> resultlist_Promoter_name) {
		this.resultlist_Promoter_name = resultlist_Promoter_name;
	}

	public List<String> getResultlist_Promoter_name() {
		return resultlist_Promoter_name;
	}

	public void setResultlist_Keyword(List<String> resultlist_Keyword) {
		this.resultlist_Keyword = resultlist_Keyword;
	}

	public List<String> getResultlist_Keyword() {
		return resultlist_Keyword;
	}

	public void setResultlist_Keyword_keywords(
			List<String> resultlist_Keyword_keywords) {
		this.resultlist_Keyword_keywords = resultlist_Keyword_keywords;
	}

	public List<String> getResultlist_Keyword_keywords() {
		return resultlist_Keyword_keywords;
	}

	public void setResultlist_Resource(List<String> resultlist_Resource) {
		this.resultlist_Resource = resultlist_Resource;
	}

	public List<String> getResultlist_Resource() {
		return resultlist_Resource;
	}

	public void setResultlist_Resource_name(
			List<String> resultlist_Resource_name) {
		this.resultlist_Resource_name = resultlist_Resource_name;
	}

	public List<String> getResultlist_Resource_name() {
		return resultlist_Resource_name;
	}

	public void setResultlist_Resource_id(List<String> resultlist_Resource_id) {
		this.resultlist_Resource_id = resultlist_Resource_id;
	}

	public List<String> getResultlist_Resource_id() {
		return resultlist_Resource_id;
	}

	public void setResultlist_Resource_link(
			List<String> resultlist_Resource_link) {
		this.resultlist_Resource_link = resultlist_Resource_link;
	}

	public List<String> getResultlist_Resource_link() {
		return resultlist_Resource_link;
	}

	public void setResultlist_Homology_name(
			List<String> resultlist_Homology_name) {
		this.resultlist_Homology_name = resultlist_Homology_name;
	}

	public List<String> getResultlist_Homology_name() {
		return resultlist_Homology_name;
	}

	public void setResultlist_Homology(List<String> resultlist_Homology) {
		this.resultlist_Homology = resultlist_Homology;
	}

	public List<String> getResultlist_Homology() {
		return resultlist_Homology;
	}

	public void setResultlist_Reference(List<String> resultlist_Reference) {
		this.resultlist_Reference = resultlist_Reference;
	}

	public List<String> getResultlist_Reference() {
		return resultlist_Reference;
	}

	public void setResultlist_Reference_id(List<String> resultlist_Reference_id) {
		this.resultlist_Reference_id = resultlist_Reference_id;
	}

	public List<String> getResultlist_Reference_id() {
		return resultlist_Reference_id;
	}

	public void setResultlist_Reference_author(
			List<String> resultlist_Reference_author) {
		this.resultlist_Reference_author = resultlist_Reference_author;
	}

	public List<String> getResultlist_Reference_author() {
		return resultlist_Reference_author;
	}

	public void setResultlist_Reference_title(
			List<String> resultlist_Reference_title) {
		this.resultlist_Reference_title = resultlist_Reference_title;
	}

	public List<String> getResultlist_Reference_title() {
		return resultlist_Reference_title;
	}

	public void setResultlist_Reference_location(
			List<String> resultlist_Reference_location) {
		this.resultlist_Reference_location = resultlist_Reference_location;
	}

	public List<String> getResultlist_Reference_location() {
		return resultlist_Reference_location;
	}

	public List<String> getResultlist_Promoter() {
		return resultlist_Promoter;
	}

	public void setResultlist_Promoter(List<String> resultlist_Promoter) {
		this.resultlist_Promoter = resultlist_Promoter;
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

	public void setResultlist_Gene_name(List<String> resultlist_Gene_name) {
		this.resultlist_Gene_name = resultlist_Gene_name;
	}

	public List<String> getResultlist_Gene_name() {
		return resultlist_Gene_name;
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
