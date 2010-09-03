package cn.edu.jlu.ccst;

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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class Datedealing {

	//获取所有的基因
	private static List<String> getallgene(String addr){
		File file = new File(addr);
		List<String> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) 
				{				 
					result.add(line);					
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
	//获取所有的操纵子和基因的对应数据
	private static List<Operon> getopandge(String addr){
		File file = new File(addr);
		List<Operon> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();				
				if (line.trim().length() >= 1) 
				{
					boolean flag = true;
					String[] lines= line.split("	");
					if(result.size()>=1){
						for(ListIterator it = result.listIterator();it.hasNext();){
							Operon op = (Operon)it.next();
							if(lines[0].trim().equals(op.getId())){
								op.getGeneid().add(lines[1].trim());
								flag = false;
								break;
							}
						}
					    if(flag){
						    Operon newop = new Operon();
						    newop.setId(lines[0].trim());
						    Set<String> genes = new HashSet();
						    genes.add(lines[1].trim());
						    newop.setGeneid(genes);
						    result.add(newop);	
					    }
					}else{
						Operon newop = new Operon();
						newop.setId(lines[0].trim());
						Set<String> genes = new HashSet();
						genes.add(lines[1].trim());
						newop.setGeneid(genes);
						result.add(newop);	
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
		System.out.println("Operon:"+result.size());
		return result;	
	}
	//获取所有的通路和基因的对应数据
	private static List<Pathway> getgeandpa(String addr){
		File file = new File(addr);
		List<Pathway> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();				
				if (line.trim().length() >= 1) 
				{
					boolean flag = true;
					String[] lines= line.split("	");
					if(result.size()>=1){
						for(ListIterator it = result.listIterator();it.hasNext();){
							Pathway pa = (Pathway)it.next();
							if(lines[1].trim().equals(pa.getId())){
								pa.getGeneid().add(lines[0].trim());
								flag = false;
								break;
							}
						}
					if(flag){
						Pathway newpa = new Pathway();
						newpa.setId(lines[1].trim());
						Set<String> genes = new HashSet();
						genes.add(lines[0].trim());
						newpa.setGeneid(genes);
						result.add(newpa);	
					}
					}else{
						Pathway newpa = new Pathway();
						newpa.setId(lines[1].trim());
						Set<String> genes = new HashSet();
						genes.add(lines[0].trim());
						newpa.setGeneid(genes);
						result.add(newpa);	
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
		System.out.println("Pathway:"+result.size());
		return result;	
	}
	
	private static List<String> produceseq(List<Operon> ops, List<String> ges, String resultaddr){
		List<String> seq = new ArrayList();
		for(String geneid:ges){
			boolean flag = false;
			for(Operon op:ops){
				if(op.getGeneid().contains(geneid)){
					if(seq.size()>0){
						if(!seq.contains(op.getId())){
							seq.add(op.getId());				
							//break;
						}
					}else {
						seq.add(op.getId());
					}
					flag = true;
				}
			}
			if(!flag)seq.add(geneid);
		}
		System.out.println("Totel:"+seq.size());
		
		try {
			File result = new File(resultaddr);
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			for(int i=0;i<=seq.size()-1;i++){
				output.write(seq.get(i)+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seq;
	}

	public static void main(String[] args){
//		getallgene("allgene.txt");
//		getopandge("opandge.txt");
//		getgeandpa("geandpa.list");
		produceseq(getopandge("opandge.txt"),getallgene("allgene.txt"), "seq.txt");
	}
}
