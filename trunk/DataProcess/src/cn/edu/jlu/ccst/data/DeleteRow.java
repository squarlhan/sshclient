package cn.edu.jlu.ccst.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DeleteRow {

	public List<String> deleteRowsBy(String addr, String resultaddr, String keywords){
		
		List<String> result = new ArrayList();
		List<String> filecontent = new ArrayList();
		File file = new File(addr);
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) 
				{				 
					filecontent.add(line);					
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
		
		try {
			File resultfile = new File(resultaddr);
			if (resultfile.exists()) {
				resultfile.delete();
				if (resultfile.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (resultfile.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(resultfile));
			for(int i=0;i<=filecontent.size()-1;i++){
				if(!filecontent.get(i).contains(keywords)){
					result.add(filecontent.get(i));
					output.write(filecontent.get(i)+"\n");
				}
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Counts:"+result.size());
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DeleteRow dr = new DeleteRow();
		dr.deleteRowsBy("F:/GDS3123.txt", "F:/Gene_GDS3123.txt", "/direction");

	}

}
