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
import java.util.ArrayList;
import java.util.List;

import cn.edu.jlu.ccst.model.Promoter;


/**
 * @author Woden
 * This class is used to read resource from EPD dataset to ontology
 */
public class ReadEPD {
	
	public List<String> getPromoters(String addr){
		File file = new File(addr);
		List<String> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			Promoter temppromoter;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if(line.startsWith("ID")){
					temppromoter = new Promoter();
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

	}

}
