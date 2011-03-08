
package ga;

import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;


public class APFunction
    extends FitnessFunction {
  
  public Genotype genotype;
  
  public APFunction(Genotype genotype){
	  this.genotype = genotype;
  }
  
  public double evaluate(IChromosome a_subject) {
	  double fit = 0;
	  List<IChromosome> chrs = genotype.getPopulation().getChromosomes();
	  Gene[] agenes = a_subject.getGenes();
	  for(IChromosome chr: chrs){
		  
		  boolean flag = true;
		  for(int i = 0; i<= agenes.length-1;i++){
			  if(agenes[i]!=chr.getGene(i)){
				  flag = false;
				  break;
			  } 
		  }
		  if(flag){
			  fit = chr.getFitnessValue();
			  break;
		  }
	  }
	    return fit;
	  }
}
