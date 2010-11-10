/**
 * 
 */
package cn.edu.jlu.ccst.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cn.edu.jlu.ccst.model.*;


/**
 * @author Woden
 * This class is used to read resource from EPD dataset to ontology
 */
public class ReadEPD {
	/**
	 * judge the string whether is number
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str){
	     Pattern pattern = Pattern.compile("[0-9]*");
	     return pattern.matcher(str).matches();   
	}
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
			  String sql1 = "select tax_id from epdtax where tax_name='" + sn + "';";

			  ResultSet rs = stmt.executeQuery(sql1);
			  if (rs.next()) {
				  result = rs.getString(1).trim();
			  }
			  stmt.close();
	          con.close();
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
        
        
        return result;
    }
	/**
	 * mapping the GO term from the gene_acc
	 * @param gene_acc
	 * @return
	 */
	public List<String> searchGO(String gene_acc){
		Connection con = null;
	    Statement stmt = null;
	    String url = "jdbc:mysql://localhost/tempdata";
	    String user = "root";
	    String pwd = "root";
	    try {       
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          con = DriverManager.getConnection(url,user,pwd);
	          stmt = con.createStatement();
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
	    List<String> result = new ArrayList();
        String sql1 = "select DISTINCT go_id from gene2go where geneid in (" +
        		"select DISTINCT geneid from gene2acc where gen_acc = '"+gene_acc+"');";
        try{
            ResultSet rs = stmt.executeQuery(sql1);          
            if(rs.next()){  
            	result.add(rs.getString(1).trim());
            }
            stmt.close();
            con.close();
        }catch(Exception e){
           e.printStackTrace();
        }
        return result;
    }
	/**
	 * Get all the gene information from DB regarding the promoter
	 * @param epdid
	 * @param tax
	 * @return
	 */
	public List<Gene> searchGene(String epdid, Taxonomy tax){
		List<Gene> result = new ArrayList();
		List<EpdData> dbresult = new ArrayList();
		Connection con = null;
	    Statement stmt = null;
	    String url = "jdbc:mysql://localhost/tempdata";
	    String user = "root";
	    String pwd = "root";
	    try {       
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          con = DriverManager.getConnection(url,user,pwd);
	          stmt = con.createStatement();
			  String sql1 = "select distinct * from epddata where epd_id='" + epdid + "';";

			  ResultSet rs ;
			  rs = stmt.executeQuery(sql1);
			  while (rs.next()) {
				  String eid = rs.getString(1).trim();
				  String gid = rs.getString(2).trim();
				  String rid = rs.getString(3).trim();
				  String pid = rs.getString(4).trim();
				  dbresult.add(new EpdData(eid,gid,rid,pid));
			  }
			  for(EpdData edt : dbresult){
				  boolean flag = true;
				  if(result.size()==0){
					  Gene gene = new Gene();
					  gene.setId(edt.getGeneid());
					  mRNA mrna = new mRNA();
					  mrna.setId(edt.getMrnaid());
					  Protein protein = new Protein();
					  protein.setId(edt.getProteinid());
					  gene.getMrnas().add(mrna);
					  gene.getProteins().add(protein);
					  gene.setTaxonomy(tax);
					  String sql2 = "select distinct go_id from epdgo where geneid='" + gene.getId() + "';";
					  rs = stmt.executeQuery(sql2);
					  while (rs.next()) {
						  GO ngo = new GO(rs.getString(1).trim());
						  gene.getGos().add(ngo);
					  }
					  result.add(gene);
				  }else{
					  for(int i = 0; i<= result.size()-1; i++){
						  if(result.get(i).getId().equals(edt.getGeneid())){
							  mRNA mrna = new mRNA();
							  mrna.setId(edt.getMrnaid());
							  Protein protein = new Protein();
							  protein.setId(edt.getProteinid());
							  result.get(i).getMrnas().add(mrna);
							  result.get(i).getProteins().add(protein);
							  flag = false;
							  break;							  
						  }
					  }
					  if(flag){
						  Gene gene = new Gene();
						  gene.setId(edt.getGeneid());
						  mRNA mrna = new mRNA();
						  mrna.setId(edt.getMrnaid());
						  Protein protein = new Protein();
						  protein.setId(edt.getProteinid());
						  gene.getMrnas().add(mrna);
						  gene.getProteins().add(protein);
						  gene.setTaxonomy(tax);
						  String sql2 = "select distinc go_id from epdgo where geneid='" + gene.getId() + "';";
						  rs = stmt.executeQuery(sql2);
						  while (rs.next()) {
							  GO ngo = new GO(rs.getString(1).trim());
							  gene.getGos().add(ngo);
						  }
						  result.add(gene);
					  }
				  }
			  }
			  
			  stmt.close();
	          con.close();
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
	    
		return result;
	}
	/**
	 * get promoters from the genebank file
	 * @param addr
	 * @return
	 */
	public List<Promoter> getPromoters(String addr){
		File file = new File(addr);
		List<Promoter> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Promoter temppromoter = new Promoter();
			Taxonomy taxonomy = new Taxonomy();
			Homology homology = new Homology();
			Reference reference = new Reference();
			Resource resource = new Resource();
			String epdid = "";
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("   ");
				//construct promoter object
				if(lines[0].trim().equalsIgnoreCase("ID")){
					temppromoter = new Promoter();
					temppromoter.setName(lines[1].trim());					
					result.add(temppromoter);
				}
				//construct epd resource object
				if(lines[0].trim().equalsIgnoreCase("AC")){
					Resource re = new Resource();
					re.setDataset("EPD");					
					epdid = lines[1].trim().substring(0, lines[1].trim().length()-1);
					re.setId(epdid);
					re.setLink("http://www.epd.isb-sib.ch/cgi-bin/get_doc?db=epd&format=nice&entry="+epdid);
					temppromoter.getResources().add(re);
				}
				//construct taxonomy object
				if(lines[0].trim().equalsIgnoreCase("OS")){
					taxonomy = new Taxonomy();					
					String taxname = lines[1].trim().substring(0, lines[1].trim().length()-1);
					taxonomy.setName(taxname);
					String taxid = this.searchID(taxname);
					taxonomy.setId(taxid);					
				}
				//construct homology object
				if(lines[0].trim().equalsIgnoreCase("HG")){
					homology = new Homology();
					String[] homos = lines[1].split(";");
					if (homos.length >= 2) {
						homology.setName(homos[1].trim().substring(0, homos[1].trim().length()-1));
						String[] ids = homos[0].trim().split(" ");
						if (this.isNumeric(ids[2])) {
							homology.setId(ids[2]);
						}
					}
					temppromoter.setHomology(homology);
				}
				//construct one resource object or Gene objects
				if(lines[0].trim().equalsIgnoreCase("DR")){					
					String[] drs = lines[1].split(";");						 					
					// add some genes to the promoter
					/*if (drs.length >= 2&&drs[0].trim().equalsIgnoreCase("EMBL")){
						Gene gene = new Gene();
						String gene_acc = drs[1].trim();
						gene.setId(gene_acc);
						gene.setTaxonomy(taxonomy);
						gene.getPromoters().add(temppromoter);
						temppromoter.getGenes().add(gene);
						List<String> goids = this.searchGO(gene_acc);
						List<GO> gos = new ArrayList();
						for(String goid : goids){
							gos.add(new GO(goid));
						}
						gene.setGos(gos);
					}*/
					if (drs.length >= 2
							&& drs[0].trim().equalsIgnoreCase("RefSeq")) {
						// add some resources to the promoter
						// as the dataset of dbtss only has 3 species in EPD
						if (taxonomy.getId() == "9606"
								|| taxonomy.getId() == "10090") {
							resource = new Resource();
							resource.setDataset("DBTSS");							
							resource.setId(drs[1].trim().substring(0,drs[1].trim().length() - 1));
							resource.setLink("http://dbtss.hgc.jp/cgi-bin/home.cgi?NMID="+resource.getId());
							temppromoter.getResources().add(resource);
						}
						//add mRNA to the promoter
						/*mRNA mrna = new mRNA();
						mrna.setId(drs[1].trim().substring(0, drs[1].trim().length()-1));
						temppromoter.getMrnas().add(mrna);*/
					}
				}
				//construct one reference object
				if(lines[0].trim().equalsIgnoreCase("RN")){								
					reference = new Reference();
					temppromoter.getReferences().add(reference);
				}
				if(lines[0].trim().equalsIgnoreCase("RX")){								
					String[] ids = lines[1].split(";");	
					if (ids.length >= 2&&this.isNumeric(ids[1].trim().substring(0, ids[1].trim().length()-1))) {
						reference.setPubmed(ids[1].trim().substring(0, ids[1].trim().length()-1));
					}
				}
				if(lines[0].trim().equalsIgnoreCase("RA")){
					if(reference.getAuther().isEmpty()){
						reference.setAuther(lines[1].trim());
					}else
						reference.setPubmed(reference.getAuther()+lines[1].trim());				
				}
				if(lines[0].trim().equalsIgnoreCase("RT")){
					if(reference.getTitle().isEmpty()){
						reference.setTitle(lines[1].trim());
					}else
						reference.setTitle(reference.getTitle()+lines[1].trim());				
				}
				if(lines[0].trim().equalsIgnoreCase("RL")){
					if(reference.getLocation().isEmpty()){
						reference.setLocation(lines[1].trim());
					}else
						reference.setLocation(reference.getLocation()+lines[1].trim());				
				}
				//construct some keywords objects
				if(lines[0].trim().equalsIgnoreCase("KW")){								
					String[] kws = lines[1].trim().substring(0, lines[1].trim().length()-1).split(",");
					for(String kw:kws){
						Keyword keyword = new Keyword(kw.trim());
						temppromoter.getKeywords().add(keyword);
					}
				}
				//construct gene and the relative objects
				if(lines[0].trim().equalsIgnoreCase("//")){	
					List<Gene> genes = this.searchGene(epdid,taxonomy);
				    for(Gene gene: genes){
					    temppromoter.getMrnas().addAll(gene.getMrnas());
				    }
				    temppromoter.setGenes(genes);
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
		return result;	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ReadEPD re = new ReadEPD();
//		System.out.println(re.searchID("soybean"));
		ReadEPD re = new ReadEPD();
		List<Promoter> result = re.getPromoters("test.txt");
		System.out.println("Over!");
		
	}

}
