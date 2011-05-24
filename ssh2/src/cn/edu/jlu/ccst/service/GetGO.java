package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gG")
public class GetGO {
	private SimpleExample se;

	// Gene-hasGo-Go 返回Go_list别表
	public List<String> Query_Go(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Go_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_hasGO:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Go_term "
					+ "WHERE  {"
					+ "Pre_Gene:"
					+ gene
					+ " Pre_hasGO:hasGO ?Go_term  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Go_list = se.Query_To_List(se.CreatOntoModel(), query, Go_list, "#", ">");
			i++;
		}
		return Go_list;
	}
	public List<String> Query_Go(String Gene) throws ClassNotFoundException{
		List<String> Go_list = new ArrayList();
		String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
			+ "PREFIX Pre_hasGO:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
			+ "SELECT DISTINCT ?Go_term "
			+ "WHERE  {"
			+ "Pre_Gene:"
			+ Gene.trim()
			+ " Pre_hasGO:hasGO ?Go_term  ." + "}";
	Query query = QueryFactory.create(querystatement);
	Go_list = se.Query_To_List(se.CreatOntoModel(), query, Go_list, "#", ">");
	return Go_list;
	}

	// Go----Go_item 返回Go_item_list列表
	public List<String> Query_Go_item(String Go)
			throws ClassNotFoundException {
		List<String> Go_item_list = new ArrayList();
		
			String querystatement = "PREFIX Pre_Go:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Item:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT ?Go_item  "
					+ "WHERE  {"
					+ "Pre_Go:"
					+ Go.trim()
					+ " Pre_Item:GO_Item ?Go_item  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Go_item_list = se.Query_To_List(se.CreatOntoModel(), query, Go_item_list,
					"=", "@");
		return Go_item_list;
	}
@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}
	

}
