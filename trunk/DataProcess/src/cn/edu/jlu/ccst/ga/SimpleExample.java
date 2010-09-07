/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package cn.edu.jlu.ccst.ga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
    int numEvolutions;
    int pop;
    String out1;
    String out2 ;
    String out3 ;
    if (args.length == 5) {
	   numEvolutions = Integer.parseInt(args[0]);
	   pop = Integer.parseInt(args[1]);
	   out1 = args[2];
       out2 = args[3];
       out3 = args[4];
    }else if (args.length == 2) {
 	   numEvolutions = Integer.parseInt(args[0]);
 	   pop = Integer.parseInt(args[1]);
 	  SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
 	  String marker = df.format(new Date());
 	  out1 = "fittrack"+marker+".txt";
      out2 = "resltinfo"+marker+".txt";
      out3 = "bestchrom"+marker+".txt";
     }else {
	   numEvolutions = 500;
	   pop = 50;
	   SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
	   String marker = df.format(new Date());
	   out1 = "fittrack"+marker+".txt";
	   out2 = "resltinfo"+marker+".txt";
	   out3 = "bestchrom"+marker+".txt";
    }
    
    Configuration gaConf = new DefaultConfiguration();
    gaConf.setPreservFittestIndividual(true);
    gaConf.setKeepPopulationSizeConstant(false);
    Genotype genotype = null;
    int chromeSize = 2411;
    
    double maxFitness = Math.pow((double)2411,0.5)/(double)123;
    
    List<List<String>> matrix = new ArrayList<List<String>>();
    
    File file = new File("pathway.txt");
	try {
		InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
		BufferedReader br = new BufferedReader(insr);
		String line;
		while ((line = br.readLine()) != null) {		
			if (line.trim().length() >= 1) 
			{	
				String[] lines = line.trim().split(" ");
				if(matrix.size()==0){
					for(String label:lines){
						List<String> pas = new ArrayList<String>();
						pas.add(label);
						matrix.add(pas);
					}
				}else{
					for(int i = 0;i<=lines.length-1; i++){
						List<String> pas = matrix.get(i);
						pas.add(lines[i]);
						matrix.set(i, pas);
					}
				}				
			}
		}
		br.close();
		insr.close();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Matrix:"+matrix.size()+"*"+matrix.get(0).size());
   
    try {
      IChromosome sampleChromosome = new Chromosome(gaConf,
          new BooleanGene(gaConf), chromeSize);
      gaConf.setSampleChromosome(sampleChromosome);
      gaConf.setPopulationSize(pop);
      gaConf.setFitnessFunction(new MaxFunction(matrix));
      genotype = Genotype.randomInitialGenotype(gaConf);
    }
    catch (InvalidConfigurationException e) {
      e.printStackTrace();
      System.exit( -2);
    }
    int progress = 0;
    int percentEvolution = numEvolutions / 100;
    List<String> fittrack = new ArrayList();
    for (int i = 0; i < numEvolutions; i++) {
      genotype.evolve();
      // Print progress.
      // ---------------
      if (percentEvolution > 0 && i % percentEvolution == 0) {
        progress++;
        IChromosome fittest = genotype.getFittestChromosome();
        double fitness = fittest.getFitnessValue();
        System.out.println("Currently fittest Chromosome has fitness " +i+" "+
                           fitness);
        fittrack.add(String.valueOf(fitness));
        if (fitness >= maxFitness) {
          break;
        }
      }
    }
    // Print summary.
    // --------------
    String suminfo = "";
    List<String> bestchrom = new ArrayList();
    IChromosome fittest = genotype.getFittestChromosome();
    System.out.println("Fittest Chromosome has fitness " +
                       fittest.getFitnessValue());
    int back = 0;
    int cc = 0;
    for (int i = 0; i <= fittest.size()-1; i++){
		BooleanGene value = (BooleanGene) fittest.getGene(i);
		if (value.booleanValue()) {
			bestchrom.add("1");
			suminfo=suminfo+String.valueOf(i)+"\t";
			back++;
			cc++;
			if(back==5){
				suminfo=suminfo+"\n";
				back = 0;
			}			
		}else bestchrom.add("0");
	}
    suminfo = "The best fitness is " +fittest.getFitnessValue()+
    		"\nThe sum of Supercoli pos is "+cc+".\n"+suminfo;
    
    try {
		File result = new File(out1);
		File result1 = new File(out2);
		File result2 = new File(out3);
		if (result.exists()) {
			result.delete();
			if (result.createNewFile()) {
				System.out.println("result file create success!");
			} else {
				System.out.println("result file create failed!");
			}
		} else {
			if (result.createNewFile()) {
				System.out.println("result file create success!");
			} else {
				System.out.println("result file create failed!");
			}

		}
		if (result1.exists()) {
			result1.delete();
			if (result1.createNewFile()) {
				System.out.println("result1 file create success!");
			} else {
				System.out.println("result1 file create failed!");
			}
		} else {
			if (result1.createNewFile()) {
				System.out.println("result1 file create success!");
			} else {
				System.out.println("result1 file create failed!");
			}

		}
		if (result2.exists()) {
			result2.delete();
			if (result2.createNewFile()) {
				System.out.println("result2 file create success!");
			} else {
				System.out.println("result2 file create failed!");
			}
		} else {
			if (result2.createNewFile()) {
				System.out.println("result2 file create success!");
			} else {
				System.out.println("result2 file create failed!");
			}

		}

		BufferedWriter output = new BufferedWriter(new FileWriter(result));
		BufferedWriter output1 = new BufferedWriter(new FileWriter(result1));
		BufferedWriter output2 = new BufferedWriter(new FileWriter(result2));
		for(int i=0;i<=fittrack.size()-1;i++){
			output.write(fittrack.get(i)+"\n");
		}
		output1.write(suminfo);
		for(int i=0;i<=bestchrom.size()-1;i++){
			output2.write(bestchrom.get(i));
		}
		output.close();
		output1.close();
		output2.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
}
