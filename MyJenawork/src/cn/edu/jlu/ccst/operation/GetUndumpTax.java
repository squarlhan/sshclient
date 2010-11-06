package cn.edu.jlu.ccst.operation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntResource;

import cn.edu.jlu.ccst.example.SimpleExample;
import cn.edu.jlu.ccst.model.*;

public class GetUndumpTax {
	
	/**
	 * Get the NCBI ID of the species from the database I Build
	 * @param sn
	 * @return
	 */
	public String searchID(String sn){
		Connection con = null;
	    Statement stmt = null;
	    String url = "jdbc:mysql://localhost/tempdata";
	    String user = "root";
	    String pwd = "root";
	    String result = null;
	    try {       
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          con = DriverManager.getConnection(url,user,pwd);
	          stmt = con.createStatement();
	          String str = "	"+sn+"	";
			  String sql1 = "select ID from taxonomy where Name='" + str + "';";

			  ResultSet rs = stmt.executeQuery(sql1);
			  if (rs.next()) {
				  result = rs.getString(1).trim();
			  }
			  con.close();
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
        
        
        return result;
    }
	/**
	 * Read the file and return undump taxonomy list
	 * @param addr
	 * @return
	 */
	public List<Taxonomy> dealwithfile(String addr, String saveaddr){
		File file = new File(addr);
		List<Taxonomy> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Taxonomy taxonomy = new Taxonomy();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("   ");
				if(lines[0].trim().equalsIgnoreCase("OS")){
					taxonomy = new Taxonomy();					
					String taxname = lines[1].trim().substring(0, lines[1].trim().length()-1);
					taxonomy.setName(taxname);
					int fnind = lines[1].indexOf('(');
					if(fnind!=-1){
						taxname = lines[1].substring(0, fnind).trim();
					}
					String taxid = this.searchID(taxname);
					taxonomy.setId(taxid);
					Boolean flag = false;
                    for(Taxonomy t:result){
                    	if(result.size()==0){
                    		break;
                    	}
                    	if(t == null || t.getId() == null){
                    		System.out.println("tttttttt");
                    	}
                    	if(taxid == null){
                    		System.out.println(taxname);
                    	}
                    	if(t.getId().equals(taxid.trim())){
                    		flag = true;
                    		break;
                    	}
                    }
                    if(!flag){
                    	result.add(taxonomy);
                    }
				}
				
				
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("promoter:"+result.size());
		
		try {
			File mysave = new File(saveaddr);
			if (mysave.exists()) {
				mysave.delete();
				if (mysave.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (mysave.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}
			

			BufferedWriter output = new BufferedWriter(new FileWriter(mysave));
			for(int i=0;i<=result.size()-1;i++){
				output.write(result.get(i).getId()+"\t"+result.get(i).getName()+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
    /**
     * Return a taxonomy tree from tax to root
     * @param tax
     * @return
     */
	public List<Taxonomy> GetTaxTree(Taxonomy tax){
		List<Taxonomy> result = new ArrayList();
		Connection con = null;
	    Statement stmt = null;
	    String url = "jdbc:mysql://localhost/tempdata";
	    String user = "root";
	    String pwd = "root";
		result.add(tax);
        String papid = "";
        String papname = "";
		
		try {       
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          con = DriverManager.getConnection(url,user,pwd);
	          stmt = con.createStatement();		 
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
		
		while (!tax.getId().trim().equals("1")) {
			ResultSet rs;
			String sql1 = "select dad_id from taxdad where tax_id='" + tax.getId() + "	" + "';";	
			
			try {
				rs = stmt.executeQuery(sql1);
				if (rs.next()) {
					papid = rs.getString(1).trim();
				}
				String sql2 = "select Name from taxonomy where ID='" + papid +"	" + "';";
				
				rs = stmt.executeQuery(sql2);
				if (rs.next()) {
					papname = rs.getString(1).trim();
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Taxonomy newtax = new Taxonomy(papid, papname);
			result.add(newtax);
			tax = newtax;
//			System.out.println("New One: "+result.size()+":"+papid+" <==> "+papname);  
		}
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Get all the species of EPD from a preprocessed file
	 * @param addr
	 * @return
	 */
	public List<Taxonomy> GetEpdTax(String addr){
		List<Taxonomy> result = new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Taxonomy taxonomy;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("	");
				taxonomy = new Taxonomy(lines[0], lines[1]);
				result.add(taxonomy);
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Add taxonomy information to Ontology
	 * @param tasxes
	 */
    public void GenerateNewTax(List<Taxonomy> taxes){
    	SimpleExample se = new SimpleExample();
    	List<Taxonomy> newtaxes = new ArrayList();
    	String labeluri = "http://www.w3.org/2000/01/rdf-schema#label";
		OntModel onmo = null;
        try {
      	    onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	for(Taxonomy tax:taxes){
    		String newuri = se.NCBI_PREFIX+"#NCBI_"+tax.getId().trim();
    		OntResource myRes = onmo.getOntResource(newuri);
    		if(myRes==null){   			 
    			int count =0;
    			OntClass c1 = onmo.createClass(newuri);
    			Individual i1 = onmo.createIndividual(newuri, onmo.getOntClass(newuri));
    			c1.addLabel(tax.getName().trim(), "EN");
    			i1.addLabel(tax.getName().trim(), "EN");
    			count++;
    			List<OntClass> classtree = new ArrayList();
    			classtree.add(c1);
    			newtaxes = this.GetTaxTree(tax);
    			for(int i = 1; i<=newtaxes.size()-1; i++){
    				String newtaxuri = se.NCBI_PREFIX+"#NCBI_"+newtaxes.get(i).getId().trim();
    				OntResource tempRes = onmo.getOntResource(newtaxuri);
    				if(tempRes == null){
    					OntClass ctemp = onmo.createClass(newtaxuri);
    					Individual itemp = onmo.createIndividual(newtaxuri, onmo.getOntClass(newtaxuri));
    					ctemp.addLabel(newtaxes.get(i).getName().trim(), "EN");
    					itemp.addLabel(newtaxes.get(i).getName().trim(), "EN");
    					count++;
    					classtree.add(ctemp);
    				}else{
    					OntResource fatherRes = onmo.getOntResource(se.NCBI_PREFIX+"#NCBI_"+newtaxes.get(i+1).getId().trim());
    					if(fatherRes!=null&&fatherRes.canAs(OntClass.class)){
    						OntClass fatherClass = fatherRes.as(OntClass.class);
    						classtree.add(fatherClass);
    					}
    					break;
    				}
    			}
    			
    			for(int i = 0; i<=classtree.size()-2;i++){
    				classtree.get(i+1).addSubClass(classtree.get(i));
    			}
    			System.out.println("New One: "+count+":"+tax.getId()+" <==> "+tax.getName()); 
			}
    	}
	}
    /**
     * Create individual for each class and add label
     * @param taxes
     */
    public void GenerateNewIdv(List<Taxonomy> taxes){
    	SimpleExample se = new SimpleExample();
    	List<Taxonomy> newtaxes = new ArrayList();
    	String labeluri = "http://www.w3.org/2000/01/rdf-schema#label";
		OntModel onmo = null;
        try {
      	    onmo = se.loadDB2nd();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	for(Taxonomy tax:taxes){   		
    		newtaxes = this.GetTaxTree(tax);
    		for(int i = 0; i<=newtaxes.size()-2;i++){
    			String newuri = se.NCBI_PREFIX+"#NCBI_"+newtaxes.get(i).getId().trim();
    			String papuri = se.NCBI_PREFIX+"#NCBI_"+newtaxes.get(i+1).getId().trim();
    			Individual itemp = onmo.createIndividual(newuri, onmo.getOntClass(newuri));
    			itemp.addLabel(newtaxes.get(i).getName().trim(), "EN");
    			//OntClass ctemp = (OntClass) se.CreateandGetRes(newuri, papuri, onmo, 3);
    			//ctemp.addLabel(newtaxes.get(i).getName().trim(), "EN");
    		}			
    	}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetUndumpTax myobj = new GetUndumpTax();
		//myobj.dealwithfile("epd104.dat", "alltax.txt");
		List<Taxonomy> rs = myobj.GetEpdTax("alltax.txt");
		myobj.GenerateNewIdv(rs);
		System.out.println("END!");

	}

}
