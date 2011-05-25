package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gres")
public class GetResource {
	private SimpleExample se;
	// Promoter--hasResource--Resource 返回Resource_list
	public List<String> Query_Resource(String Promoter)
			throws ClassNotFoundException {
		List<String> Resource_list =new ArrayList();
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_hasResource:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT ?Resource  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ Promoter.trim()
					+ " Pre_hasResource:hasResource ?Resource  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Resource_list = se.Query_To_List(se.CreatOntoModel(), query,
					Resource_list, "#", ">");
		return Resource_list;
	}

	// Resource----Name 返回Resource_name_list
	public List<String> Query_Resource_name(String Resource)
			throws ClassNotFoundException {
		List<String> Resource_name_list = new ArrayList();
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT ?name  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ Resource.trim()
					+ " Pre_Name:Name ?name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_name_list = se.Query_To_List(se.CreatOntoModel(), query,
					Resource_name_list, "=", "@");
		return Resource_name_list;
	}

	// Resource----ID 返回Resource_id_list
	public List<String> Query_Resource_id(String Resource)
			throws ClassNotFoundException {
		List<String> Resource_id_list = new ArrayList();
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT ?id  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ Resource.trim()
					+ " Pre_Identifier:Identifier ?id  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_id_list = se.Query_To_List(se.CreatOntoModel(), query,
					Resource_id_list, "=", "@");
		return Resource_id_list;
	}

	// Resource----link 返回Resource_link_list
	public List<String> Query_Resource_link(String Resource)
			throws ClassNotFoundException {
		List<String> Resource_link_list = new ArrayList();
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Link:<http://miuras.inf.um.es/ontologies/promoter.owl> "
					+ "SELECT ?link  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ Resource.trim()
					+ " Pre_Link:Link ?link  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_link_list = se.Query_To_List(se.CreatOntoModel(), query,
					Resource_link_list, "=", "@");
		return Resource_link_list;
	}


	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

}
