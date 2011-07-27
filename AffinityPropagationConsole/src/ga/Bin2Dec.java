package ga;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.BooleanGene;

public class Bin2Dec {
	
	
	public static double[] binstr2decstr(IChromosome chr, int acc, Double qmax, Double qmin){
		double[] result = new double[chr.size()/acc];
		if(chr.size()%acc!=0){
			System.err.println("The input value is wrong from Class Bin2Dec!");
			return null;
		}else{
			Double delta = (qmax-qmin)/(Math.pow(2, acc)-1); 
			for(int i = 0; i<= chr.size()-acc; i+=acc){
				int r = 0;
				int mul = 1;
				for(int j = 0;j <= acc-1; j++){
					 r += mul * (((BooleanGene) chr.getGene(i+j)).booleanValue() ? 1 : 0);
					 mul *= 2;
				}
				result[i/acc]= delta*r+qmin;
			}
		}
		return result;
	}
	/**
	 * 二进制串变成实数串
	 * @param chr 一个二进制串
	 * @param acc 精度
	 * @param inters 每个参数的取值范围
	 * @return 实数串
	 */
	public static double[] binstr2decstr(IChromosome chr, int acc, Double[][] inters){
		
		if(chr.size()%acc!=0){
			System.err.println("The input value is wrong from Class Bin2Dec!");
			return null;
		}
		int len = chr.size() / acc;
		if(len!=inters.length||inters[0].length!=2){
			System.err.println("The input value is wrong from Class Bin2Dec in inters!");
			return null;
		}
		double[] result = new double[len];
		
		for (int i = 0; i <= chr.size() - acc; i += acc) {
			int r = 0;
			int mul = 1;
			for (int j = 0; j <= acc - 1; j++) {
				r += mul
						* (((BooleanGene) chr.getGene(i + j)).booleanValue() ? 1
								: 0);
				mul *= 2;
			}
			int index = i / acc;
			Double delta = ( inters[index][1] -  inters[index][0]) / (Math.pow(2, acc) - 1);
			result[index] = delta * r + inters[index][0];
		}

		return result;
	}
	
	/**
	 * 二进制串变成实数串,区间的变化，第一个实数是区间开始，第二个是区间长度
	 * @param chr 一个二进制串
	 * @param acc 精度
	 * @param inters 每个参数的取值范围
	 * @return 实数串
	 */
	public static double[] binstr2decstr_interval(IChromosome chr, int acc, Double[][] inters){
		
		if(chr.size()%acc!=0||(chr.size()/acc)%2!=0){
			System.err.println("The input value is wrong from Class Bin2Dec!");
			return null;
		}
		int len = chr.size() / acc;
		if(len!=inters.length||inters[0].length!=2){
			System.err.println("The input value is wrong from Class Bin2Dec in inters!");
			return null;
		}
		double[] result = new double[len];
		
		for (int i = 0; i <= chr.size() - acc; i += acc) {
			int r = 0;
			int mul = 1;
			for (int j = 0; j <= acc - 1; j++) {
				r += mul* (((BooleanGene) chr.getGene(i + j)).booleanValue() ? 1: 0);
				mul *= 2;
			}
			int index = i / acc;
			if(index%2==0){			
				Double delta = ( inters[index][1] -  inters[index][0]) / (Math.pow(2, acc) - 1);			
				result[index] = delta * r + inters[index][0];
			}else{
				Double delta = ( inters[index][1] - result[index-1]) / (Math.pow(2, acc) - 1);			
				result[index] = delta * r;
			}
		}

		return result;
	}
	
	
	public static double[][] binlst2declst(Population pop, int acc, Double qmax, Double qmin){
		
		
		List<IChromosome> chrlist = pop.getChromosomes();
		//IChromosome[] chrs = new IChromosome[chrlist.size()];
		double[][] results = new double[chrlist.size()][chrlist.get(0).size()/acc];
		for(int i = 0; i<=chrlist.size()-1; i++){
			results[i] = Bin2Dec.binstr2decstr(chrlist.get(i), acc, qmax, qmin);			
		}
		return results;
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Double delta = (3 - 0) / (Math.pow(2, 2) - 1);
		String s = "0011001101100110";   
		for (int i = 0; i <= s.length() - 2; i += 2) {
			int r = 0;
			int mul = 1;
			for (int j = 0; j <= 2 - 1; j++) {
				r += mul * (s.charAt(i+j) == '1' ? 1 : 0);
				mul *= 2;
			}
			System.out.print(delta * r + 0);
			System.out.print("/");
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("//////////");
	}

}
