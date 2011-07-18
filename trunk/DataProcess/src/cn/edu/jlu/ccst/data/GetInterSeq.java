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
					output.write(">" + String.valueOf(i + 1) + "\n");
					String str1 = genome.substring(pos.get(i).get(0) - 1);
					String str2 = genome.substring(0, pos.get(i).get(1));
					int a = 0;
					while (a <= (str1 + str2).length() - 1) {
						
						if (a > 1 && (a) % 60 == 0)
							output.write("\n");
						output.write((str1 + str2).substring(a, a + 1));
						a++;
					}
					printfile(String.valueOf(i + 1), str1 + str2);
				} else {
					if (pos.get(i).get(0) <= pos.get(i).get(1)) {
						output.write("\n>" + String.valueOf(i + 1) + "\n");
						String str1 = genome.substring(pos.get(i).get(0) - 1,pos.get(i).get(1));
						int a = 0;
						while (a <= str1.length() - 1) {
							
							if (a > 1 && (a) % 60 == 0)
								output.write("\n");
							output.write(str1.substring(a, a + 1));
							a++;
						}
						printfile(String.valueOf(i + 1), str1);
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
	
	public static void producefnabyre(String rsaddr, String addr, String finaladdr){
		File file = new File(rsaddr);
		File finalfile = new File(finaladdr);
		List<Integer> result = new ArrayList();
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
			System.out.println("result:" + result.size());
			
			
			if (finalfile.exists()) {
				finalfile.delete();
				if (finalfile.createNewFile()) {
					System.out
							.println(finalfile + "result file create success!");
				} else {
					System.out.println(finalfile + "result file create failed!");
				}
			} else {
				if (finalfile.createNewFile()) {
					System.out
							.println(finalfile + "result file create success!");
				} else {
					System.out.println(finalfile + "result file create failed!");
				}

			}

			BufferedWriter output = new BufferedWriter(new FileWriter(finalfile));
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
								output.write(line + "\n");
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
			output.close();
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//producefasta("all", getgenomenew("NC_000913.one"), getpos("pos.txt"));
		producefnabyre("4.txt", "rs/", "resutlt_4_interval.fna");
	}

}
