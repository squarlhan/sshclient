package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gg")
public class GetGene {
	private SimpleExample se;

	// keyword----Gene 返回Gene列表
	public List<String> Query_Gene(String keyword)
			throws ClassNotFoundException {
		// ?Gene_name Pre_label:label keyword
		List<String> Gene_list = new ArrayList();
		String querystatement = "PREFIX Pre_label:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?Gene  "
				+ "WHERE {"
				+ "?Gene Pre_label:label \""
				+ keyword + "\"@EN ." + "}";
		Query query = QueryFactory.create(querystatement);
		Gene_list = se.Query_To_List(se.CreatOntoModel(), query, Gene_list,
				"#", ">");
		return Gene_list;
	}

	// Taxonomy----Gene
	public List<String> Query_GeneByTax(String Taxonomy)
			throws ClassNotFoundException {
		List<String> Gene_list = new ArrayList();
			String querystatement = "PREFIX Pre_fromSpecies:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Tax:<http://um.es/ncbi.owl#> "
					+ "SELECT DISTINCT ?gene "
					+ "WHERE {"
					+ "?gene Pre_fromSpecies:fromSpecies Pre_Tax:"
					+ Taxonomy.trim()
					+ "  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_list = se.Query_To_List(se.CreatOntoModel(), query, Gene_list,
					"#", ">");
		return Gene_list;
	}
	// Promoter----Gene返回Gene列表
	public List<String> Query_GeneByPro(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Gene_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_isBelongedTo:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT ?gene "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter.trim()
					+ " Pre_isBelongedTo:isBelongedTo ?gene  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_list = se.Query_To_List(se.CreatOntoModel(), query, Gene_list, "#",
					">");
			i++;
		}
		return Gene_list;
	}

	// Gene----Gene_name 返回Gene_name列表
	public List<String> Query_Gene_name(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Gene_name_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Gene_name  "
					+ "WHERE {"
					+ "Pre_Gene:"
					+ gene.trim()
					+ " Pre_Name:Name ?Gene_name ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_name_list = se.Query_To_List(se.CreatOntoModel(), query,
					Gene_name_list, "=", "@");
			i++;
		}
		return Gene_name_list;
	}

	public List<String> Query_Gene_name(String Gene)
			throws ClassNotFoundException {
		List<String> Gene_name_list = new ArrayList();
		String querystatement = "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
				+ "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
				+ "SELECT ?Gene_name  "
				+ "WHERE {"
				+ "Pre_Gene:"
				+ Gene
				+ " Pre_Name:Name ?Gene_name ." + "}";
		Query query = QueryFactory.create(querystatement);
		Gene_name_list = se.Query_To_List(se.CreatOntoModel(), query,
				Gene_name_list, "=", "@");
		return Gene_name_list;
	}

	// Gene----Gene_id 返回Gene_id 列表
	public List<String> Query_Gene_id(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Gene_id_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Gene_id  "
					+ "WHERE {"
					+ "Pre_Name:"
					+ gene
					+ " Pre_Identifier:Identifier ?Gene_id ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_id_list = se.Query_To_List(se.CreatOntoModel(), query,
					Gene_id_list, "=", "@");
			i++;
		}
		return Gene_id_list;
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

}
