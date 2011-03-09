
package ga;

import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;


public class APFunction
    extends FitnessFunction {
  
  
  public double evaluate(IChromosome a_subject) {
//	  double fit = 0;
//	  List<IChromosome> chrs = genotype.getPopulation().getChromosomes();
//	  Gene[] agenes = a_subject.getGenes();
//	  for(IChromosome chr: chrs){
//		  
//		  boolean flag = true;
//		  for(int i = 0; i<= agenes.length-1;i++){
//			  if(agenes[i]!=chr.getGene(i)){
//				  flag = false;
//				  break;
//			  } 
//		  }
//		  if(flag){
//			  fit = chr.getFitnessValue();
//			  break;
//		  }
//	  }
	  double total = 0;
	    
	    double[] decs = Bin2Dec.binstr2decstr(a_subject, 20, 5.12, -5.12);
	    for (int i = 0; i < decs.length; i++) {
	      
	        total += Math.pow(decs[i], 2.0);
	      
	    }

	    return 3*Math.pow(5.12, 2.0)-total;
	  }
}
