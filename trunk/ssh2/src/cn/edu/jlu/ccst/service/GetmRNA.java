package cn.edu.jlu.ccst.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;

@Component("gm")
public class GetmRNA {
	private SimpleExample se;
	// Promoter--active--mRNA 返回mRNA_list
	public List<String> Query_mRNAByP(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> mRNA_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_active:<http://miuras.inf.um.es/ontologies/promoter.owl> "
					+ "SELECT ?mRNA  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter.trim()
					+ " Pre_active:active ?mRNA  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_list = se.Query_To_List(se.CreatOntoModel(), query, mRNA_list, "#",
					">");
			i++;
		}
		return mRNA_list;
	}
	// Gene--isTranscribedto--mRNA 返回mRNA_list
	public List<String> Query_mRNA(String Gene)
			throws ClassNotFoundException {
		List<String> mRNA_list = new ArrayList();
			String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "PREFIX Pre_isTranscribedto:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "SELECT ?mRNA "
					+ "WHERE {"
					+ "Pre_Gene:"
					+ Gene.trim()
					+ " Pre_isTranscribedto:isTranscribedeto ?mRNA  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_list = se.Query_To_List(se.CreatOntoModel(), query, mRNA_list, "#",
					">");
		return mRNA_list;
	}

	// mRNA----mRNA_name 返回mRNA_name_list
	public List<String> Query_mRNA_name(String mRNA)
			throws ClassNotFoundException {
		List<String> mRNA_name_list = new ArrayList();
			String querystatement = "PREFIX Pre_mRNA:<http://miuras.inf.um.es/ontologies/promoter.owl#> "
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#> "
					+ "SELECT ?mRNA_name  "
					+ "WHERE {"
					+ "Pre_nRNA:"
					+ mRNA.trim()
					+ " Pre_Name:Name ?mRNA_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_name_list = se.Query_To_List(se.CreatOntoModel(), query,
					mRNA_name_list, "=", "@");
		return mRNA_name_list;
	}

	// mRNA----mRNA_id 返回mRNA_id_list
	public List<String> Query_mRNA_id(String mRNA)
			throws ClassNotFoundException {
		List<String> mRNA_id_list = new ArrayList();
			String querystatement = "PREFIX Pre_mRNA:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?mRNA_id  "
					+ "WHERE {"
					+ "Pre_mRNA:"
					+ mRNA.trim()
					+ " Pre_Identifier:Identifier ?mRNA_id  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_id_list = se.Query_To_List(se.CreatOntoModel(), query, mRNA_id_list,
					"#", ">");

		return mRNA_id_list;
	}

@Resource
	public void setSe(SimpleExample se) {
		this.se = se;
	}

	public SimpleExample getSe() {
		return se;
	}
	

}
