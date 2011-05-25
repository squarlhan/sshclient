package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gk")
public class GetKeyword {
	private SimpleExample se;
	// Promoter--hasKeyword--Keyword 返回Keyword_list
	public List<String> Query_Keyword(String Promoter)
			throws ClassNotFoundException {
		List<String> Keyword_list = new ArrayList();
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_hasKeywords:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT ?Keyword  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ Promoter.trim()
					+ " Pre_hasKeywords:hasKeywords ?Keyword ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Keyword_list = se.Query_To_List(se.CreatOntoModel(), query, Keyword_list,
					"#", ">");
		return Keyword_list;
	}

	// Keyword----keyword返回Keyword_keyword_list
	public List<String> Query_Keyword_Keywords(String Keyword)
			throws ClassNotFoundException {
		List<String> Keyword_Keywords_list = new ArrayList();
			String querystatement = "PREFIX Pre_Keyword:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Keywords:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT ?keywords  "
					+ "WHERE {"
					+ "Pre_Keyword:"
					+ Keyword.trim() + " Pre_Keywords:Keywords ?keywords  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Keyword_Keywords_list = se.Query_To_List(se.CreatOntoModel(), query,
					Keyword_Keywords_list, "=", "@");
		return Keyword_Keywords_list;
	}


	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

}
