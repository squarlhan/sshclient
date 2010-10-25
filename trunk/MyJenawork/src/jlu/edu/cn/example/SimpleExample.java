/**
 * some simple examples to practice jena
 */
package jlu.edu.cn.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * @author Woden
 * 
 */
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
	private static final String uriOntology = "file:D:/My Document/ontologies/promoter.owl"; // Where the ontology file is located in your pc
	
	private PersistentOntology po = new PersistentOntology();
	private List<String[]> importList = new ArrayList();
	private String[] nss = {OGO_PREFIX,
			"file:D:/My Document/ontologies/OGO.owl"};
	private String[] nss1 = {ECO_PREFIX,
	        "file:D:/My Document/ontologies/eco_punned.owl"};
	private String[] nss2 = {GO_PREFIX,
	        "file:D:/My Document/ontologies/go_punned.owl"};
	private String[] nss3 = {NCBI_PREFIX,
	        "file:D:/My Document/ontologies/ncbi_punned.owl"};

	
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
	
	//Get the ontology from the Database
	public OntModel loadDB2nd() throws ClassNotFoundException {
		Class.forName (DB_Driver);                  // Load the Driver
		
		ModelMaker maker = po.getRDBMaker( DB_URL+DB_SCHEMA, DB_USER, DB_PASSWD, TYPE_DB, false );
		Model base = maker.createModel( uriOntology, false );
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleExample se = new SimpleExample();
		OntModel onmo = null;
        try {
        	onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OntResource mor = onmo.getOntResource("http://www.w3.org/2002/07/owl#Thing");		
		if (mor.canAs(OntClass.class)){
			OntClass rootClass = mor.as(OntClass.class);
			ExtendedIterator<OntClass> it = rootClass.listSubClasses();
			while(it.hasNext()){
				OntClass childClass = it.next();
				System.out.println("ChildCC = "+childClass.getURI());
			}
		}
		ExtendedIterator<ObjectProperty> it = onmo.listObjectProperties();
		while (it.hasNext()) {
			ObjectProperty subprop = it.next();
			System.out.println("ChildOP = " + subprop.getURI());
		}
		ExtendedIterator<DatatypeProperty> it1 = onmo.listDatatypeProperties();
		while (it1.hasNext()) {
			DatatypeProperty subprop = it1.next();
			System.out.println("ChildDP = " + subprop.getURI());
		}
		
		System.out.println("Over!");
		//onmo.createIndividual(PROMOTER_PREFIX+"#temp1", onmo.getOntClass(PROMOTER_PREFIX+"#Promoter"));
		OntResource mor1 = onmo.getOntResource("http://miuras.inf.um.es/ontologies/promoter.owl#Promoter");
		if (mor1.canAs(OntClass.class)){
			OntClass rootClass = mor1.as(OntClass.class);
			ExtendedIterator<? extends OntResource> it2 = rootClass.listInstances();
			while(it2.hasNext()){
				OntResource tt = it2.next();
				Individual ttt = null;
				if (tt.canAs(Individual.class)){
					ttt = tt.as(Individual.class);
				}
				
				System.out.println("ChildInstance = "+ttt.getURI());
			}
		}

	}

}
