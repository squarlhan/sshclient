package ga.graph.modeltool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BenchMark {

	public String fileName;
	public int domNum;
	public int varNum;
	public int relNum;
	public int conNum;
	public BMVariable[] variables;
	public BMRelation[] relations;
	public BMConstraint[] constraints;
	public BMDomain[] domains;
	public int line = 0;
	public File file;
	public BufferedReader reader;
	public String tempString;
	public int state = 0;
	public int domainIndex = 0;
	public int varIndex = 0;
	public int relIndex = 0;
	public int conIndex = 0;
	public String name;
	public int ary = 0;
	
	public int biggestDomainSize=0;
	public boolean domainContinue=true;
	public boolean type0=false;
	public boolean type1=false;
	public double tightest=1;//最紧的，就是值对最少的
	public double loosest=0;//最松的，就是值对最多的
	
	public static BenchMark createBenchMark(String fileName) {
		return new BenchMark(fileName);
	}

	private BenchMark(String fileName) {
		this.fileName=fileName;
		file = new File(fileName);
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
				dealline(tempString);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public boolean dealline(String s) {
		if (s.trim().length() == 0)
			return true;
		int index1 = 0;
		int index2 = 1;
		while (s.substring(index1, index2).equals(" ")) {
			index1++;
			index2++;
		}

		if (state == 11) {
			int index3 = index1;
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index3++;
				index2++;
			}
			domNum = Integer.parseInt(s.substring(index1, index2));
			this.domains = new BMDomain[domNum];
			state = 1;
			return true;
		}
		if (state == 12) {

			int index3 = index1;
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index3++;
				index2++;
			}
			varNum = Integer.parseInt(s.substring(index1, index2));
			this.variables = new BMVariable[varNum];
			state = 2;
			return true;
		}
		if (state == 13) {
			int index3 = index1;
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index3++;
				index2++;
			}
			relNum = Integer.parseInt(s.substring(index1, index2));
			this.relations = new BMRelation[relNum];
			state = 3;
			return true;
		}
		if (state == 14) {
			int index3 = index1;
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index3++;
				index2++;
			}
			conNum = Integer.parseInt(s.substring(index1, index2));
			this.constraints = new BMConstraint[conNum];
			state = 4;
			return true;
		}
		if (state == 1) {
			BMDomain dom = new BMDomain(s);
			domains[domainIndex] = dom;
			domainIndex++;
			if (domainIndex == domNum) {
				state = 12;
			}
			if(dom.size>this.biggestDomainSize){
				this.biggestDomainSize=dom.size;
			}
			if(dom.size-1!=dom.values[dom.size-1]){
				this.domainContinue=false;
			}
			return true;
		}
		if (state == 2) {
			BMVariable var = new BMVariable(s);
			variables[varIndex] = var;
			varIndex++;
			if (varIndex == varNum) {
				state = 13;
			}
			return true;
		}
		if (state == 3) {
			BMRelation rel = new BMRelation(s);
			if (rel.ary > ary)
				ary = rel.ary;
			relations[relIndex] = rel;
			relIndex++;
			if (relIndex == relNum)
				state = 14;
			double fullTuples=1;
			for(int i=0;i<rel.domIds.length;i++){
				fullTuples*=this.domains[rel.domIds[i]].size;
			}
			double tight=((double)rel.tupleCount)/((double)fullTuples);
			if(rel.type==0){
				type0=true;
				tight=1-tight;
			}else{
				type1=true;
			}
			if(tight>this.loosest&&tight<1){
				this.loosest=tight;
			}
			if(tight<this.tightest){
				this.tightest=tight;
			}
			return true;
		}
		if (state == 4) {
			BMConstraint con = new BMConstraint(s);
			constraints[conIndex] = con;
			con.id = conIndex;
			conIndex++;
			return true;
		}
		String temp = s.substring(index1, index2);
		int value = StringTool.parseToInteger(temp);
		if (value == -10000) {
			state = 11;
			name = s.trim();
			return true;
		} else {
			int index3 = index1;
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index3++;
				index2++;
			}
			domNum = Integer.parseInt(s.substring(index1, index2));
			this.domains = new BMDomain[domNum];
			state = 1;
			return true;

		}
	}

	public void outputbenchmark(int n) {
		System.out.println("Name : " + name);
		System.out.println("Greatest ary : " + ary);
		if (n == 1 || n == 5) {
			outputdomain();
		}
		if (n == 2 || n == 5) {
			outputvariable();
		}
		if (n == 3 || n == 5) {
			outputrelation();
		}
		if (n == 4 || n == 5) {
			outputconstraint();
		}

	}

	public void outputdomain() {
		System.out.println("domain count : " + this.domNum);
		System.out.println("id    size   values");
		for (int i = 0; i < this.domNum; i++) {
			System.out.print(this.domains[i].id + "      "
					+ this.domains[i].size + "    ");
			for (int j = 0; j < this.domains[i].values.length; j++)
				System.out.print(this.domains[i].values[j] + " ");
			System.out.println();
		}

	}

	public void outputvariable() {
		System.out.println("variable count : " + this.varNum);
		System.out.println("id    domain-id");
		for (int i = 0; i < this.varNum; i++) {
			System.out.println(this.variables[i].id + "         "
					+ this.variables[i].domId);
		}
	}

	public void outputrelation() {
		System.out.println("relation  count: " + this.relNum);
		System.out
				.println("id  type  ary           dom-id           tuple number     tuples:");
		for (int i = 0; i < this.relNum; i++) {
			System.out.print(this.relations[i].id + "    "
					+ this.relations[i].type + "     " + this.relations[i].ary
					+ "             ");
			for (int j = 0; j < this.relations[i].ary; j++) {
				System.out.print(this.relations[i].domIds[j] + " ");
			}
			System.out.print("                " + this.relations[i].tupleCount
					+ "            ");
			for (int j = 0; j < this.relations[i].tupleCount; j++) {
				for (int k = 0; k < this.relations[i].ary; k++) {
					System.out.print(this.relations[i].tuples[k][j] + " ");
				}
			}

			for (int j = 0; j < this.relations[i].tuples.length; j++) {
				for (int k = 0; k < this.relations[i].tuples[j].length; k++) {
					System.out.print(this.relations[i].tuples[j][k] + " ");
				}
			}
			System.out.println();
		}
	}

	public void outputconstraint() {
		System.out.println("constraint count : " + this.conNum);
		System.out.println("ary            var-id             relation id");
		for (int i = 0; i < this.conNum; i++) {
			System.out.print(this.constraints[i].ary + "               ");
			for (int j = 0; j < this.constraints[i].varIds.length; j++) {
				System.out.print(this.constraints[i].varIds[j] + " ");
			}
			System.out.println("                " + this.constraints[i].relId);
		}
	}

}
