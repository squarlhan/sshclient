package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
@Component("gp")
public class GetPromoter {
	private SimpleExample se;

	// Gene--hasPromoter--Promoter 返回Promoter_list
	public List<String> Query_Promoter(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Promoter_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);

			String querystatement = 
				"PREFIX Pre_Gene:" +
				"<http://miuras.inf.um.es/ontologies/OGO.owl#>"
				+ "PREFIX Pre_hasPromoter:" +
				"<http://miuras.inf.um.es/ontologies/promoter.owl#>"
				+ "SELECT DISTINCT ?Promoter "
				+ "WHERE {"
				+ "Pre_Gene:"
				+ gene
				+ " Pre_hasPromoter:hasPromoter ?Promoter  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_list = se.Query_To_List(se.CreatOntoModel(), query,
					Promoter_list, "#", ">");
			i++;
		}
		return Promoter_list;
	}
	// Gene--hasPromoter--Promoter 返回Promoter_list
	public List<String> Query_Promoter(String Gene)
			throws ClassNotFoundException {
		List<String> Promoter_list = new ArrayList();
			String querystatement = 
				"PREFIX Pre_Gene:" +
				"<http://miuras.inf.um.es/ontologies/OGO.owl#>"
				+ "PREFIX Pre_hasPromoter:" +
				"<http://miuras.inf.um.es/ontologies/promoter.owl#>"
				+ "SELECT DISTINCT ?Promoter "
				+ "WHERE {"
				+ "Pre_Gene:"
				+ Gene.trim()
				+ " Pre_hasPromoter:hasPromoter ?Promoter  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_list = se.Query_To_List(se.CreatOntoModel(), query,
					Promoter_list, "#", ">");
		return Promoter_list;
	}
	// Promoter----Promoter_name 返回Promoter_name_list
	public List<String> Query_Promoter_name(String Promoter)
			throws ClassNotFoundException {
		List<String> Promoter_name_list = new ArrayList();
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Promoter_name "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ Promoter + " Pre_Name:Name ?Promoter_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_name_list = se.Query_To_List(se.CreatOntoModel(), query,
					Promoter_name_list, "=", "@");
		return Promoter_name_list;
	}
@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}
}
