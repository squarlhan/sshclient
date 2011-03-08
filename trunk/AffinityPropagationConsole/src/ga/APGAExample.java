package ga;

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
	
	
	public static double getfit(IChromosome a_subject) {
	    double total = 0;
	    
	    double[] decs = Bin2Dec.binstr2decstr(a_subject, 20, 5.12, -5.12);
	    for (int i = 0; i < decs.length; i++) {
	      
	        total += Math.pow(decs[i], 2.0);
	      
	    }

	    return 3*Math.pow(5.12, 2.0)-total;
	  }
	
	
	public static  List<Double> calcObjectValue(Genotype genotype, List<Integer> results, double[][] datamatrix, double lamda){
		Population pop = genotype.getPopulation();
		List<IChromosome> chrs = pop.getChromosomes();
		List<Double> objects = new ArrayList();
		Set<Integer> centers = new HashSet();
		centers.addAll(results);
		Map<Integer, Double> centerObjects = new HashMap();
		Iterator iter = centers.iterator();
		while(iter.hasNext()){
			int a = (Integer)iter.next();
			//do something to get the objective value
			Double dis = getfit(chrs.get(a));
			centerObjects.put(a, dis);
		}
		for(int i=0; i <= datamatrix.length-1; i++){
			double object;
			if(i==results.get(i)){
				object = centerObjects.get(i);
			}else{
				double s = 1/(1+datamatrix[i][results.get(i)]);
				object = ((1-lamda)*s+lamda)*centerObjects.get(results.get(i));
			}
			objects.add(object);
			genotype.getPopulation().getChromosome(i).setFitnessValue(object);
		}
		/*for(double a : objects){
        	System.out.println(a);
        }*/

		return objects;
	}
	
	private static AffinityConnectingMethod getConnMode(Map<String, String> map) {
        String modeStr = map.get("conn");
        if (modeStr == null) {
            return AffinityConnectingMethod.ORIGINAL;
        } else {
            if (modeStr.equals("org")) {
                return AffinityConnectingMethod.ORIGINAL;
            } else {
                return AffinityConnectingMethod.PRIME_ALG;
            }
        }
    }

    private static String getOutputKind(Map<String, String> map) {
        String kind = map.get("kind");
        if (kind == null) {
            return "clusters";
        } else {
            if (kind.equals("centers")) {
                return kind;
            } else {
                return "clusters";
            }
        }
    }

    private static Double getPreferences(Map<String, String> map) {
        String lamStr = map.get("p");
        if (lamStr == null) {
            System.out.println("You have to set preferences (p)!");
            return null;
        } else {
            try {
                System.out.println("pref: " + Double.valueOf(lamStr));
                return Double.valueOf(lamStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private static Double getLambda(Map<String, String> map) {
        String lamStr = map.get("lam");
        if (lamStr == null) {
            System.out.println("You have to set lambda (lam)!");
            return null;
        } else {
            try {
                return Double.valueOf(lamStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private static Integer getIterations(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("it"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Integer getConvits(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("con"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String getFilepath(Map<String, String> map) {
        return map.get("in");
    }

    private static String getFoutput(Map<String, String> map) {
        return map.get("out");
    }

    private static boolean getRefine(Map<String, String> map) {
        String ref = map.get("ref");
        if (ref == null) {
            return true;
        } else if (ref.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    private static Integer getSteps(Map<String, String> map) {
        String depthStr = map.get("dep");
        if (depthStr == null) {
            return null;
        } else {
            try {
                return Integer.valueOf(depthStr);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private static boolean getTakeLog(Map<String, String> map) {
        String getLog = map.get("log");
        if (getLog == null) {
            return false;
        } else if (getLog.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

	
	public static void main(String[] args) {
		int numEvolutions = 100;
		Configuration gaConf = new DefaultConfiguration();
		gaConf.setPreservFittestIndividual(true);
		gaConf.setKeepPopulationSizeConstant(false);
		Genotype genotype = null;
		int chromeSize = 60;
		double maxFitness = 3 * Math.pow(5.12, 2.0);
		try {
			IChromosome sampleChromosome = new Chromosome(gaConf,
					new BooleanGene(gaConf), chromeSize);
			gaConf.setSampleChromosome(sampleChromosome);
			gaConf.setPopulationSize(4);	
			gaConf.setFitnessFunction(new APFunction(genotype));
			genotype = Genotype.randomInitialGenotype(gaConf);
			//genotype.getConfiguration().setFitnessFunction(new APFunction(genotype));
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		int progress = 0;
		int percentEvolution = numEvolutions / 10;
		for (int i = 0; i < numEvolutions; i++) {
			// start AP
			Map<String, String> map = CommandLineParser.parseTokens(args);
			double[][] chromatrix = Bin2Dec.binlst2declst(genotype, 20, 5.12,-5.12);
			Collection<InteractionData> inputs = EucDistance.calcEucMatrix(chromatrix);
			Double lambda = 0.9;
			Integer iterations = 100;

			Integer convits = getConvits(map);
			Double preferences = -0.1;

			String kind = getOutputKind(map);
			AffinityConnectingMethod connMode = getConnMode(map);
			boolean takeLog = getTakeLog(map);
			boolean refine = getRefine(map);
			Integer steps = getSteps(map);

			RunAlgorithm alg = new RunAlgorithm(inputs, lambda, iterations,
					convits, preferences, kind);
			alg.setTakeLog(takeLog);
			alg.setConnMode(connMode);
			alg.setSteps(steps);
			alg.setRefine(refine);

			alg.setParemeters();
			List<Integer> results = alg.run();
			List<Double> objests = calcObjectValue(genotype, results, chromatrix, 0.9);

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
				+ fittest.getFitnessValue());
		DecimalFormat myformat = new DecimalFormat("#0.00");
		for (int i = 0; i < fited.length; i++) {

			System.out.println(myformat.format(fited[i]));

		}
	}
}
