package affinitymain;

import algorithm.abs.AffinityPropagationAlgorithm;
import algorithm.abs.AffinityPropagationAlgorithm.AffinityConnectingMethod;
import algorithm.abs.Cluster;
import algorithm.abs.ClusterInteger;
import algorithm.matrix.MatrixPropagationAlgorithm;
import algorithm.smart.SmartPropagationAlgorithm;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import distance.EucDistance;
import listeners.ConsoleIterationListener;

/**
 *
 * @author misiek (mw219725@gmail.com)
 */
public class RunAlgorithm {

    private Collection<InteractionData> inputs;
    private String outpath;
    private AffinityPropagationAlgorithm af = new SmartPropagationAlgorithm();
    private double lambda;
    private int iterations;
    private double preferences;
    private Integer convits;
    private Collection<String> nodeNames = new HashSet<String>();
    private String kind;
    private boolean takeLog;
    private boolean refine;
    private Integer steps = null;
    private AffinityConnectingMethod connMode;

    public RunAlgorithm(Collection<InteractionData> inputs, String outpath, double lambda, int iterations, Integer convits, double preferences, String kind) {
        this.inputs = inputs;
        this.outpath = outpath;
        this.lambda = lambda;
        this.iterations = iterations;
        this.preferences = preferences;
        this.kind = kind;
        this.convits = convits;
    }

    public void setRefine(boolean refine) {
        this.refine = refine;
    }

    public void setConnMode(AffinityConnectingMethod connMode) {
        this.connMode = connMode;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public void setParemeters() {
        af.setLambda(lambda);
        af.setIterations(iterations);
        af.setConvits(convits);
        af.setSteps(steps);
        af.setRefine(refine);
        af.setConnectingMode(connMode);
        af.setConnectingMode(AffinityPropagationAlgorithm.AffinityConnectingMethod.ORIGINAL);
        af.addIterationListener(new ConsoleIterationListener(iterations));

        Iterator iter = inputs.iterator();
        while(iter.hasNext()){
        	InteractionData temp = (InteractionData)iter.next();
        	nodeNames.add(temp.getFrom());
            nodeNames.add(temp.getTo());
        }
        //    af.setN(ints.size());
        af.setN(nodeNames.size()+1);

        af.init();
        for (InteractionData intData : inputs) {
            //     System.out.println(intData.getFrom() + " " + intData.getTo() + " " + intData.getSim());
            Double val;
            if (takeLog) {
                if (intData.getSim() > 0) {
                    val = Math.log(intData.getSim());
                } else {
                    val = Double.valueOf(0);
                }
            } else {
                val = intData.getSim();
            }
            int source = (int)Double.valueOf(intData.getFrom()).doubleValue();
            int target = (int)Double.valueOf(intData.getTo()).doubleValue();
            af.setSimilarityInt(source, target, val);
            //   af.setSimilarityInt(target, source, val);
            //af.setSimilarityInt(Integer.valueOf(intData.getFrom()), Integer.valueOf(intData.getTo()), val);
        }
        Double pref;
        if (takeLog) {
            if (preferences > 0) {
                pref = Math.log(preferences);
            } else {
                pref = Double.valueOf(0);
            }
        } else {
            pref = preferences;
        }

        System.out.println("pref: " + pref);
        af.setConstPreferences(pref);
    }

    public void run() {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedWriter bw = null;
        try {
            File outputCenters = new File(outpath);
            fos = new FileOutputStream(outputCenters);
            bos = new BufferedOutputStream(fos);
            bw = new BufferedWriter(new OutputStreamWriter(bos));
            System.out.println(kind);
            if (kind.equals("centers")) {
                Map<Integer, ClusterInteger> clusters = af.doClusterAssocInt();
                //Map<Integer, Cluster<Integer>> clusters = af.doClusterAssocInt();
                if (clusters != null) {
                    for (Integer clustName : clusters.keySet()) {
                        //for (Integer clustName : clusters.keySet()) {
                        bw.append(clustName + "\n");
                    }
                }
            } else {
                Map<Integer, Integer> clusters = af.doClusterInt();
                if (clusters != null) {
                    for (Entry<Integer, Integer> entry : clusters.entrySet()) {
                        bw.append(entry.getValue() + "\n");
                    }
                }/*
                Map<String, String> clusters = af.doCluster();
                if (clusters != null) {
                for (Entry<String, String> entry : clusters.entrySet()) {
                bw.append(entry.getKey() + " " + entry.getValue() + "\n");
                }
                }*/
            }

        } catch (IOException ex) {
            Logger.getLogger(RunAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                bos.close();
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(RunAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void setTakeLog(boolean takeLog) {
        this.takeLog = takeLog;
    }
}
