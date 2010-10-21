/**
 * some simple examples to practice jena
 */
package jlu.edu.cn.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;

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
	private static final String uriOntology = "file:D:/My Document/ontologies/promoter.owl"; // Where the ontology file is located in your pc
	private PersistentOntology po = new PersistentOntology();
	private List<String[]> importList = new ArrayList();
	private String[] nss = {"http://um.es/eco.owl",
			"http://um.es/go.owl",
			"http://um.es/ncbi.owl",
			"http://miuras.inf.um.es/ontologies/OGO.owl"};
	private String[] add = {"file:D:/My Document/ontologies/eco.owl",
			"file:D:/My Document/ontologies/go.owl",
			"file:D:/My Document/ontologies/ncbi.owl",
			"file:D:/My Document/ontologies/OGO.owl"};

	
	public OntModel createOntModel(){
		
		OntModel model=null;
		boolean deleteDB = false; //If true the persistent model will be deleted.
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
	public OntModel loadDB2nd() {
		// use the model maker to get the base model as a persistent model
		// strict=false, so we get an existing model by that name if it exists
		// or create a new one
		IDBConnection conn  = new DBConnection( DB_URL, DB_USER, DB_PASSWD, TYPE_DB );
		ModelMaker maker = ModelFactory.createModelRDBMaker( conn );

		// now we plug that base model into an ontology model that also uses
		// the given model maker to create storage for imported models
		OntModel m = ModelFactory.createOntologyModel( po.getModelSpec( maker ));
		// now load the source document, which will also load any imports
//		addImports(m); //if the ontology doesn¡¯t import any other ontology this is not needed
//		m.read( source );
		
		return m;
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
		importList.add(add);
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
//		se.createOntModel();
		OntModel om = se.loadDB2nd();
		OntResource or = om.getOntResource("http://www.w3.org/2002/07/owl#Thing");
		System.out.println("Over!");

	}

}
