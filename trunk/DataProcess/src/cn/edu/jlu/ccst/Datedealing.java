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
import java.util.Iterator;
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
	//把pathway中的基因换成对映的operon
	private static void producepath(String ogaddr, String seqaddr, List<Pathway> pas, String resultaddr){
		//获取operon和gene的对应数据，分别存到一个list
		File file = new File(ogaddr);
		List<String> opids = new ArrayList();
		List<String> geids = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();				
				if (line.trim().length() >= 1) 
				{					
					String[] lines= line.split("	");
					opids.add(lines[0]);
					geids.add(lines[1]);
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
		System.out.println("Operon:"+opids.size());
		
		//处理数据
		int count = 0;
		for(ListIterator it = pas.listIterator();it.hasNext();){
			Pathway pa = (Pathway)it.next();
			Object[] temp = pa.getGeneid().toArray();
			Set settemp = new HashSet();
			for(int a = 0;a<=temp.length-1;a++){
				for(int i = 0; i<= geids.size()-1; i++){
					if(geids.get(i).equals(temp[a])){
						temp[a] = opids.get(i);
						count++;
						break;
					}
				}
			}
			for(Object tt:temp)settemp.add(tt);
			pa.setGeneid(settemp);
		}
		
		List<String> printxt = new ArrayList();
		for(Pathway pa: pas){
			for(String gid: pa.getGeneid()){
				printxt.add(gid+"	"+pa.getId());
			}
		}
		System.out.println("Count:"+count);
		try {
			File result1 = new File("newpa.txt");
			if (result1.exists()) {
				result1.delete();
				if (result1.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}
			} else {
				if (result1.createNewFile()) {
					System.out.println("result file create success!");
				} else {
					System.out.println("result file create failed!");
				}

			}

			BufferedWriter output1 = new BufferedWriter(new FileWriter(result1));
			for(int i=0;i<=printxt.size()-1;i++){
				output1.write(printxt.get(i)+"\n");
			}
			output1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> seq = getallgene(seqaddr);
		String[][] res = new String[seq.size()][pas.size()];
		for(int a = 0; a<=seq.size()-1; a++){
			for(int i = 0; i<=pas.size()-1; i++)res[a][i] = "0";
		}
		for(int i = 0; i<=pas.size()-1; i++){
			for(Iterator it = pas.get(i).getGeneid().iterator();it.hasNext();){
				String gene = (String)it.next();
				for(int a = 0; a<=seq.size()-1; a++){
					if(seq.get(a).equals(gene)){
						res[a][i] = "1";
						break;
					}
				}
			}
		}
		List<String> outtxt = new ArrayList();		
		for(int a = 0; a<=seq.size()-1; a++){
			String t = "";
			for(int i = 0; i<=pas.size()-1; i++)t=t+res[a][i]+" ";
			outtxt.add(t);
		}
		//写出得到的结果
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
			for(int i=0;i<=outtxt.size()-1;i++){
				output.write(outtxt.get(i)+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
//		getallgene("allgene.txt");
//		getopandge("opandge.txt");
//		getgeandpa("geandpa.txt");
//		produceseq(getopandge("opandge.txt"),getallgene("allgene.txt"), "seq.txt");
		producepath("opandge.txt", "newseq.txt", getgeandpa("geandpa.txt"), "try.txt");
	}
}
