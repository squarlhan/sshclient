package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gh")
public class GetHomology {
	private SimpleExample se;

	// Promoter--hasHomology--Homology 返回Homology_list
	public List<String> Query_Homology(String Promoter)
			throws ClassNotFoundException {
		List<String> Homology_list = new ArrayList();
		String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
				+ "PREFIX Pre_hasHomology:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
				+ "SELECT ?homology  "
				+ "WHERE  {"
				+ "Pre_Promoter:"
				+ Promoter.trim()
				+ " Pre_hasHomology:hasHomology ?homology  ."
				+ "}";
		Query query = QueryFactory.create(querystatement);
		Homology_list = se.Query_To_List(se.CreatOntoModel(), query,
				Homology_list, "#", ">");
		return Homology_list;
	}

	// Homology----name返回Homology_name_list
	public List<String> Query_Homology_name(String Homology)
			throws ClassNotFoundException {
		List<String> Homology_name_list = new ArrayList();
		String querystatement = "PREFIX Pre_Homology:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
				+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
				+ "SELECT ?name  "
				+ "WHERE  {"
				+ "Pre_Homology:"
				+ Homology.trim() + " Pre_Name:Name ?name  ." + "}";
		Query query = QueryFactory.create(querystatement);
		Homology_name_list = se.Query_To_List(se.CreatOntoModel(), query,
				Homology_name_list, "=", "@");
		return Homology_name_list;
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

}
