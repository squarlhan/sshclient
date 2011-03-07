package ga;

import java.util.ArrayList;
import java.util.List;

import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.impl.BooleanGene;

public class Bin2Dec {
	
	
	public static Double[] binstr2decstr(IChromosome chr, int acc, Double qmax, Double qmin){
		Double[] result = new Double[chr.size()/acc];
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
	
	public static Double[][] binlst2declst(Genotype genotype, int acc, Double qmax, Double qmin){
		
		Population pop = genotype.getPopulation();
		IChromosome[] chrs = (IChromosome[]) pop.getChromosomes().toArray();
		Double[][] results = new Double[chrs.length][chrs[0].size()/acc];
		for(int i = 0; i<=chrs.length; i++){
			results[i] = Bin2Dec.binstr2decstr(chrs[i], acc, qmax, qmin);			
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
