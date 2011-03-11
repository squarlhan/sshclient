package ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgap.*;
import org.jgap.impl.*;

import affinitymain.CommandLineParser;
import affinitymain.InteractionData;
import affinitymain.RunAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;
import distance.EucDistance;

public class APGAExample {
	
	
	
	

	
	public static void main(String[] args) throws IOException {
		File result = new File("apga.txt");
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
		
		BufferedWriter output = new BufferedWriter(new FileWriter(result));
		
		long startTime=System.currentTimeMillis();
		int numEvolutions = 200;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;
		int chromeSize = 2000;
		double maxFitness = 100 * Math.pow(5.12, 2.0);
		try {
			IChromosome sampleChromosome = new Chromosome(gaConf,
					new BooleanGene(gaConf), chromeSize);
			gaConf.setSampleChromosome(sampleChromosome);
			gaConf.setPopulationSize(40);	
			gaConf.setFitnessFunction(new MaxFunction());
			genotype = Genotype.randomInitialGenotype(gaConf);
			//genotype.getConfiguration().setFitnessFunction(new APFunction(genotype));
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		int progress = 0;
		int percentEvolution = numEvolutions / 10;
		for (int i = 0; i < numEvolutions; i++) {
			
			
			//Start GA
			genotype.evolve(true);
			// Print progress.
			// ---------------
			if (percentEvolution > 0 && i % percentEvolution == 0) {
				progress++;
				IChromosome fittest = genotype.getFittestChromosome();
				double fitness = fittest.getFitnessValue();
				System.out.println("Currently fittest Chromosome has fitness "
						+ fitness);
				output.write(String.valueOf(fitness)+"\n");
				// if (fitness >= maxFitness) {
				// break;
				// }
			}
		}
		// Print summary.
		// --------------
		IChromosome fittest = genotype.getFittestChromosome();
		double[] fited = Bin2Dec.binstr2decstr(fittest, 20, 5.12, -5.12);
		System.out.println("Fittest Chromosome has fitness "
				+ (maxFitness-fittest.getFitnessValue()));
		output.write("Fittest Chromosome has fitness "
				+ (maxFitness-fittest.getFitnessValue())+"\n");
		DecimalFormat myformat = new DecimalFormat("#0.00");
		for (int i = 0; i < fited.length; i++) {

			System.out.println(myformat.format(fited[i]));
			output.write(String.valueOf(myformat.format(fited[i]))+"\n");
		}
		long endTime=System.currentTimeMillis();
	    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
	    output.write("程序运行时间： " + (endTime - startTime) + "ms"+"\n");
	    output.close();
	}
}
