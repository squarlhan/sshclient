/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package ga;

import java.util.List;

import org.jgap.*;
import org.jgap.impl.*;

/**
 * Fitness function for our example. See evaluate(...) method for details.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class InterMaxFunction
    extends FitnessFunction {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";

  /**
   * This example implementation calculates the fitness value of Chromosomes
   * using BooleanAllele implementations. It simply returns a fitness value
   * equal to the numeric binary value of the bits. In other words, it
   * optimizes the numeric value of the genes interpreted as bits. It should
   * be noted that, for clarity, this function literally returns the binary
   * value of the Chromosome's genes interpreted as bits. However, it would
   * be better to return the value raised to a fixed power to exaggerate the
   * difference between the higher values. For example, the difference
   * between 254 and 255 is only about .04%, which isn't much incentive for
   * the selector to choose 255 over 254. However, if you square the values,
   * you then get 64516 and 65025, which is a difference of 0.8% -- twice
   * as much and, therefore, twice the incentive to select the higher
   * value.
   *
   * @param a_subject the Chromosome to be evaluated
   * @return defect rate of our problem
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 2.0
   */
  
  private Double[][] inters;
  private int p;
  private Double maxfit;


public Double getMaxfit() {
	return maxfit;
}

public void setMaxfit(Double maxfit) {
	this.maxfit = maxfit;
}

public InterMaxFunction(Double[][] inters, int p, Double maxfit) {
	super();
	this.inters = inters;
	this.p = p;
	this.maxfit = maxfit;
}

public int getP() {
	return p;
}

public void setP(int p) {
	this.p = p;
}

public Double[][] getInters() {
	return inters;
}

public void setInters(Double[][] inters) {
	this.inters = inters;
}



public double evaluate(IChromosome a_subject) {
    double total = 0;
    try {
		Thread.sleep(0);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    double[] decs = Bin2Dec.binstr2decstr(a_subject, p, inters);
    for (int i = 0; i < decs.length; i++) {
      
        total += Math.pow(decs[i], 2.0);
      
    }

    return maxfit-total;
  }
}
