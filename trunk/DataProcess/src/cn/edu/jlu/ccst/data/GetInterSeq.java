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

public class GetInterSeq {

	// 获取基因组序列
	private static String getgenome(String addr) {
		File file = new File(addr);
		String seq = "";
		List<String> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if ((line.trim().length() >= 1)
						&& (!line.trim().startsWith(">"))) {
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
		for (String templine : result) {
			seq = seq + templine;
		}
		System.out.println("seq:" + seq.length());
		return seq;
	}

	// 新的获取基因组序列
	private static String getgenomenew(String addr) {
		File file = new File(addr);
		String seq = "";
		List<String> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if ((line.trim().length() >= 1)
						&& (!line.trim().startsWith(">"))) {
					seq = line;
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
		System.out.println("seq:" + seq.length());
		return seq;
	}

	// 获取基因间隙位置
	private static List<List<Integer>> getpos(String addr) {
		File file = new File(addr);
		List<List<Integer>> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) {
					String[] lines = line.split("	");
					int s, e;
					s = e = 0;
					if (lines.length == 2) {
						s = Integer.parseInt(lines[0]);
						e = Integer.parseInt(lines[1]);
					}
					List<Integer> temp = new ArrayList();
					temp.add(s);
					temp.add(e);
					result.add(temp);
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
		System.out.println("result:" + result.size());
		return result;
	}
	
	// 新的获取基因间隙位置
	private static List<List<Integer>> getnewpos(String addr) {
		File file = new File(addr);
		List<List<Integer>> result = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) {
					String[] lines = line.split("	");
					int label, s, e;
					label= s = e = 0;
					if (lines.length == 3) {
						label = Integer.parseInt(lines[0]);
						s = Integer.parseInt(lines[1]);
						e = Integer.parseInt(lines[2]);
					}
					List<Integer> temp = new ArrayList();
					temp.add(label);
					temp.add(s);
					temp.add(e);
					result.add(temp);
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
		System.out.println("result:" + result.size());
		return result;
	}

	// 得到基因间的序列
	public static void producefasta(String all, String genome,
			List<List<Integer>> pos) {
		try {
			File result = new File("rs/" + all + ".fna");
			if (result.exists()) {
				result.delete();

				if (result.createNewFile()) {
					System.out.println(all + " result file create success!");
				} else {
					System.out.println(all + " result file create failed!");
				}

			} else {
				if (result.createNewFile()) {
					System.out.println(all + " result file create success!");
				} else {
					System.out.println(all + " result file create failed!");
				}

			}
			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			for (int i = 0; i <= pos.size() - 1; i++) {
				if (i == 0) {
					output.write(">" + String.valueOf(pos.get(i).get(0)) + "\n");
					String str1 = genome.substring(pos.get(i).get(1) - 1);
					String str2 = genome.substring(0, pos.get(i).get(2));
					int a = 0;
					while (a <= (str1 + str2).length() - 1) {
						
						if (a > 1 && (a) % 60 == 0)
							output.write("\n");
						output.write((str1 + str2).substring(a, a + 1));
						a++;
					}
					printfile(String.valueOf(pos.get(i).get(0)), str1 + str2);
				} else {
					if (pos.get(i).get(1) <= pos.get(i).get(2)) {
						output.write("\n>" + String.valueOf(pos.get(i).get(0)) + "\n");
						String str1 = genome.substring(pos.get(i).get(1) - 1,pos.get(i).get(2));
						int a = 0;
						while (a <= str1.length() - 1) {
							
							if (a > 1 && (a) % 60 == 0)
								output.write("\n");
							output.write(str1.substring(a, a + 1));
							a++;
						}
						printfile(String.valueOf(pos.get(i).get(0)), str1);
					}else{
							System.out	.println(all+" " +String.valueOf(pos.get(i).get(0)));
						}
				}
			}
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 存成文本
	public static void printfile(String fileaddr, String content) {
		try {
			File result = new File("rs/" + fileaddr + ".fna");
			if (result.exists()) {
				result.delete();
				if (result.createNewFile()) {
					System.out
							.println(fileaddr + "result file create success!");
				} else {
					System.out.println(fileaddr + "result file create failed!");
				}
			} else {
				if (result.createNewFile()) {
					System.out
							.println(fileaddr + "result file create success!");
				} else {
					System.out.println(fileaddr + "result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(result));
			output.write(">" + fileaddr + "\n");
			int a = 0;
			while (a <= content.length() - 1) {
				
				if (a > 1 && (a) % 60 == 0)
					output.write("\n");
				output.write(content.substring(a, a + 1));
				a++;
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void producefnabyre(String rsaddr, String pmfileaddr, String addr, String finaladdr){
		File file = new File(rsaddr);
		File pmfile = new File(pmfileaddr);
		File finalfilep = new File(finaladdr+"_p.fna");
		File finalfilem = new File(finaladdr+"_m.fna");
		List<Integer> result = new ArrayList();
		List<String> pms = new ArrayList();
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(
					file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) {
					result.add(Integer.parseInt(line.trim()));
				}
			}
			br.close();
			insr.close();
			
			InputStreamReader insrpm = new InputStreamReader(new FileInputStream(
					pmfile), "gb2312");
			BufferedReader pmbr = new BufferedReader(insrpm);
			String pmline;
			while ((pmline = pmbr.readLine()) != null) {
				pmline = pmline.trim();
				if (pmline.trim().length() >= 1) {
					pms.add(pmline.trim());
				}
			}
			pmbr.close();
			insrpm.close();
			
			
			if (finalfilep.exists()) {
				finalfilep.delete();
				if (finalfilep.createNewFile()) {
					System.out
							.println(finalfilep + "result file create success!");
				} else {
					System.out.println(finalfilep + "result file create failed!");
				}
			} else {
				if (finalfilep.createNewFile()) {
					System.out
							.println(finalfilep + "result file create success!");
				} else {
					System.out.println(finalfilep + "result file create failed!");
				}

			}
			
			if (finalfilem.exists()) {
				finalfilem.delete();
				if (finalfilem.createNewFile()) {
					System.out
							.println(finalfilem + "result file create success!");
				} else {
					System.out.println(finalfilem + "result file create failed!");
				}
			} else {
				if (finalfilem.createNewFile()) {
					System.out
							.println(finalfilem + "result file create success!");
				} else {
					System.out.println(finalfilem + "result file create failed!");
				}

			}

			BufferedWriter outputp = new BufferedWriter(new FileWriter(finalfilep));
			BufferedWriter outputm = new BufferedWriter(new FileWriter(finalfilem));
			int b = 0;
			for(int a=0; a <= result.size()-1; a++){
				if (result.get(a) == 1) {
					File temp = new File(addr + (a + 1) + ".fna");
					
					if (temp.exists()) {
						InputStreamReader insrtemp = new InputStreamReader(
								new FileInputStream(temp), "gb2312");
						BufferedReader brtemp = new BufferedReader(insrtemp);
						line = "";
						
						while ((line = brtemp.readLine()) != null) {
							line = line.trim();
							if (line.trim().length() >= 1) {
								if(pms.get(a).equals("+"))outputp.write(line + "\n");
								else if (pms.get(a).equals("-"))outputm.write(line + "\n");
							}
						}
						brtemp.close();
						insrtemp.close();
					}else{
							b++;
							System.out.println("There is no file "+(a + 1));
					}
					
				}
			}
			System.out.println("There is no file counts "+b);
			outputp.close();
			outputm.close();
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
		
	}
	
	public static void producepmpos(List<List<Integer>> pos2549, List<List<Integer>> allposm, List<List<Integer>> allposp){
		try {
			File result1 = new File("posp.txt");
			File result2 = new File("posm.txt");
			File result3 = new File("pm2549.txt");
			if (result1.exists()) {
				result1.delete();
				if (result1.createNewFile()) {
					System.out
							.println( "posp result file create success!");
				} else {
					System.out.println("posp result file create failed!");
				}
			} else {
				if (result1.createNewFile()) {
					System.out
							.println( "posp result file create success!");
				} else {
					System.out.println("posp result file create failed!");
				}

			}
			
			if (result2.exists()) {
				result2.delete();
				if (result2.createNewFile()) {
					System.out
							.println( "posm result file create success!");
				} else {
					System.out.println("posm result file create failed!");
				}
			} else {
				if (result2.createNewFile()) {
					System.out
							.println( "posm result file create success!");
				} else {
					System.out.println("posm result file create failed!");
				}

			}
			
			if (result3.exists()) {
				result3.delete();
				if (result3.createNewFile()) {
					System.out
							.println( "pm result file create success!");
				} else {
					System.out.println("pm result file create failed!");
				}
			} else {
				if (result3.createNewFile()) {
					System.out
							.println( "pm result file create success!");
				} else {
					System.out.println("pm result file create failed!");
				}

			}

			BufferedWriter output1 = new BufferedWriter(new FileWriter(result1));
			BufferedWriter output2 = new BufferedWriter(new FileWriter(result2));
			BufferedWriter output3 = new BufferedWriter(new FileWriter(result3));
			
			for(int a = 0; a<= pos2549.size()-1; a++){
				int ends = pos2549.get(a).get(1);
				for(int i = 0; i<=allposp.size()-1;i++){
					if(ends == allposp.get(i).get(1)){
						output1.write(String.valueOf(a+1));
						output1.write("\t");
						output1.write(String.valueOf(allposp.get(i).get(0)));
						output1.write("\t");
						output1.write(String.valueOf(allposp.get(i).get(1)));
						output1.write("\n");
						output3.write("+\t");
						output3.write("\n");
						break;
					}
				}
				for(int i = 0; i<=allposm.size()-1;i++){
					if(ends == allposm.get(i).get(1)){
						output2.write(String.valueOf(a+1));
						output2.write("\t");
						output2.write(String.valueOf(allposm.get(i).get(0)));
						output2.write("\t");
						output2.write(String.valueOf(allposm.get(i).get(1)));
						output2.write("\n");
						output3.write("-\t");
						output3.write("\n");
						break;
					}
				}
			}
			
			output1.close();
			output2.close();
			output3.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		producefasta("allp", getgenomenew("NC_000913.one"), getnewpos("posp.txt"));
//		producefasta("allm", getgenomenew("NC_000913.one"), getnewpos("posm.txt"));
		producefnabyre("4.txt", "pm.txt", "rs/", "result_4_interval");
		//producepmpos(getpos("pos.txt"), getpos("allposm.txt"), getpos("allposp.txt"));
	}

}
