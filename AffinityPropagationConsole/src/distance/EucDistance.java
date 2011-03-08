package distance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.Vector;

import affinitymain.InteractionData;

public class EucDistance {

	/**
	 * read data from file
	 * @param addr
	 * @return
	 * @throws IOException
	 */
	public static double[][] getData(String addr) throws IOException {
		final int EOF = -1;
		double[][] source;// 数据源矩阵
		File f = new File(addr);
		String a = new String();
		String b = new String();
		Vector v = new Vector();
		int row = 0;// 数据源矩阵的行
		int col = 0;// 列

		FileReader in = new FileReader(f);
		BufferedReader inTxt = new BufferedReader(in);
		inTxt.mark((int)f.length()+2);
		
        a = inTxt.readLine();
		StringTokenizer wordString = new StringTokenizer(a);
		while (wordString.hasMoreTokens()) {
			v.add(wordString.nextToken());
		}
		col = v.size();
		inTxt.reset();
		while (inTxt.read() != EOF) {
			row++;
			inTxt.readLine();
		}
		inTxt.reset();
		source = new double[row][col];

		int i, j;

		for (i = 0; i<row; i++) {
			a = inTxt.readLine();
			StringTokenizer wordSS = new StringTokenizer(a);
			for (j = 0; wordSS.hasMoreTokens(); j++) {
				b = wordSS.nextToken();
				source[i][j] = Double.valueOf(b).doubleValue();
			}

		}

		in.close();
		return source;
	}

	/**
	 * @param point1   first point
	 * @param point2   second point
	 * @return  the  Euclidean distance of two points
	 */
	public static double getDistance(double[] point1, double[] point2) {
	    double distance = 0;
	    for (int i = 0; i < point1.length; i++) {
	        distance = distance + Math.pow((point1[i] - point2[i]), 2);
	    }
	    return Math.sqrt(distance);
	}
	/**
	 * calculate the Eucl-distance of the data 
	 * @param mydata
	 * @return
	 */
	public static Collection<InteractionData> calcEucMatrix(double[][] mydata){
		Collection<InteractionData> ints = new HashSet<InteractionData>();
		int row = mydata.length;
		for(int i = 0; i<= row-1; i++){
			for(int j = 0; j<= row-1; j++){
				Double dis = getDistance(mydata[i],mydata[j]) == 0?0.9:getDistance(mydata[i],mydata[j]);
				ints.add(new InteractionData(String.valueOf(i), String.valueOf(j), 	-1*dis));
				//System.out.println(-1*getDistance(mydata[i],mydata[j]));
			}
		}
		return ints;
	}
	
	/**
	 * read distance matrix from formated file
	 * @param mydata
	 * @return
	 */
	public static Collection<InteractionData> getEucMatrix(double[][] mydata){
		Collection<InteractionData> ints = new HashSet<InteractionData>();
		int row = mydata.length;
		for(int i = 0; i<= row-1; i++){
			ints.add(new InteractionData(String.valueOf(mydata[i][0]), 
						String.valueOf(mydata[i][1]), mydata[i][2]));
		}
		return ints;
	}
	
	/**
	 * read distance matrix from unformatted file
	 * @param datamatrix
	 * @return
	 */
	public static Collection<InteractionData> getunEucMatrix(double[][] datamatrix){
		Collection<InteractionData> ints = new HashSet<InteractionData>();
		int row = datamatrix.length;
		int col = datamatrix[0].length;
		for(int i = 0; i<= row-1; i++){
			for(int j = 0;j<= col-1; j++){
				ints.add(new InteractionData(String.valueOf(i+1), 
						String.valueOf(j+1), datamatrix[i][j]));
			}		
		}
		return ints;
	}
	
	public static void main(String[] arg){
		try {
			calcEucMatrix(getData("data/b.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
