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
	
	public static final String DB_URL = "jdbc:mysql://localhost:3306/"; // URL of the database
	public static final String DB_SCHEMA = "promoter"; // name of the database schema.
	public static final String DB_USER = "root"; // database user name
	public static final String DB_PASSWD = "root"; // database user password
	public static final String TYPE_DB = "MySQL"; // database type
	public static final String DB_Driver = "com.mysql.jdbc.Driver";

	public static final String PROMOTER_PREFIX = "http://miuras.inf.um.es/ontologies/promoter.owl";
	public static final String OGO_PREFIX = "http://miuras.inf.um.es/ontologies/OGO.owl";
	public static final String ECO_PREFIX = "http://um.es/eco.owl";
	public static final String GO_PREFIX = "http://um.es/go.owl";
	public static final String NCBI_PREFIX = "http://um.es/ncbi.owl";
	private static final String uriOntology = "file:E:/promoter/ontologies/promoter.owl"; // Where the ontology file is located in your pc
	
	private PersistentOntology po;
	public PersistentOntology getPo() {
		return po;
	}

	@javax.annotation.Resource
	public void setPo(PersistentOntology po) {
		this.po = po;
	}

	private List<String[]> importList = new ArrayList();

	private String[] nss = {OGO_PREFIX,
			"file:E:/promoter/ontologies/OGO.owl"};
	private String[] nss1 = {ECO_PREFIX,
	        "file:E:/promoter/ontologies/eco_punned.owl"};
	private String[] nss2 = {GO_PREFIX,
	        "file:E:/promoter/ontologies/go_punned.owl"};
	private String[] nss3 = {NCBI_PREFIX,
	        "file:E:/promoter/ontologies/ncbi_punned.owl"};

	
	public OntModel createOntModel(){
		
		OntModel model=null;
		boolean deleteDB = true; //If true the persistent model will be deleted.
			try{
				Class.forName(DB_Driver);// Load the Driver
				ModelMaker mm = po.getRDBMaker(DB_URL+DB_SCHEMA,DB_USER, DB_PASSWD,TYPE_DB,deleteDB);
				model=loadDB(mm,uriOntology);
				System.out.println("uriOntology="+uriOntology);
				
			}catch(Exception e){
				System.out.println("Error: when loading the ontology");
				e.printStackTrace();
			}
			return model;
		}
	
	/**
	 * Get the ontology from the Database
	 * @return
	 * @throws ClassNotFoundException
	 */
	public OntModel loadDB2nd() throws ClassNotFoundException {
		Class.forName (DB_Driver);                  // Load the Driver
		
		ModelMaker maker = po.getRDBMaker( DB_URL+DB_SCHEMA, DB_USER, DB_PASSWD, TYPE_DB, false );
		Model base = maker.openModel( uriOntology, false );
		OntModelSpec spec = new OntModelSpec( OntModelSpec.OWL_MEM );
        spec.setImportModelMaker( maker );       
		OntModel m = ModelFactory.createOntologyModel(spec, base );		
		
//		IDBConnection conn  = new DBConnection( DB_URL+DB_SCHEMA, DB_USER, DB_PASSWD, TYPE_DB );
//		ModelMaker maker = ModelFactory.createModelRDBMaker( conn );
//		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
//        spec.setImportModelMaker(maker);
//        Model model = maker.createModel( uriOntology, false );
//        OntModel m = ModelFactory.createOntologyModel(spec,model);	
		
		return m;
//		conn.close();
	}
	
	private OntModel loadDB( ModelMaker maker, String source ) {
		// use the model maker to get the base model as a persistent model
		// strict=false, so we get an existing model by that name if it exists
		// or create a new one
		Model base = maker.createModel( source, false );

		// now we plug that base model into an ontology model that also uses
		// the given model maker to create storage for imported models
		OntModel m = ModelFactory.createOntologyModel( po.getModelSpec( maker ), base );
		// now load the source document, which will also load any imports
		addImports(m); //if the ontology doesn¡¯t import any other ontology this is not needed
		m.read( source );
		
		return m;
	}

	private void addImports(OntModel model){
		importList.add(nss);
		importList.add(nss1);
		importList.add(nss2);
		importList.add(nss3);
		if((importList!=null)&&(!importList.isEmpty())){ //importList is a two dimension array where position [0] contains the namespace of the imported ontology and position [1] contains the location of the imported ontology file;
			OntDocumentManager dm = model.getDocumentManager();
			Iterator<String[]> itImp = importList.iterator();
			while(itImp.hasNext()){
				String[] importOntology = itImp.next();
				dm.addAltEntry(importOntology[0],importOntology[1]);
			}
			dm.setProcessImports(true);
			dm.loadImports(model);
		}
	}
	/**
	 * delete resource by uri
	 * @param onmo
	 * @param myuri
	 */
    public void deleteResource(OntModel onmo, String myuri){
    	 //access every individual of a class
	    OntResource or = onmo.getOntResource(myuri);	
		if (or!=null){
			or.remove();
		}else return;
    }
    /**
     * create new Object Property statement for a individual
     * @param onmo
     * @param sinduri
     * @param propuri
     * @param oinduri
     * @return
     */
    public Individual addObjProperty(OntModel onmo, String sinduri, String propuri,String oinduri){
    	Individual sind = null;
    	ObjectProperty prop = null;
    	Individual oind = null;
    	OntResource or1 = onmo.getOntResource(sinduri);	
		if (or1!=null&&or1.canAs(Individual.class)){
			sind = or1.as(Individual.class);
		}else return null;
		OntResource or2 = onmo.getOntResource(propuri);	
		if (or2!=null&&or2.canAs(ObjectProperty.class)){
			prop = or2.as(ObjectProperty.class);
		}else return null;
		OntResource or3 = onmo.getOntResource(oinduri);	
		if (or3!=null&&or3.canAs(Individual.class)){
			oind = or3.as(Individual.class);
		}else return null;
		sind.addProperty(prop, oind);
		return sind;
    }
    /**
     * Add value to Data Property for a individual
     * @param onmo
     * @param sinduri
     * @param propuri
     * @param valueuri
     * @return
     */
    public Individual addDataProperty(OntModel onmo, String sinduri, String propuri,String valueuri){
    	Individual sind = null;
    	DatatypeProperty prop = null;
    	OntResource or1 = onmo.getOntResource(sinduri);	
		if (or1!=null&&or1.canAs(Individual.class)){
			sind = or1.as(Individual.class);
		}else return null;
		OntResource or2 = onmo.getOntResource(propuri);	
		if (or2!=null&&or2.canAs(DatatypeProperty.class)){
			prop = or2.as(DatatypeProperty.class);
		}else return null;
		BaseDatatype bd=null;
		if (prop.getRange() != null){
			bd = new BaseDatatype(prop.getRange().toString());
			if (bd != null){
				sind.addProperty(prop,valueuri,bd);
			}else{
				sind.addProperty(prop,valueuri);
			}
		}
		return sind;
    }
      
    /**
     * create or get resource if the uri exist. 0: individual 1: objectproperty 2: dataproerty 3:class
     */
    public OntResource CreateandGetRes(String newuri, String typeuri, OntModel onmo, int Type){
    	OntResource newres = null; 
    	newres = onmo.getOntResource(newuri);
    	if(newres != null)return newres;
		else {
			switch (Type) {
			case 0:
				newres = onmo.createIndividual(newuri, onmo.getOntClass(typeuri));
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
				if(fatherRes!=null&&fatherRes.canAs(OntClass.class)){
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
    public Individual CreateandGetIdv(String newuri, String typeuri, OntModel onmo){
    	Individual newres = null; 
    	newres = onmo.getIndividual(newuri);
    	if(newres != null )return newres;
		else {
			
				newres = onmo.createIndividual(newuri, onmo.getOntClass(typeuri));		
			}
		return newres;
    }
    /**
     * modefi the property of a individual
     * @param onmo
     * @param idvuri
     * @param dpuri
     * @param dpvalue
     * @return
     */
    public Individual modifyProperty(OntModel onmo, String idvuri, String dpuri, String dpvalue){
    	Individual indi = onmo.getIndividual(idvuri);
    	Property pro = onmo.getProperty(dpuri);
    	Individual objindi = onmo.getIndividual(dpvalue);
    	RDFNode rdfNode = null;
    	rdfNode = objindi != null?objindi:onmo.createLiteral(dpvalue);        
        indi.setPropertyValue(pro, rdfNode);
        return indi;
    }
    /**
     * Do SPARQL Query
     * @param onmo
     * @param querystatement
     * @return
     */
    public ResultSet doquery(OntModel onmo, String keyword){
    	//Culicinae
    	String querystatement = 
    	    "PREFIX rdfsch: <http://www.w3.org/2000/01/rdf-schema#>" +
			"SELECT ?x " +
			"WHERE {" +
			"      ?x rdfsch:label \""+keyword+"\" . " +
			"      }";

		Query query = QueryFactory.create(querystatement);
		QueryExecution qe = QueryExecutionFactory.create(query, onmo);
		ResultSet results = qe.execSelect();
		ResultSetFormatter.out(System.out, results, query);
		qe.close();
		return results;
    }
    /**
     * do reasoner for the ontology
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
		Resource resource_2 = infmodel.getResource(PROMOTER_PREFIX	+ "#Promoter");
		ExtendedIterator iter_2 = infmodel.listStatements(resource_2, null,	(RDFNode) null);
		while (iter_2.hasNext()) {
			System.out.println(iter_2.next());
		}
		return infmodel;
	}
	/**
	 * For testing, list all the individuals and the 3-mers of a class
	 * @param onmo
	 * @param classuri
	 */
	public void testListIndividual(OntModel onmo, String classuri){
		OntResource mor1 = onmo.getOntResource(classuri);	
		if (mor1.canAs(OntClass.class)){
			OntClass rootClass = mor1.as(OntClass.class);
			ExtendedIterator<? extends OntResource> it2 = rootClass.listInstances();
			while(it2.hasNext()){
				OntResource tt = it2.next();
				Individual ttt = null;
				if (tt.canAs(Individual.class)){
					ttt = tt.as(Individual.class);
					 //access the RDF 3-mer 
					StmtIterator stmtI = ttt.listProperties();
					while(stmtI.hasNext()){
						Statement stmt = stmtI.nextStatement();
						Resource subject   = stmt.getSubject();   // get the subject
						Property predicate = stmt.getPredicate(); // get the predicate
						RDFNode object = stmt.getObject();    // get the object
						System.out.println(subject.toString()+"->"+predicate.toString()+"->"+object.toString());
					}
				}
				
				System.out.println("ChildInstance = "+ttt.toString());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleExample se = new SimpleExample();
//        se.createOntModel();
		OntModel onmo = null;
        try {
        	onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//access every subclass of a class
		/*OntResource mor = onmo.getOntResource("http://www.w3.org/2002/07/owl#Thing");		
		if (mor.canAs(OntClass.class)){
			OntClass rootClass = mor.as(OntClass.class);
			ExtendedIterator<OntClass> it = rootClass.listSubClasses();
			while(it.hasNext()){
				OntClass childClass = it.next();
				System.out.println("ChildCC = "+childClass.getURI());
			}
		}
		//access all the Object Property
		ExtendedIterator<ObjectProperty> it = onmo.listObjectProperties();
		while (it.hasNext()) {
			ObjectProperty subprop = it.next();
			System.out.println("ChildOP = " + subprop.getURI());
		}
		//access all the Data Property
		ExtendedIterator<DatatypeProperty> it1 = onmo.listDatatypeProperties();
		while (it1.hasNext()) {
			DatatypeProperty subprop = it1.next();
			System.out.println("ChildDP = " + subprop.getURI());
		}
	    //access every individual of a class
		se.CreateandGetRes(PROMOTER_PREFIX+"#temp1", PROMOTER_PREFIX+"#Promoter", onmo, 0);
		se.CreateandGetRes(OGO_PREFIX+"#gene1", OGO_PREFIX+"#Gene", onmo, 0);
		se.CreateandGetRes(OGO_PREFIX+"#pubm1", OGO_PREFIX+"#Pubmed", onmo, 0);
		se.addObjProperty(onmo, PROMOTER_PREFIX+"#temp1", PROMOTER_PREFIX+"#isBelongedTo", OGO_PREFIX+"#gene1");
		se.modifyProperty(onmo, PROMOTER_PREFIX+"#temp1", PROMOTER_PREFIX+"#hasReference", OGO_PREFIX+"#pubm1");
		se.modifyProperty(onmo, OGO_PREFIX+"#pubm1", PROMOTER_PREFIX+"#Link", "KILL");*/
//		se.testListIndividual(onmo, PROMOTER_PREFIX+"#Promoter");
//		se.testListIndividual(onmo, OGO_PREFIX+"#Pubmed");	
//		se.testListIndividual(onmo, OGO_PREFIX+"#Gene");	
//		OntResource ose = se.CreateandGetRes(PROMOTER_PREFIX+"#Promoter_HS_PTBP1", PROMOTER_PREFIX+"#Promoter", onmo, 0);
//		if (ose.canAs(Individual.class)){
//			Individual osei = ose.as(Individual.class);
//			 //access the RDF 3-mer 
//			StmtIterator stmtI = osei.listProperties();
//			while(stmtI.hasNext()){
//				Statement stmt = stmtI.nextStatement();
//				Resource subject   = stmt.getSubject();   // get the subject
//				Property predicate = stmt.getPredicate(); // get the predicate
//				RDFNode object = stmt.getObject();    // get the object
//				System.out.println(subject.toString()+"->"+predicate.toString()+"->"+object.toString());
//			}
//		}
		se.doquery(onmo, "Culicinae");
	}

}
