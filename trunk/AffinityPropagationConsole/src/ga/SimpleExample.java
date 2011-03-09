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

import java.text.DecimalFormat;

import org.jgap.*;
import org.jgap.impl.*;

/**
 * Simple class that demonstrates the basic usage of JGAP.
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 2.0
 */
public class SimpleExample {
  /** String containing the CVS revision. Read out via reflection!*/
  private static final String CVS_REVISION = "$Revision: 1.9 $";

  /**
   * Starts the example.
   * @param args if optional first argument provided, it represents the number
   * of bits to use, but no more than 32
   *
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 2.0
   */
  public static void main(String[] args) {
	 long startTime=System.currentTimeMillis();
    int numEvolutions = 200;
    Configuration gaConf = new DefaultConfiguration();
    gaConf.setPreservFittestIndividual(true);
    gaConf.setKeepPopulationSizeConstant(false);
    Genotype genotype = null;
    int chromeSize = 60;    
    double maxFitness = 3*Math.pow( 5.12, 2.0);
    try {
      IChromosome sampleChromosome = new Chromosome(gaConf,
          new BooleanGene(gaConf), chromeSize);
      gaConf.setSampleChromosome(sampleChromosome);
      gaConf.setPopulationSize(40);
      gaConf.setFitnessFunction(new MaxFunction());
      genotype = Genotype.randomInitialGenotype(gaConf);
    }
    catch (InvalidConfigurationException e) {
      e.printStackTrace();
      System.exit( -2);
    }
    int progress = 0;
    int percentEvolution = numEvolutions / 10;
    for (int i = 0; i < numEvolutions; i++) {
      genotype.evolve();
      // Print progress.
      // ---------------
      if (percentEvolution > 0 && i % percentEvolution == 0) {
        progress++;
        IChromosome fittest = genotype.getFittestChromosome();
        double fitness = fittest.getFitnessValue();
        System.out.println("Currently fittest Chromosome has fitness " +
                           fitness);
//        if (fitness >= maxFitness) {
//          break;
//        }
      }
    }
    // Print summary.
    // --------------
    IChromosome fittest = genotype.getFittestChromosome();
    double[] fited = Bin2Dec.binstr2decstr(fittest, 20, 5.12, -5.12);
    System.out.println("Fittest Chromosome has fitness " +
                       fittest.getFitnessValue());
    DecimalFormat myformat=new DecimalFormat("#0.00");
    for (int i = 0; i < fited.length; i++) {
        
    	System.out.println(myformat.format(fited[i]));
      
    }
    long endTime=System.currentTimeMillis();
    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
  }
}
