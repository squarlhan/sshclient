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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
public class MaxFunction
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
//  public double evaluate(IChromosome a_subject) {
//    int total = 0;
//
//    for (int i = 0; i < a_subject.size(); i++) {
//      BooleanGene value = (BooleanGene) a_subject.getGene(a_subject.size() -
//          (i + 1));
//      if (value.booleanValue()) {
//        total += Math.pow(2.0, (double) i);
//      }
//    }
//
//    return total;
//  }
  
  private List<List<String>> matrix;
  private List<Integer> myweight;
  private List<Integer> myfrq;
  
  public MaxFunction(List<List<String>> matrix, List<Integer> myweight, List<Integer> myfrq){
	  this.matrix = matrix;
	  this.myweight = myweight;
	  this.myfrq = myfrq;
	  //MaxFunction();
  }
  public double evaluate(IChromosome a_subject) {
	    

	    int total = 0;
		List<Integer> pos = new ArrayList();
		for (int i = 0; i < a_subject.size()-1; i++){
			BooleanGene value = (BooleanGene) a_subject.getGene(i);
			if (value.booleanValue()) {
				pos.add(i);
			}
		}
			
		List<Integer> sum = new ArrayList();
		for(List<String> pa: matrix){
			List<Integer> gepos = new ArrayList();
			for (int i = 0; i < pa.size()-1; i++){
				if(pa.get(i).equals("1"))gepos.add(i);
			}
			int a = 0;
			int sumitem = 0;
			int np = 0;
			while(a<pos.size()-1){
				boolean flag = false;				
				for(int p:gepos){
					if(p>=pos.get(a)&&p<pos.get(a+1)){
						flag=true;
						break;
					}
					np = p;
				}
				if(flag){
					 int tsum = 0;
		             for (int ks=pos.get(a); ks<=pos.get(a+1)-1; ks++)
		                 tsum = tsum+myweight.get(ks);
		             sumitem += tsum;
				}
				a++;
			}
			if(np>=pos.get(pos.size()-1)){
				int tsum = 0;
				for(int ks = pos.get(pos.size()-1); ks < matrix.get(0).size();ks++)
					tsum+=myweight.get(ks);
				if(pos.get(0)>1)
				{
					for(int ks = 0; ks < pos.get(0);ks++)
						tsum+=myweight.get(ks);
				}
				sumitem += tsum;
			}
			sum.add(sumitem);
		}
        
	    for (int i = 0; i < sum.size(); i++) {	      
	        total += sum.get(i)*myfrq.get(i);
	    }
	    
	    int total1 = 0;
	    int a = 0;
	    while (a < pos.size()-1){
	    	int sumitem = 0;
	    	for(int i = pos.get(a); i<=pos.get(a+1)-1;i++){
	    		sumitem += myweight.get(i);
	    	}
	    	total1 += Math.abs(sumitem-10);
	         a++;
	    }
	    int tsum = 0;
		for(int ks = pos.get(pos.size()-1); ks < matrix.get(0).size();ks++)
			tsum+=myweight.get(ks);
		if(pos.get(0)>1)
		{
			for(int ks = 0; ks < pos.get(0);ks++)
				tsum+=myweight.get(ks);
		}
		total1 = total1+Math.abs(tsum-10);
		
		double res = Math.pow((double)pos.size(),1/3)/(Math.pow((double)total,1/3)+Math.pow((double)total1,1/2));
//	    System.out.println("fitness:"+pos.size()+"/"+total+" :"+Math.pow((double)pos.size(),0.5)/(double)total);
	    return res;
	  }
}
