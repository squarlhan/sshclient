/**
 * some simple examples to practice jena
 */
package cn.edu.jlu.ccst.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hp.hpl.jena.datatypes.BaseDatatype;
import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * @author Woden
 * 
 */
@Component("se")
public class SimpleExample {

	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	// URL of the database
	public static final String DB_SCHEMA = "promoter";
	// name of the database schema.
	public static final String DB_USER = "root"; // database user name
	public static final String DB_PASSWD = "root"; // database user password
	public static final String TYPE_DB = "MySQL"; // database type
	public static final String DB_Driver = "com.mysql.jdbc.Driver";
	private static final String uriOntology = "file:E:/promoter/ontologies/promoter.owl";

	public static final String PROMOTER_PREFIX = "http://miuras.inf.um.es/ontologies/promoter.owl";
	public static final String OGO_PREFIX = "http://miuras.inf.um.es/ontologies/OGO.owl";
	public static final String ECO_PREFIX = "http://um.es/eco.owl";
	public static final String GO_PREFIX = "http://um.es/go.owl";
	public static final String NCBI_PREFIX = "http://um.es/ncbi.owl";
	// Where
	// the
	// ontology
	// file
	// is
	// located
	// in
	// your
	// pc

	private PersistentOntology po;

	public PersistentOntology getPo() {
		return po;
	}

	@javax.annotation.Resource
	public void setPo(PersistentOntology po) {
		this.po = po;
	}

	private List<String[]> importList = new ArrayList();

	private String[] nss = { OGO_PREFIX, "file:E:/promoter/ontologies/OGO.owl" };
	private String[] nss1 = { ECO_PREFIX,
			"file:E:/promoter/ontologies/eco_punned.owl" };
	private String[] nss2 = { GO_PREFIX,
			"file:E:/promoter/ontologies/go_punned.owl" };
	private String[] nss3 = { NCBI_PREFIX,
			"file:E:/promoter/ontologies/ncbi_punned.owl" };

	public OntModel createOntModel() {

		OntModel model = null;
		boolean deleteDB = true; // If true the persistent model will be
									// deleted.
		try {
			Class.forName(DB_Driver);// Load the Driver
			ModelMaker mm = po.getRDBMaker(DB_URL + DB_SCHEMA, DB_USER,
					DB_PASSWD, TYPE_DB, deleteDB);
			model = loadDB(mm, uriOntology);
			System.out.println("uriOntology=" + uriOntology);

		} catch (Exception e) {
			System.out.println("Error: when loading the ontology");
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * Get the ontology from the Database
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	public OntModel CreatOntoModel() throws ClassNotFoundException {
		Class.forName(DB_Driver); // Load the Driver

		ModelMaker maker = po.getRDBMaker(DB_URL + DB_SCHEMA, DB_USER,
				DB_PASSWD, TYPE_DB, false);
		Model base = maker.openModel(uriOntology, false);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		spec.setImportModelMaker(maker);
		OntModel m = ModelFactory.createOntologyModel(spec, base);
		return m;
	}

	private OntModel loadDB(ModelMaker maker, String source) {
		// use the model maker to get the base model as a persistent model
		// strict=false, so we get an existing model by that name if it exists
		// or create a new one
		Model base = maker.createModel(source, false);

		// now we plug that base model into an ontology model that also uses
		// the given model maker to create storage for imported models
		OntModel m = ModelFactory.createOntologyModel(po.getModelSpec(maker),
				base);
		// now load the source document, which will also load any imports
		addImports(m); // if the ontology doesn��t import any other ontology
						// this is not needed
		m.read(source);

		return m;
	}

	private void addImports(OntModel model) {
		importList.add(nss);
		importList.add(nss1);
		importList.add(nss2);
		importList.add(nss3);
		if ((importList != null) && (!importList.isEmpty())) { // importList is
																// a two
																// dimension
																// array where
																// position [0]
																// contains the
																// namespace of
																// the imported
																// ontology and
																// position [1]
																// contains the
																// location of
																// the imported
																// ontology
																// file;
			OntDocumentManager dm = model.getDocumentManager();
			Iterator<String[]> itImp = importList.iterator();
			while (itImp.hasNext()) {
				String[] importOntology = itImp.next();
				dm.addAltEntry(importOntology[0], importOntology[1]);
			}
			dm.setProcessImports(true);
			dm.loadImports(model);
		}
	}

	/**
	 * delete resource by uri
	 * 
	 * @param onmo
	 * @param myuri
	 */
	public void deleteResource(OntModel onmo, String myuri) {
		// access every individual of a class
		OntResource or = onmo.getOntResource(myuri);
		if (or != null) {
			or.remove();
		} else
			return;
	}

	/**
	 * create new Object Property statement for a individual
	 * 
	 * @param onmo
	 * @param sinduri
	 * @param propuri
	 * @param oinduri
	 * @return
	 */
	public Individual addObjProperty(OntModel onmo, String sinduri,
			String propuri, String oinduri) {
		Individual sind = null;
		ObjectProperty prop = null;
		Individual oind = null;
		OntResource or1 = onmo.getOntResource(sinduri);
		if (or1 != null && or1.canAs(Individual.class)) {
			sind = or1.as(Individual.class);
		} else
			return null;
		OntResource or2 = onmo.getOntResource(propuri);
		if (or2 != null && or2.canAs(ObjectProperty.class)) {
			prop = or2.as(ObjectProperty.class);
		} else
			return null;
		OntResource or3 = onmo.getOntResource(oinduri);
		if (or3 != null && or3.canAs(Individual.class)) {
			oind = or3.as(Individual.class);
		} else
			return null;
		sind.addProperty(prop, oind);
		return sind;
	}

	/**
	 * Add value to Data Property for a individual
	 * 
	 * @param onmo
	 * @param sinduri
	 * @param propuri
	 * @param valueuri
	 * @return
	 */
	public Individual addDataProperty(OntModel onmo, String sinduri,
			String propuri, String valueuri) {
		Individual sind = null;
		DatatypeProperty prop = null;
		OntResource or1 = onmo.getOntResource(sinduri);
		if (or1 != null && or1.canAs(Individual.class)) {
			sind = or1.as(Individual.class);
		} else
			return null;
		OntResource or2 = onmo.getOntResource(propuri);
		if (or2 != null && or2.canAs(DatatypeProperty.class)) {
			prop = or2.as(DatatypeProperty.class);
		} else
			return null;
		BaseDatatype bd = null;
		if (prop.getRange() != null) {
			bd = new BaseDatatype(prop.getRange().toString());
			if (bd != null) {
				sind.addProperty(prop, valueuri, bd);
			} else {
				sind.addProperty(prop, valueuri);
			}
		}
		return sind;
	}

	/**
	 * create or get resource if the uri exist. 0: individual 1: objectproperty
	 * 2: dataproerty 3:class
	 */
	public OntResource CreateandGetRes(String newuri, String typeuri,
			OntModel onmo, int Type) {
		OntResource newres = null;
		newres = onmo.getOntResource(newuri);
		if (newres != null)
			return newres;
		else {
			switch (Type) {
			case 0:
				newres = onmo.createIndividual(newuri,
						onmo.getOntClass(typeuri));
				break;
			case 1:
				newres = onmo.createObjectProperty(newuri);
				break;
			case 2:
				newres = onmo.createDatatypeProperty(newuri);
				break;
			case 3:
				newres = onmo.createClass(newuri);
				OntResource fatherRes = onmo.getOntResource(typeuri);
				if (fatherRes != null && fatherRes.canAs(OntClass.class)) {
					OntClass fatherClass = fatherRes.as(OntClass.class);
					fatherClass.addSubClass(newres);
				}
				break;
			default:
				break;
			}
			return newres;
		}
	}

	/**
	 * create or get individual if the uri exist.
	 */
	public Individual CreateandGetIdv(String newuri, String typeuri,
			OntModel onmo) {
		Individual newres = null;
		newres = onmo.getIndividual(newuri);
		if (newres != null)
			return newres;
		else {

			newres = onmo.createIndividual(newuri, onmo.getOntClass(typeuri));
		}
		return newres;
	}

	/**
	 * modefi the property of a individual
	 * 
	 * @param onmo
	 * @param idvuri
	 * @param dpuri
	 * @param dpvalue
	 * @return
	 */
	public Individual modifyProperty(OntModel onmo, String idvuri,
			String dpuri, String dpvalue) {
		Individual indi = onmo.getIndividual(idvuri);
		Property pro = onmo.getProperty(dpuri);
		Individual objindi = onmo.getIndividual(dpvalue);
		RDFNode rdfNode = null;
		rdfNode = objindi != null ? objindi : onmo.createLiteral(dpvalue);
		indi.setPropertyValue(pro, rdfNode);
		return indi;
	}

	/**
	 * Do SPARQL Query
	 * 
	 * @param onmo
	 * @param querystatement
	 * @return
	 */
	public ResultSet doquery(OntModel onmo, String keyword) {
		// Culicinae
		System.out.println(keyword);

		String querystatement = "PREFIX Prop: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?Gene_Name ?isExpressedTo ?protein "
				+ "WHERE {"
				+ "      ?protein Prop:label \""
				+ keyword
				+ "\" . "
				+ " ?protein ?prop ?NCBI_ID ."
				+ " ?Gene_Name ?isExpressedTo ?protein ." + "    }";

		Query query = QueryFactory.create(querystatement);
		QueryExecution qe = QueryExecutionFactory.create(query, onmo);
		ResultSet results = qe.execSelect();
		// ResultSetFormatter.out(System.out, results, query);
		qe.close();
		return results;
	}

	// 查询结果为数组
	public List<String> Query_To_List(OntModel onmo, Query query,
			List<String> resultlist1, String begin, String end) {
		QueryExecution qe = QueryExecutionFactory.create(query, onmo);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			String aa = results.next().toString();
			String bb = getSubString(aa, begin, end);
			resultlist1.add(bb);
			System.out.println(bb);
		}
		qe.close();
		return resultlist1;
	}

	// 查询结果为字符串
	public String Query_To_String(OntModel onmo, Query query) {
		QueryExecution qe = QueryExecutionFactory.create(query, onmo);
		ResultSet result = qe.execSelect();
		return result.toString();
	}

	// 得到字符串子串
	public String getSubString(String s, String a, String b) {
		int index1 = s.indexOf(a);
		int index2 = s.lastIndexOf(b);
		String ss;
		if (a.equals("=") && b.equals("@")) {
			String[] sarray = s.split("\"");
			ss = sarray[1];
			// ss = s.substring(index1 + 3, index2-1);
		} else if (a.equals("\\") && b.equals("\\")) {
			ss = s.substring(index1 + 5, index2 - 4);
		} else {
			ss = s.substring(index1 + 1, index2);
		}
		return ss;
	}

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
		Gene_list = Query_To_List(CreatOntoModel(), query, Gene_list, "#", ">");
		return Gene_list;
	}

	// keyword----Promoter 返回Promoter列表
	public List<String> Query_Promoter(String keyword)
			throws ClassNotFoundException {
		List<String> Promoter_list = new ArrayList();
		String querystatement = "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
				+ "PREFIX Pre_string:<http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT ?Promoter  "
				+ "WHERE {"
				+ "?Promoter Pre_Name:Name \"Promoter_"
				+ keyword
				+ "\"^^Pre_string:string ." + "}";
		Query query = QueryFactory.create(querystatement);
		Promoter_list = Query_To_List(CreatOntoModel(), query, Promoter_list,
				"#", ">");
		return Promoter_list;
	}

	// keyword----Taxonomy 返回Taxonomy列表
	public List<String> Query_Taxonomy(String keyword)
			throws ClassNotFoundException {
		List<String> Taxonomy_list = new ArrayList();
		String querystatement = "PREFIX Pre_label:<http://www.w3.org/2000/01/rdf-schema#>"
				+ "SELECT ?Taxonomy  "
				+ "WHERE {"
				+ "?Taxonomy Pre_label:label \"" + keyword + "\"@EN ." + "}";
		Query query = QueryFactory.create(querystatement);
		Taxonomy_list = Query_To_List(CreatOntoModel(), query, Taxonomy_list,
				"#", ">");
		return Taxonomy_list;
	}

	// Taxonomy----Gene
	public List<String> Query_GeneByTax(List<String> Taxonomy)
			throws ClassNotFoundException {
		List<String> Gene_list = new ArrayList();
		int size = Taxonomy.size();
		int i = 0;
		while (i < size) {
			String Tax = Taxonomy.get(i);
			String querystatement = "PREFIX Pre_fromSpecies:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Tax:<http://um.es/ncbi.owl#>"
					+ "SELECT ?gene "
					+ "WHERE {"
					+ "?gene Pre_fromSpecies:fromSpecies Pre_Tax:"
					+ Tax
					+ "  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_list = Query_To_List(CreatOntoModel(), query, Gene_list, "#",
					">");
			i++;
		}
		return Gene_list;
	}

	// keyword----Keywords 返回Keyword列表
	public List<String> Query_KeywordBykey(List<String> Keywords)
			throws ClassNotFoundException {
		List<String> Keyword_list = new ArrayList();
		int size = Keywords.size();
		int i = 0;
		while (i < size) {
			String key = Keywords.get(i);
			String querystatement = "PREFIX Pre_string:<http://www.w3.org/2001/XMLSchema#>"
					+ "PREFIX Pre_Keywords:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?Keyword "
					+ "WHERE {"
					+ "?Keyword Pre_Keywords:Keywords \""
					+ key
					+ "\"^^Pre_string:string" + "  ." + "}";
			Query query = QueryFactory.create(querystatement);
			if (query.hasAggregators())
				Keyword_list = Query_To_List(CreatOntoModel(), query,
						Keyword_list, "#", ">");
			i++;
		}
		return Keyword_list;
	}

	// Keyword----Promoter 返回Promoter_list列表
	public List<String> Query_ProByKey(List<String> Keyword)
			throws ClassNotFoundException {
		List<String> Promoter_list = new ArrayList();
		int size = Keyword.size();
		int i = 0;
		while (i < size) {
			String key = Keyword.get(i);
			String querystatement = "PREFIX Pre_hasKeywords:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Keyword:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?promoter "
					+ "WHERE {"
					+ "?promoter Pre_hasKeywords:hasKeywords Pre_Keyword:"
					+ key + "  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_list = Query_To_List(CreatOntoModel(), query,
					Promoter_list, "#", ">");
			i++;
		}
		return Promoter_list;
	}

	// Promoter----Gene返回Gene列表
	public List<String> Query_GeneByPro(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Gene_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_isBelongedTo:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?gene "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ " Pre_isBelongedTo:isBelongedTo ?gene  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_list = Query_To_List(CreatOntoModel(), query, Gene_list, "#",
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
					+ gene
					+ " Pre_Name:Name ?Gene_name ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_name_list = Query_To_List(CreatOntoModel(), query,
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
		Gene_name_list = Query_To_List(CreatOntoModel(), query, Gene_name_list,
				"=", "@");
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
			Gene_id_list = Query_To_List(CreatOntoModel(), query, Gene_id_list,
					"#", ">");
			i++;
		}
		return Gene_id_list;
	}

	// Gene--fromSpcies--Taxonomy 返回Taxonomy列表
	public List<String> Query_Taxonomy(List<String> Gene)
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
			Taxonomy_list = Query_To_List(CreatOntoModel(), query,
					Taxonomy_list, "#", ">");
			i++;
		}
		return Taxonomy_list;
	}

	// Taxonomy----Tax_label 返回Tax_label_list列表
	public List<String> Query_Tax_label(List<String> Taxonomy)
			throws ClassNotFoundException {
		List<String> Taxonomy_label_list = new ArrayList();
		int size = Taxonomy.size();
		int i = 0;
		while (i < size) {
			String taxonomy = Taxonomy.get(i);
			String querystatement = "PREFIX Pre_Tax_label:<http://www.w3.org/2000/01/rdf-schema#>"
					+ "PREFIX Pre_Tax_name:<http://um.es/ncbi.owl#>"
					+ "SELECT ?Tax_label  "
					+ "WHERE {"
					+ "Pre_Tax_name:"
					+ taxonomy + " Pre_Tax_label:label ?Tax_label  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Taxonomy_label_list = Query_To_List(CreatOntoModel(), query,
					Taxonomy_label_list, "=", "@");
			i++;
		}
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
			Taxonomy_id_list = Query_To_List(CreatOntoModel(), query,
					Taxonomy_id_list, "#", ">");
			i++;
		}
		return Taxonomy_id_list;
	}

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
			Go_list = Query_To_List(CreatOntoModel(), query, Go_list, "#", ">");
			i++;
		}
		return Go_list;
	}

	// Go----Go_item 返回Go_item_list列表
	public List<String> Query_Go_item(List<String> Go)
			throws ClassNotFoundException {
		List<String> Go_item_list = new ArrayList();
		int size = Go.size();
		int i = 0;
		while (i < size) {
			String go = Go.get(i);
			String querystatement = "PREFIX Pre_Go:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Item:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?Go_item  "
					+ "WHERE  {"
					+ "Pre_Go:"
					+ go
					+ " Pre_Item:GO_Item ?Go_item  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Go_item_list = Query_To_List(CreatOntoModel(), query, Go_item_list,
					"=", "@");
			i++;
		}
		return Go_item_list;
	}

	// Gene--isExpressedto--Protein返回Protein_listliebiao
	public List<String> Query_Protein(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Protein_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_IsExpressedTo:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?protein  "
					+ "WHERE  {"
					+ "Pre_Gene:"
					+ gene
					+ " Pre_IsExpressedTo:isExpressedTo ?protein  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Protein_list = Query_To_List(CreatOntoModel(), query, Protein_list,
					"#", ">");
			i++;
		}
		return Protein_list;
	}

	// Protein----Protein_name 返回Protein_name_list列表
	public List<String> Query_Protein_name(List<String> Protein)
			throws ClassNotFoundException {
		List<String> Protein_name_list = new ArrayList();
		int size = Protein.size();
		int i = 0;
		while (i < size) {
			String protein = Protein.get(i);
			String querystatement = "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Protein_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Protein_name  "
					+ "WHERE  {"
					+ "Pre_Protein:"
					+ protein + " Pre_Protein_Name:Name ?Protein_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Protein_name_list = Query_To_List(CreatOntoModel(), query,
					Protein_name_list, "=", "@");
			i++;
		}
		return Protein_name_list;
	}

	// Protein----Protein_id返回Protein_id_list
	public List<String> Query_Protein_id(List<String> Protein)
			throws ClassNotFoundException {
		List<String> Protein_id_list = new ArrayList();
		int size = Protein.size();
		int i = 0;
		while (i < size) {
			String protein = Protein.get(i);
			String querystatement = "PREFIX Pre_Protein:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Protein_id  "
					+ "WHERE  {"
					+ "Pre_Protein:"
					+ protein
					+ " Pre_Identifier:Identifier ?Protein_id  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Protein_id_list = Query_To_List(CreatOntoModel(), query,
					Protein_id_list, "#", ">");

		}
		return Protein_id_list;
	}

	// Gene--isTranscribedto--mRNA 返回mRNA_list
	public List<String> Query_mRNA(List<String> Gene)
			throws ClassNotFoundException {
		List<String> mRNA_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Gene:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_isTranscribedto:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?mRNA"
					+ "WHERE {"
					+ "Pre_Gene:"
					+ gene
					+ " Pre_isTranscribedto:isTranscribedeto ?mRNA  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_list = Query_To_List(CreatOntoModel(), query, mRNA_list, "#",
					">");
			i++;
		}
		return mRNA_list;
	}

	// mRNA----mRNA_name 返回mRNA_name_list
	public List<String> Query_mRNA_name(List<String> mRNA)
			throws ClassNotFoundException {
		List<String> mRNA_name_list = new ArrayList();
		int size = mRNA.size();
		int i = 0;
		while (i < size) {
			String mrna = mRNA.get(i);
			String querystatement = "PREFIX Pre_mRNA:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?mRNA_name  "
					+ "WHERE {"
					+ "Pre_nRNA:"
					+ mrna
					+ " Pre_Name:Name ?mRNA_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_name_list = Query_To_List(CreatOntoModel(), query,
					mRNA_name_list, "=", "@");
			i++;
		}
		return mRNA_name_list;
	}

	// mRNA----mRNA_id 返回mRNA_id_list
	public List<String> Query_mRNA_id(List<String> mRNA)
			throws ClassNotFoundException {
		List<String> mRNA_id_list = new ArrayList();
		int size = mRNA.size();
		int i = 0;
		while (i < size) {
			String mrna = mRNA.get(i);
			String querystatement = "PREFIX Pre_mRNA:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?mRNA_id  "
					+ "WHERE {"
					+ "Pre_mRNA:"
					+ mrna
					+ " Pre_Identifier:Identifier ?mRNA_id  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_id_list = Query_To_List(CreatOntoModel(), query, mRNA_id_list,
					"#", ">");
			i++;
		}
		return mRNA_id_list;
	}

	// Gene--hasPromoter--Promoter 返回Promoter_list
	public List<String> Query_Promoter(List<String> Gene)
			throws ClassNotFoundException {
		List<String> Promoter_list = new ArrayList();
		int size = Gene.size();
		int i = 0;
		while (i < size) {
			String gene = Gene.get(i);
			String querystatement = "PREFIX Pre_Gene:"
					+ "<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_hasPromoter:"
					+ "<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?Promoter " + "WHERE {" + "Pre_Gene:" + gene
					+ " Pre_hasPromoter:hasPromoter ?Promoter  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_list = Query_To_List(CreatOntoModel(), query,
					Promoter_list, "#", ">");
			i++;
		}
		return Promoter_list;
	}

	// Promoter----Promoter_name 返回Promoter_name_list
	public List<String> Query_Promoter_name(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Promoter_name_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Promoter_name "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter + " Pre_Name:Name ?Promoter_name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Promoter_name_list = Query_To_List(CreatOntoModel(), query,
					Promoter_name_list, "=", "@");
			i++;
		}
		return Promoter_name_list;
	}

	// Promoter--isBelongedTo--Gene 返回Gene_list
	public List<String> Query_GeneByP(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Gene_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_isBelongedTo:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?gene  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ "Pre_isBelongedTo:isBelongedTo ?gene  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Gene_list = Query_To_List(CreatOntoModel(), query, Gene_list, "#",
					">");
			i++;
		}
		return Gene_list;
	}

	// Promoter--active--mRNA 返回mRNA_list
	public List<String> Query_mRNAByP(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> mRNA_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_active:<http://miuras.inf.um.es/ontologies/promoter.owl>"
					+ "SELECT ?mRNA  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ "Pre_active:active ?mRNA  ." + "}";
			Query query = QueryFactory.create(querystatement);
			mRNA_list = Query_To_List(CreatOntoModel(), query, mRNA_list, "#",
					">");
			i++;
		}
		return mRNA_list;
	}

	// Promoter--hasKeyword--Keyword 返回Keyword_list
	public List<String> Query_Keyword(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Keyword_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_hasKeywords:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?Keyword  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ " Pre_hasKeywords:hasKeywords ?Keyword ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Keyword_list = Query_To_List(CreatOntoModel(), query, Keyword_list,
					"#", ">");
			i++;
		}
		return Keyword_list;
	}

	// Keyword----keyword返回Keyword_keyword_list
	public List<String> Query_Keyword_Keywords(List<String> Keyword)
			throws ClassNotFoundException {
		List<String> Keyword_Keywords_list = new ArrayList();
		int size = Keyword.size();
		int i = 0;
		while (i < size) {
			String keyword = Keyword.get(i);
			String querystatement = "PREFIX Pre_Keyword:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Keywords:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?keywords  "
					+ "WHERE {"
					+ "Pre_Keyword:"
					+ keyword + " Pre_Keywords:Keywords ?keywords  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Keyword_Keywords_list = Query_To_List(CreatOntoModel(), query,
					Keyword_Keywords_list, "=", "@");
			i++;
		}
		return Keyword_Keywords_list;
	}

	// Promoter--hasResource--Resource 返回Resource_list
	public List<String> Query_Resource(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Resource_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_hasResource:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?Resource  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ " Pre_hasResource:hasResource ?Resource  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Resource_list = Query_To_List(CreatOntoModel(), query,
					Resource_list, "#", ">");
			i++;
		}
		return Resource_list;
	}

	// Resource----Name 返回Resource_name_list
	public List<String> Query_Resource_name(List<String> Resource)
			throws ClassNotFoundException {
		List<String> Resource_name_list = new ArrayList();
		int size = Resource.size();
		int i = 0;
		while (i < size) {
			String resource = Resource.get(i);
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?name  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ resource
					+ " Pre_Name:Name ?name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_name_list = Query_To_List(CreatOntoModel(), query,
					Resource_name_list, "=", "@");
			i++;
		}
		return Resource_name_list;
	}

	// Resource----ID 返回Resource_id_list
	public List<String> Query_Resource_id(List<String> Resource)
			throws ClassNotFoundException {
		List<String> Resource_id_list = new ArrayList();
		int size = Resource.size();
		int i = 0;
		while (i < size) {
			String resource = Resource.get(i);
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?id  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ resource
					+ " Pre_Identifier:Identifier ?id  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_id_list = Query_To_List(CreatOntoModel(), query,
					Resource_id_list, "=", "@");
			i++;
		}
		return Resource_id_list;
	}

	// Resource----link 返回Resource_link_list
	public List<String> Query_Resource_link(List<String> Resource)
			throws ClassNotFoundException {
		List<String> Resource_link_list = new ArrayList();
		int size = Resource.size();
		int i = 0;
		while (i < size) {
			String resource = Resource.get(i);
			String querystatement = "PREFIX Pre_Resource:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "PREFIX Pre_Link:<http://miuras.inf.um.es/ontologies/promoter.owl>"
					+ "SELECT ?link  "
					+ "WHERE {"
					+ "Pre_Resource:"
					+ resource
					+ " Pre_Link:Link ?link  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Resource_link_list = Query_To_List(CreatOntoModel(), query,
					Resource_link_list, "=", "@");
			i++;
		}
		return Resource_link_list;
	}

	// Promoter--hasHomology--Homology 返回Homology_list
	public List<String> Query_Homology(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Homology_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_hasHomology:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?homology  "
					+ "WHERE  {"
					+ "Pre_Promoter:"
					+ promoter
					+ " Pre_hasHomology:hasHomology ?homology  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Homology_list = Query_To_List(CreatOntoModel(), query,
					Homology_list, "#", ">");
			i++;
		}
		return Homology_list;
	}

	// Homology----name返回Homology_name_list
	public List<String> Query_Homology_name(List<String> Homology)
			throws ClassNotFoundException {
		List<String> Homology_name_list = new ArrayList();
		int size = Homology.size();
		int i = 0;
		while (i < size) {
			String homology = Homology.get(i);
			String querystatement = "PREFIX Pre_Homology:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Name:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?name  "
					+ "WHERE  {"
					+ "Pre_Homology:"
					+ homology + " Pre_Name:Name ?name  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Homology_name_list = Query_To_List(CreatOntoModel(), query,
					Homology_name_list, "=", "@");
			i++;
		}
		return Homology_name_list;
	}

	// Promoter--hasReference--Reference
	public List<String> Query_Reference(List<String> Promoter)
			throws ClassNotFoundException {
		List<String> Reference_list = new ArrayList();
		int size = Promoter.size();
		int i = 0;
		while (i < size) {
			String promoter = Promoter.get(i);
			String querystatement = "PREFIX Pre_Promoter:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_hasReference:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?reference  "
					+ "WHERE {"
					+ "Pre_Promoter:"
					+ promoter
					+ " Pre_hasReference:hasReference ?reference ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Reference_list = Query_To_List(CreatOntoModel(), query,
					Reference_list, "#", ">");
			i++;
		}
		return Reference_list;
	}

	// Reference----id 返回Reference_id_list
	public List<String> Query_Reference_id(List<String> Reference)
			throws ClassNotFoundException {
		List<String> Reference_id_list = new ArrayList();
		int size = Reference.size();
		int i = 0;
		while (i < size) {
			String reference = Reference.get(i);
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Identifier:<http://miuras.inf.um.es/ontologies/OGO.owl#>"
					+ "SELECT ?identifier "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ reference
					+ " Pre_Identifier:Identifier ?identifier  ."
					+ "}";
			Query query = QueryFactory.create(querystatement);
			Reference_id_list = Query_To_List(CreatOntoModel(), query,
					Reference_id_list, "=", "@");
			i++;
		}
		return Reference_id_list;
	}

	// Reference----author返回Reference_author_list
	public List<String> Query_Reference_author(List<String> Reference)
			throws ClassNotFoundException {
		List<String> Reference_author_list = new ArrayList();
		int size = Reference.size();
		int i = 0;
		while (i < size) {
			String reference = Reference.get(i);
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Author:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?author "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ reference + " Pre_Author:Author ?author  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_author_list = Query_To_List(CreatOntoModel(), query,
					Reference_author_list, "=", "@");
			i++;
		}
		return Reference_author_list;
	}

	// Reference----Title返回Reference_title_list
	public List<String> Query_Reference_title(List<String> Reference)
			throws ClassNotFoundException {
		List<String> Reference_title_list = new ArrayList();
		int size = Reference.size();
		int i = 0;
		while (i < size) {
			String reference = Reference.get(i);
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Title:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?title "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ reference + " Pre_Title:Title ?title  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_title_list = Query_To_List(CreatOntoModel(), query,
					Reference_title_list, "=", "^^");
			i++;
		}
		return Reference_title_list;
	}

	// Reference----Location返回Reference_location_list
	public List<String> Query_Reference_location(List<String> Reference)
			throws ClassNotFoundException {
		List<String> Reference_location_list = new ArrayList();
		int size = Reference.size();
		int i = 0;
		while (i < size) {
			String reference = Reference.get(i);
			String querystatement = "PREFIX Pre_Reference:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "PREFIX Pre_Location:<http://miuras.inf.um.es/ontologies/promoter.owl#>"
					+ "SELECT ?location "
					+ "WHERE {"
					+ "Pre_Reference:"
					+ reference + " Pre_Location:Location ?location  ." + "}";
			Query query = QueryFactory.create(querystatement);
			Reference_location_list = Query_To_List(CreatOntoModel(), query,
					Reference_location_list, "#", ">");
			i++;
		}
		return Reference_location_list;
	}

	/**
	 * do reasoner for the ontology
	 * 
	 * @param onmo
	 * @return
	 */
	public InfModel doReasoner(OntModel onmo) {
		List rules = Rule.rulesFromURL("myrules.txt");
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setTransitiveClosureCaching(true);

		InfModel infmodel = ModelFactory.createInfModel(reasoner, onmo);
		infmodel.write(System.out, "N-TRIPLE");
		Resource resource_2 = infmodel.getResource(PROMOTER_PREFIX
				+ "#Promoter");
		ExtendedIterator iter_2 = infmodel.listStatements(resource_2, null,
				(RDFNode) null);
		while (iter_2.hasNext()) {
			System.out.println(iter_2.next());
		}
		return infmodel;
	}

	/**
	 * For testing, list all the individuals and the 3-mers of a class
	 * 
	 * @param onmo
	 * @param classuri
	 */
	public void testListIndividual(OntModel onmo, String classuri) {
		OntResource mor1 = onmo.getOntResource(classuri);
		if (mor1.canAs(OntClass.class)) {
			OntClass rootClass = mor1.as(OntClass.class);
			ExtendedIterator<? extends OntResource> it2 = rootClass
					.listInstances();
			while (it2.hasNext()) {
				OntResource tt = it2.next();
				Individual ttt = null;
				if (tt.canAs(Individual.class)) {
					ttt = tt.as(Individual.class);
					// access the RDF 3-mer
					StmtIterator stmtI = ttt.listProperties();
					while (stmtI.hasNext()) {
						Statement stmt = stmtI.nextStatement();
						Resource subject = stmt.getSubject(); // get the subject
						Property predicate = stmt.getPredicate(); // get the
																	// predicate
						RDFNode object = stmt.getObject(); // get the object
						System.out.println(subject.toString() + "->"
								+ predicate.toString() + "->"
								+ object.toString());
					}
				}

				System.out.println("ChildInstance = " + ttt.toString());
			}
		}
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub SimpleExample se = new SimpleExample(); // se.createOntModel();
	 * OntModel onmo = null; try { onmo = se.CreatOntoModel(); } catch
	 * (ClassNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * // access every subclass of a class OntResource mor = onmo
	 * .getOntResource("http://www.w3.org/2002/07/owl#Thing"); if
	 * (mor.canAs(OntClass.class)) { OntClass rootClass =
	 * mor.as(OntClass.class); ExtendedIterator<OntClass> it =
	 * rootClass.listSubClasses(); while (it.hasNext()) { OntClass childClass =
	 * it.next(); System.out.println("ChildCC = " + childClass.getURI()); } } //
	 * access all the Object Property ExtendedIterator<ObjectProperty> it =
	 * onmo.listObjectProperties(); while (it.hasNext()) { ObjectProperty
	 * subprop = it.next(); System.out.println("ChildOP = " + subprop.getURI());
	 * } // access all the Data Property ExtendedIterator<DatatypeProperty> it1
	 * = onmo.listDatatypeProperties(); while (it1.hasNext()) { DatatypeProperty
	 * subprop = it1.next(); System.out.println("ChildDP = " +
	 * subprop.getURI()); } // access every individual of a class
	 * se.CreateandGetRes(PROMOTER_PREFIX + "#temp1", PROMOTER_PREFIX +
	 * "#Promoter", onmo, 0); se.CreateandGetRes(OGO_PREFIX + "#gene1",
	 * OGO_PREFIX + "#Gene", onmo, 0); se.CreateandGetRes(OGO_PREFIX + "#pubm1",
	 * OGO_PREFIX + "#Pubmed", onmo, 0); se.addObjProperty(onmo, PROMOTER_PREFIX
	 * + "#temp1", PROMOTER_PREFIX + "#isBelongedTo", OGO_PREFIX + "#gene1");
	 * se.modifyProperty(onmo, PROMOTER_PREFIX + "#temp1", PROMOTER_PREFIX +
	 * "#hasReference", OGO_PREFIX + "#pubm1"); se.modifyProperty(onmo,
	 * OGO_PREFIX + "#pubm1", PROMOTER_PREFIX + "#Link", "KILL"); //
	 * se.testListIndividual(onmo, PROMOTER_PREFIX+"#Promoter"); //
	 * se.testListIndividual(onmo, OGO_PREFIX+"#Pubmed"); //
	 * se.testListIndividual(onmo, OGO_PREFIX+"#Gene"); // OntResource ose = //
	 * se.CreateandGetRes(PROMOTER_PREFIX+"#Promoter_HS_PTBP1", //
	 * PROMOTER_PREFIX+"#Promoter", onmo, 0); // if
	 * (ose.canAs(Individual.class)){ // Individual osei =
	 * ose.as(Individual.class); // //access the RDF 3-mer // StmtIterator stmtI
	 * = osei.listProperties(); // while(stmtI.hasNext()){ // Statement stmt =
	 * stmtI.nextStatement(); // Resource subject = stmt.getSubject(); // get
	 * the subject // Property predicate = stmt.getPredicate(); // get the
	 * predicate // RDFNode object = stmt.getObject(); // get the object //
	 * System
	 * .out.println(subject.toString()+"->"+predicate.toString()+"->"+object
	 * .toString()); // } // } // se.Query_Gene("Gene"); }
	 */
}
