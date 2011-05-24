package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
@Component("gr")
public class GetReference {
	public SimpleExample se;
	

	// Promoter--hasReference--Reference
	public List<String> Query_Reference(String Promoter)
			throws ClassNotFoundException {
		List<String> Reference_list = new ArrayList();
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_hasReference:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT DISTINCT ?reference  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ Promoter.trim()
					+ " Pre_hasReference:hasReference ?reference ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Reference_list = se.Query_To_List(se.CreatOntoModel(), query,
					Reference_list, "#", ">");
		return Reference_list;
	}

	// Reference----id 返回Reference_id_list
	public List<String> Query_Reference_id(String Reference)
			throws ClassNotFoundException {
		List<String> Reference_id_list = new ArrayList();
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT DISTINCT ?identifier "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ Reference.trim()
					+ " Pre_Identifier:Identifier ?identifier  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Reference_id_list = se.Query_To_List(se.CreatOntoModel(), query,
					Reference_id_list, "=", "@");
		return Reference_id_list;
	}

	// Reference----author返回Reference_author_list
	public List<String> Query_Reference_author(String Reference)
			throws ClassNotFoundException {
		List<String> Reference_author_list = new ArrayList();
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Author:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT DISTINCT ?author "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ Reference.trim() + " Pre_Author:Author ?author  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_author_list = se.Query_To_List(se.CreatOntoModel(), query,
					Reference_author_list, "=", "@");
		return Reference_author_list;
	}

	// Reference----Title返回Reference_title_list
	public List<String> Query_Reference_title(String Reference)
			throws ClassNotFoundException {
		List<String> Reference_title_list = new ArrayList();
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Title:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT DISTINCT ?title "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ Reference.trim()+ " Pre_Title:Title ?title  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_title_list = se.Query_To_List(se.CreatOntoModel(), query,
					Reference_title_list, "=", "^^");
		return Reference_title_list;
	}

	// Reference----Location返回Reference_location_list
	public List<String> Query_Reference_location(String Reference)
			throws ClassNotFoundException {
		List<String> Reference_location_list = new ArrayList();
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Location:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT DISTINCT ?location "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ Reference.trim() + " Pre_Location:Location ?location  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_location_list = se.Query_To_List(se.CreatOntoModel(), query,
					Reference_location_list, "#", ">");
		return Reference_location_list;
	}
	public SimpleExample getSe() {
		return se;
	}
@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

}
