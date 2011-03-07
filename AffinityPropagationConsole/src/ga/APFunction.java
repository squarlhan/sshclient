
package ga;

import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;


public class APFunction
    extends FitnessFunction {
  
  public double getfit(IChromosome a_subject) {
    double total = 0;
    
    double[] decs = Bin2Dec.binstr2decstr(a_subject, 20, 5.12, -5.12);
    for (int i = 0; i < decs.length; i++) {
      
        total += Math.pow(decs[i], 2.0);
      
    }

    return 3*Math.pow(5.12, 2.0)-total;
  }
  
  public double evaluate(IChromosome a_subject) {

	    return a_subject.getFitnessValue();
	  }
}
