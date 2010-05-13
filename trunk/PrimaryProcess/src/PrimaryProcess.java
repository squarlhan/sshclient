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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PrimaryProcess {

	public static List<String> readfile(String fileaddress) throws NumberFormatException, IOException{
		File file = new File(fileaddress);
		List<String> mydata = new ArrayList<String>();
		InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
		BufferedReader br = new BufferedReader(insr);
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.trim().length() >= 1) 
			{				 
				mydata.add(line);
				
			}
		}
		br.close();
		insr.close();
		System.out.println(mydata.size());
		return mydata;
	}
	
	public static void writefile(List<String> mydata, String fileaddress){
		try {
			File result = new File(fileaddress);
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
			for(int i=0;i<=mydata.size()-1;i++){
				output.write(mydata.get(i)+"\n");
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static List<String> processfile(List<String> mydata){
		List<String> dataresult = new ArrayList<String>();
		for(int i = 0;i<=mydata.size()-1;i++){
			String line = mydata.get(i).trim();
			if(line.length()>=1){
				if(line.length()>=3&&isNumeric(line.substring(0, 3))){
					dataresult.add(">"+line);
				}
				if(line.startsWith("A")||line.startsWith("T")||line.startsWith("C")||line.startsWith("G")){
					line = line.split(" ")[0];
					dataresult.add(line);
				}
			}
		}
        return dataresult;
	}
	
	public static List<String> processcdsfile(List<String> mydata){
		List<String> cdsresult = new ArrayList<String>();
		for(int i = 0;i<=mydata.size()-1;i++){
			String line = mydata.get(i).trim();
			int lines = 0;
			String temp = "";
			if(line.length()>=1){
				if (line.length() >= 3 && isNumeric(line.substring(0, 3))) {
					String title = "";
					String[] sublines = line.split(";");
					String longtitle = sublines[0];
					String type = sublines[sublines.length-1];
					String[] ts = longtitle.trim().split(" ");					
					
					lines = Integer.parseInt(line.substring(0, 3)) / 80 + 1;
					for (int a = 0; a <= lines-1; a++) {
						temp = temp + mydata.get(a + i + lines + 1).trim().split(" ")[0];
					}
					
					title = ts[ts.length-2] + " " + ts[0] + " " + String.valueOf(temp.indexOf('i')+1) + type;
				
					cdsresult.add(">" + title);					
					cdsresult.add(String.valueOf(temp.indexOf('i')+1));
				}
			}
		}
        return cdsresult;
	}
	
	public static void process(List<String> mydata, String dataaddress, String cdsaddress){
		List<String> dataresult = new ArrayList<String>();
		List<String> cdsresult = new ArrayList<String>();
		for(int i = 0;i<=mydata.size()-1;i++){
			String line = mydata.get(i).trim();
			int lines = 0;
			String temp = "";
			if(line.length()>=1){
				if(line.length()>=3&&isNumeric(line.substring(0, 3))){
					
					String title = "";
					String[] sublines = line.split(";");
					String longtitle = sublines[0];
					String type = sublines[sublines.length-1];
					String[] ts = longtitle.trim().split(" ");					
					
					lines = Integer.parseInt(line.substring(0, 3)) / 80 + 1;
					for (int a = 0; a <= lines-1; a++) {
						temp = temp + mydata.get(a + i + lines + 1).trim().split(" ")[0];
					}
					
					title = ts[ts.length-2] + " " + ts[0] + " " + String.valueOf(temp.indexOf('i')+1) + type;
					
					dataresult.add(">"+title);
					cdsresult.add(">" + title);
					
					cdsresult.add(String.valueOf(temp.indexOf('i')+1));
				}
				if(line.startsWith("A")||line.startsWith("T")||line.startsWith("C")||line.startsWith("G")){
					line = line.split(" ")[0];
					dataresult.add(line);
				}
			}
		}
		writefile(dataresult,dataaddress);
		writefile(cdsresult,cdsaddress);
	}
	
	public static void joinfile(String datafile, String cdsfile, String resultfile){
		List<String> mydata = new ArrayList<String>();
		List<String> mycds = new ArrayList<String>();
		
		try {
			mydata = readfile(datafile);
			mycds = readfile(cdsfile);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int a = 0;
		int i = 0;
		while(i<=mydata.size()-1){
			int j = i+1;
			String dna = "";
			while(j<=mydata.size()-1&&!mydata.get(j).startsWith(">")){
				dna+=mydata.get(j).trim();
				j++;
			}
			int num = dna.length();
			String[] cdslines = mycds.get(a).split(" ");
			String title = ">"+cdslines[0]+" "+String.valueOf(num)+" "+cdslines[1];
			mydata.set(i, title);
			i=j;
			a++;
		}
		
		writefile(mydata,resultfile);
	}
	
	public static void main(String[] args) throws IOException{
		try {
			//writefile(processfile(readfile("vertebrates.txt")),"result.txt");
			//writefile(processcdsfile(readfile("vertebrates.txt")),"cdsresult.txt");
			//process(readfile("vertebrates.txt"),"result.txt","cdsresult.txt");
			joinfile("DNASequences.fasta", "CDS.tbl", "ALLSEQ.txt");
			joinfile("hmrfasta.txt", "hmrcds.txt", "HMR195.txt");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
