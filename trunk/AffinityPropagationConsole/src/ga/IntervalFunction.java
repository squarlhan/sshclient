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
 * 区间进化适应度，用来衡量区间的适应度
 *
 * @author Wu Chunguo
 * @author Han Xiaosong
 * @since 1.0
 */
public class IntervalFunction   extends FitnessFunction {

  /**
	 * 
	 */
	private static final long serialVersionUID = 2384811441099976989L;
/**
   * 二进制编码实数，每个区间用两个实数表示，第一个实数是本区间的起点，后一个实数是本区间长度
   * 适应度的计算分两部分，第一部分是子区间多维体积，用每个区间的长度乘积表示，V 越小越好
   * 第二部分是该子区间内参数群体的平均适应度， F越大越好
   * 适应度是F/V
   *
   * @param a_subject the Chromosome to be evaluated
   * @return defect rate of our problem
   *
 * @author Wu Chunguo
 * @author Han Xiaosong
 * @since 1.0
   */
  
  private Double[][] inters; // 每个参数的取值范围
  private int p; // 参数精度
  private Genotype genotype; //某子空间内参数进化结果


public IntervalFunction(Double[][] inters, int p, Genotype genotype) {
	super();
	this.inters = inters;
	this.p = p;
	this.genotype = genotype;
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



public Genotype getGenotype() {
	return genotype;
}

public void setGenotype(Genotype genotype) {
	this.genotype = genotype;
}

public double evaluate(IChromosome a_subject) {
    double total = 0;
    try {
		Thread.sleep(0);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    double[] decs = Bin2Dec.binstr2decstr_interval(a_subject, p, inters);
    for (int i = 1; i <= decs.length-2; i+=2) {      
        total *= decs[i];     
    }
    Population pop = genotype.getPopulation();
    double avgfit = 0;
   for(int i = 0; i<=pop.size()-1;i++){
	   avgfit+=pop.getChromosome(i).getFitnessValue();
   }

    return avgfit/(pop.size()*total);
  }
}
