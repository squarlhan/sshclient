package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gP")
public class GetProtein {
	private SimpleExample se;
	// Gene--isExpressedto--Protein返回Protein_listliebiao
	public List<String> Query_Protein(String Gene)
			throws ClassNotFoundException {
		List<String> Protein_list = new ArrayList();
			String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_IsExpressedTo:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT DISTINCT ?protein  "
					+ "WHERE  {"
					+ "Pre_Gene:"
					+ Gene.trim()
					+ " Pre_IsExpressedTo:isExpressedTo ?protein  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Protein_list = se.Query_To_List(se.CreatOntoModel(), query, Protein_list,
					"#", ">");
		return Protein_list;
	}

	// Protein----Protein_name 返回Protein_name_list列表
	public List<String> Query_Protein_name(String Protein)
			throws ClassNotFoundException {
		List<String> Protein_name_list = new ArrayList();
			String querystatement = "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Protein_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Protein_name  "
					+ "WHERE  {"
					+ "Pre_Protein:"
					+ Protein.trim() + " Pre_Protein_Name:Name ?Protein_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Protein_name_list = se.Query_To_List(se.CreatOntoModel(), query,
					Protein_name_list, "=", "@");
		return Protein_name_list;
	}

	// Protein----Protein_id返回Protein_id_list
	public List<String> Query_Protein_id(String Protein)
			throws ClassNotFoundException {
		List<String> Protein_id_list = new ArrayList();
			String querystatement = "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT ?Protein_id  "
					+ "WHERE  {"
					+ "Pre_Protein:"
					+ Protein.trim()
					+ " Pre_Identifier:Identifier ?Protein_id  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Protein_id_list = se.Query_To_List(se.CreatOntoModel(), query,
					Protein_id_list, "#", ">");

		return Protein_id_list;
	}

	@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}

}
