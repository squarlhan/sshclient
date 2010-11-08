package cn.edu.jlu.ccst.operation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import cn.edu.jlu.ccst.model.GO;
import cn.edu.jlu.ccst.model.Gene;
import cn.edu.jlu.ccst.model.Homology;
import cn.edu.jlu.ccst.model.Keyword;
import cn.edu.jlu.ccst.model.Promoter;
import cn.edu.jlu.ccst.model.Reference;
import cn.edu.jlu.ccst.model.Resource;
import cn.edu.jlu.ccst.model.Taxonomy;
import cn.edu.jlu.ccst.model.mRNA;

public class FilterDB {
	/**
	 * get all the genes relative to the promoter and save them in a table
	 * @param addr
	 */
	public void generateepdgene(String addr){
		
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
        
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			String epd_id = "";
			String go_id = "";
			String gene_id = "";
			String gene_name = "";
			String rna_acc = "";
			String pro_acc = "";
			List<String> embls = new ArrayList();
			List<String> refseqs = new ArrayList();
			while ((line = br.readLine()) != null) {			
				line = line.trim();
				String[] lines = line.split("   ");

				if(lines[0].trim().equalsIgnoreCase("AC")){
					epd_id = "";
					go_id = "";
					gene_id = "";
					gene_name = "";
					rna_acc = "";
					pro_acc = "";
					embls = new ArrayList();
					refseqs = new ArrayList();
					
					epd_id = lines[1].trim().substring(0, lines[1].trim().length()-1);
				}

				if(lines[0].trim().equalsIgnoreCase("DR")){					
					String[] drs = lines[1].split(";");	
					if (drs.length >= 2&&drs[0].trim().equalsIgnoreCase("EMBL")){
						embls.add(drs[1].trim());
					}
					if (drs.length >= 2&& drs[0].trim().equalsIgnoreCase("RefSeq")) {
						refseqs.add(drs[1].trim().substring(0,drs[1].trim().length() - 1));
					}
				}
				if(lines[0].trim().equalsIgnoreCase("//")){
					if(refseqs.size()>0){
						for(String ref_acc: refseqs){
							String sql1 = "select distinct geneid, rna_acc, pro_acc from epdrefseq " +
									"where rna_acc = '"+ref_acc+"';";
							ResultSet rs;
							rs = stmt.executeQuery(sql1);
							if (rs.next()) {
								gene_id = rs.getString(1).trim();
								rna_acc = rs.getString(2).trim();
								pro_acc = rs.getString(3).trim();
							}
							String sql2 = "select distinct go_id, go_term from epdgo " +
							        "where geneid = '"+gene_id+"';";
							rs = stmt.executeQuery(sql2);
							if (rs.next()) {
								go_id = rs.getString(1).trim();
								gene_name = rs.getString(2).trim();
							}else{
								go_id = "-";
								gene_name = "-";
							}
							String sql3 = "insert into  epddata go_id values( " +
							        "'"+epd_id+"',"+
							        "'"+gene_id+"',"+
							        "'"+rna_acc+"',"+
							        "'"+pro_acc+"',"+
							        "'"+go_id+"',"+
							        "'"+gene_name+"')";
							stmt.execute(sql3);							  
						}
					}
				}else{
					for(String embl: embls){
						ResultSet rs;
						String sql0 = "select count(distinct geneid, rna_acc, pro_acc) " +
								"from epdgene where rna_acc = '"+embl+"';";
						rs = stmt.executeQuery(sql0);
						int num = 0;
						if (rs.next()) {
							num = rs.getInt(1);
						}
						if(num == 1){
							String sql1 = "select distinct geneid, rna_acc, pro_acc from epdgene " +
							        "where rna_acc = '"+embl+"';";
							rs = stmt.executeQuery(sql1);
							if (rs.next()) {
								gene_id = rs.getString(1).trim();
								rna_acc = rs.getString(2).trim();
								pro_acc = rs.getString(3).trim();
							}
							String sql2 = "select distinct go_id, go_term from epdgo " +
							        "where geneid = '"+gene_id+"';";
							rs = stmt.executeQuery(sql2);
							if (rs.next()) {
								go_id = rs.getString(1).trim();
								gene_name = rs.getString(2).trim();
							}else{
								go_id = "-";
								gene_name = "-";
							}
							
						}else{
							gene_id = embl;
							go_id = "-";
							gene_name = "-";
							rna_acc = "-";
							pro_acc = "-";
						}
						String sql3 = "insert into  epddata go_id values( " +
				                 "'"+epd_id+"',"+
				                 "'"+gene_id+"',"+
				                 "'"+rna_acc+"',"+
				                 "'"+pro_acc+"',"+
				                 "'"+go_id+"',"+
				                 "'"+gene_name+"')";
				        stmt.execute(sql3);	
					}
				}
			}
			br.close();
			insr.close();

			stmt.close();
			con.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("promoter:");
	}

	/**
	 * find the gene and go with epd species to a new table
	 * @param alltax
	 */
	public void filtergeneandgo(List<Taxonomy> alltax){
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
	    for(Taxonomy tax: alltax){
	    	String sql1 = "insert into epdgene " +
	    			"select distinct tax_id,geneid,rna_acc, pro_acc, gen_acc from gene2acc " +
	    			"where tax_id = '"+tax.getId()+"' and gen_acc!='-';";
	    	String sql2 = "insert into epdgo " +
			        "select distinct tax_id,geneid,go_id,go_term from gene2go " +
			        "where tax_id = '"+tax.getId()+"' ;";
	    	String sql3 = "insert into epdrefseq " +
	                "select distinct tax_id,geneid,rna_acc, pro_acc, gen_acc from gene2refseq " +
	                "where tax_id = '"+tax.getId()+"' and gen_acc!='-';";
	    	try {
				stmt.execute(sql1);
				stmt.execute(sql2);
				stmt.execute(sql3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    try {
	    	stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FilterDB myobj = new FilterDB();
		myobj.filtergeneandgo(myobj.GetEpdTax("alltax.txt"));
		System.out.println("//");
	}

}
