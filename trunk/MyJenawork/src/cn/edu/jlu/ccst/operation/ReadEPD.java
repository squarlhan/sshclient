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
	    try {       
	          Class.forName("com.mysql.jdbc.Driver").newInstance();
	          con = DriverManager.getConnection(url,user,pwd);
	          stmt = con.createStatement();
	    } catch (Exception e){
	          // your installation of JDBC Driver Failed
	          e.printStackTrace();
	    }
        String str = "	"+sn+"	";
        String sql1 = "select ID from taxonomy where Name='"+str+"';";
        try{
            ResultSet rs = stmt.executeQuery(sql1);          
            if(rs.next()){  
                return rs.getString(1).trim();
            }
        }catch(Exception e){
           e.printStackTrace();
        }
        return null;
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
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("   ");
				//construct promoter object
				if(lines[0].trim().equalsIgnoreCase("ID")){
					temppromoter = new Promoter();
					temppromoter.setName(lines[1].trim());
				}
				//construct taxonomy object
				if(lines[0].trim().equalsIgnoreCase("OS")){
					taxonomy = new Taxonomy();					
					String taxname = lines[1].trim();
					taxonomy.setName(taxname);
					int fnind = lines[1].indexOf('(');
					if(fnind!=-1){
						taxname = lines[1].substring(0, fnind).trim();
					}
					String taxid = this.searchID(taxname);
					taxonomy.setId(taxid);					
				}
				//construct homology object
				if(lines[0].trim().equalsIgnoreCase("HG")){
					homology = new Homology();
					String[] homos = lines[1].split(";");
					if (homos.length >= 2) {
						homology.setName(homos[1].trim());
						String[] ids = homos[0].trim().split(" ");
						if (this.isNumeric(ids[2])) {
							homology.setId(ids[2]);
						}
					}
				}
				//construct one resource object
				if(lines[0].trim().equalsIgnoreCase("DR")){					
					String[] drs = lines[1].split(";");					
					if (drs.length >= 2&&drs[0].trim()=="RefSeq") {
						resource = new Resource();
						resource.setDataset("DBTSS");
						resource.setLink("http://dbtss.hgc.jp/cgi-bin/home.cgi?NMID=");
						resource.setId(drs[1].trim());
					}
				}
				//construct one reference object
				if(lines[0].trim().equalsIgnoreCase("RN")){								
					reference = new Reference();
				}
				if(lines[0].trim().equalsIgnoreCase("RX")){								
					String[] ids = lines[1].split(";");	
					if (ids.length >= 2&&this.isNumeric(ids[1].trim())) {
						reference.setPubmed(ids[1].trim());
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
		System.out.println("Gene:"+result.size());
		return result;	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ReadEPD re = new ReadEPD();
//		System.out.println(re.searchID("soybean"));
		System.out.println("asdf(asdf)".indexOf('('));
		System.out.println("asdf(asdf)".substring(0, "asdf(asdf)".indexOf('(')).trim());

	}

}
