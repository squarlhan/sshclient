package ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;

import affinitymain.CommandLineParser;
import affinitymain.InteractionData;
import affinitymain.RunAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;
import distance.EucDistance;

public class clustObjectFun {
	
	private static List<Integer> results;
	private static List<Double> objects;
	
	 /**
     * @param args the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

//        if (args.length < 2) {
//            showHelp();
//            return;
//        }

        Map<String, String> map = CommandLineParser.parseTokens(args);
        double[][] datamatrix = EucDistance.getData("data/b.txt");
        Collection<InteractionData> inputs = EucDistance.getunEucMatrix(datamatrix);
        Double lambda = 0.9;
        Integer iterations = 500;
      
        Integer convits = getConvits(map);
        Double preferences = -0.1;
        
        String kind = getOutputKind(map);
        AffinityConnectingMethod connMode = getConnMode(map);
        boolean takeLog = getTakeLog(map);
        boolean refine = getRefine(map);
        Integer steps = getSteps(map);

        RunAlgorithm alg = new RunAlgorithm(inputs, lambda, iterations, convits, preferences, kind);
        alg.setTakeLog(takeLog);
        alg.setConnMode(connMode);
        alg.setSteps(steps);
        alg.setRefine(refine);

        alg.setParemeters();
        results = alg.run();
        //calcObjectValue(results, datamatrix, 0.9);
    }

    public static  List<Double> calcObjectValue(Population pop, List<Integer> results, double[][] datamatrix, double lamda){
    	MaxFunction mf = new MaxFunction();
		List<IChromosome> chrs = pop.getChromosomes();
		List<Double> objects = new ArrayList();
		Set<Integer> centers = new HashSet();
		centers.addAll(results);
		Map<Integer, Double> centerObjects = new HashMap();
		Iterator iter = centers.iterator();
		while(iter.hasNext()){
			int a = (Integer)iter.next();
			//do something to get the objective value
			Double dis = mf.evaluate(chrs.get(a));
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
			pop.getChromosome(i).setFitnessValue(object);
		}
		/*for(double a : objects){
        	System.out.println(a);
        }*/

		return objects;
	}
	
	public static AffinityConnectingMethod getConnMode(Map<String, String> map) {
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

	public static String getOutputKind(Map<String, String> map) {
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

	public static Double getPreferences(Map<String, String> map) {
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

	public static Double getLambda(Map<String, String> map) {
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

	public static Integer getIterations(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("it"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

	public static Integer getConvits(Map<String, String> map) {
        try {
            return Integer.valueOf(map.get("con"));
        } catch (NumberFormatException e) {
            return null;
        }
    }

	public static String getFilepath(Map<String, String> map) {
        return map.get("in");
    }

	public static String getFoutput(Map<String, String> map) {
        return map.get("out");
    }

	public static boolean getRefine(Map<String, String> map) {
        String ref = map.get("ref");
        if (ref == null) {
            return true;
        } else if (ref.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

	public static Integer getSteps(Map<String, String> map) {
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

	public static boolean getTakeLog(Map<String, String> map) {
        String getLog = map.get("log");
        if (getLog == null) {
            return false;
        } else if (getLog.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
