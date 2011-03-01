package ga;

import java.util.ArrayList;
import java.util.List;

import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

public class Bin2Dec {
	
	
	public List<Double> binstr2decstr(IChromosome chr, int acc, Double qmax, Double qmin){
		List<Double> result = new ArrayList();
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
				result.add(delta*r+qmin);
			}
		}
		return result;
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
	}

}
