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

import cn.edu.jlu.ccst.model.*;


/**
 * @author Woden
 * This class is used to read resource from EPD dataset to ontology
 */
public class ReadEPD {
	
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
	
	public List<Promoter> getPromoters(String addr){
		File file = new File(addr);
		List<Promoter> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Promoter temppromoter = new Promoter();
			Taxonomy taxonomy = new Taxonomy();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				String[] lines = line.split("   ");
				if(lines[0].trim().equalsIgnoreCase("ID")){
					temppromoter = new Promoter();
					temppromoter.setName(lines[1]);
				}
				if(lines[0].trim().equalsIgnoreCase("OS")){
					taxonomy = new Taxonomy();
					taxonomy.setName(lines[1]);
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
		ReadEPD re = new ReadEPD();
		System.out.println(re.searchID("soybean"));

	}

}
