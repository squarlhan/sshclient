package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.jlu.ccst.model.Taxonomy;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gt")
@Transactional
public class GetTaxonomy {
	private SimpleExample se;
	private EntityManager em;
	private Taxonomy taxonomy;

	// keyword----id (模糊查询)返回Tax_id列表
	@SuppressWarnings("unchecked")
	public List<String> Query_id(String keyword) throws ClassNotFoundException {
		List<String> id_list = new ArrayList();
		String querystatement = "select t.ID from Taxonomy t where t.Name like '%"
				+ keyword.trim() + "%'";
		javax.persistence.Query query = em.createQuery(querystatement);
		System.out.println(query.getResultList());
		id_list = query.getResultList();
		return id_list;
	}

	// label----id （精确查询）返回Taxonomy_id_list

	public String Query_Taxonomy_id(String keyword)
			throws ClassNotFoundException {
		//List<String> id_list = new ArrayList();
		String querystatement = "select t.ID from Taxonomy t where t.Name = '"
				+ keyword.trim() + "'";
		javax.persistence.Query query = em.createQuery(querystatement);
		System.out.println(query.getResultList());
		String id_list = query.getResultList().toString();
		return id_list;
	}

	// id----Taxonomy返回Taxonomy_list列表
	public List<String> Query_Taxonomy(String keyword)
			throws ClassNotFoundException {
		List<String> Taxonomy_list = new ArrayList();
		List<String> ID = Query_id(keyword);
		int size = ID.size();
		int i = 0;
		while (i < size) {
			String tax = "NCBI_";
			String id = ID.get(i).trim();
			String Taxonomy = tax.concat(id);
			Taxonomy_list.add(Taxonomy);
			i++;
		}
		return Taxonomy_list;
	}
	// Gene--fromSpcies--Taxonomy 返回Taxonomy列表
	public List<String> Query_TaxonomyBYGene(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Taxonomy_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_fromSpecies:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Taxonomy "
					+ "WHERE {"
					+ "Pre_Gene:"
					+ gene
					+ " Pre_fromSpecies:fromSpecies ?Taxonomy ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_list = se.Query_To_List(se.CreatOntoModel(), query,
					Taxonomy_list, "#", ">");
			i++;
		}
		return Taxonomy_list;
	}
	// Gene--fromSpcies--Taxonomy 返回Taxonomy列表
	public List<String> Query_TaxonomyBYGene(String Gene)
			throws ClassNotFoundException {
		List<String> Taxonomy_list = new ArrayList();
			String querystatement = "PREFIX Pre_fromSpecies:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Taxonomy "
					+ "WHERE {"
					+ "Pre_Gene:"
					+ Gene.trim()
					+ " Pre_fromSpecies:fromSpecies ?Taxonomy ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_list = se.Query_To_List(se.CreatOntoModel(), query,
					Taxonomy_list, "#", ">");
		return Taxonomy_list;
	}


	// Taxonomy----Tax_label 返回Tax_label_list列表
	public List<String> Query_Taxonomy_label(List<String> Taxonomy)
			throws ClassNotFoundException {
		List<String> Taxonomy_label_list = new ArrayList();
		int size = Taxonomy.size();
		int i = 0;
		while (i < size) {
			String taxonomy = Taxonomy.get(i);
			String querystatement = "PREFIX Pre_Tax_label:<http://www.w3.org/2000/01/rdf-schema#>"
					+ "PREFIX Pre_Tax_name:<http://um.es/ncbi.owl#>"
					+ "SELECT DISTINCT ?Tax_label  "
					+ "WHERE {"
					+ "Pre_Tax_name:"
					+ taxonomy
					+ " Pre_Tax_label:label ?Tax_label  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_label_list = se.Query_To_List(se.CreatOntoModel(), query,
					Taxonomy_label_list, "=", "@");
			i++;
		}
		return Taxonomy_label_list;
	}
	// Taxonomy----Tax_label 返回Tax_label_list列表
	public List<String> Query_Taxonomy_label(String Taxonomy)
			throws ClassNotFoundException {
		List<String> Taxonomy_label_list = new ArrayList();
			String querystatement = "PREFIX Pre_Tax_label:<http://www.w3.org/2000/01/rdf-schema#>"
					+ "PREFIX Pre_Tax_name:<http://um.es/ncbi.owl#>"
					+ "SELECT DISTINCT ?Tax_label  "
					+ "WHERE {"
					+ "Pre_Tax_name:"
					+ Taxonomy.trim()
					+ " Pre_Tax_label:label ?Tax_label  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_label_list = se.Query_To_List(se.CreatOntoModel(), query,
					Taxonomy_label_list, "=", "@");
			
		return Taxonomy_label_list;
	}

	// Taxonomy----Tax_id 返回Tax_id_list列表
	public List<String> Query_Tax_id(List<String> Taxonomy)
			throws ClassNotFoundException {
		List<String> Taxonomy_id_list = new ArrayList();
		int size = Taxonomy.size();
		int i = 0;
		while (i < size) {
			String taxonomy = Taxonomy.get(i);
			String querystatement = "PREFIX Pre_Tax_id:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Tax_name:<http://um.es/ncbi.owl#>"
					+ "SELECT ?Tax_id "
					+ "WHERE  {"
					+ "Pre_Tax_name:"
					+ taxonomy + " Pre_Tax_id:Identifier ?Tax_id   ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_id_list = se.Query_To_List(se.CreatOntoModel(), query,
					Taxonomy_id_list, "#", ">");
			i++;
		}
		return Taxonomy_id_list;
	}
	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

	@PersistenceContext
	@Resource
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public EntityManager getEm() {
		return em;
	}

	@Resource
	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

}
