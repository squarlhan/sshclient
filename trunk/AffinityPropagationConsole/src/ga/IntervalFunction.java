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
  private int index; //这是第几条染色体？
  private IntervalConfig commencfg;
 

public IntervalConfig getCommencfg() {
	return commencfg;
}

public void setCommencfg(IntervalConfig commencfg) {
	this.commencfg = commencfg;
}

public IntervalFunction(Double[][] inters, int p, IntervalConfig commencfg, int index) {
	super();
	this.inters = inters;
	this.p = p;
	this.commencfg = commencfg;
	this.index = index;
}

public IntervalFunction(Double[][] inters, int p, Genotype genotype, int index) {
	super();
	this.inters = inters;
	this.p = p;
	this.genotype = genotype;
	this.index = index;
}

public IntervalFunction(Double[][] inters, int p) {
	super();
	this.inters = inters;
	this.p = p;
}

public IntervalFunction() {
	// TODO Auto-generated constructor stub
}

public int getIndex() {
	return index;
}

public void setIndex(int index) {
	this.index = index;
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

/**
 * 一个普通的遗传算法
 * @param commencfg 关于该遗传算法的配置
 * @return 返回程序终止前的种群
 */
public static Genotype commenga(IntervalConfig commencfg){
	
	Configuration.reset();
	Configuration comgaConf = new DefaultConfiguration();		
	comgaConf.setPreservFittestIndividual(true);
	comgaConf.setKeepPopulationSizeConstant(false);
	Genotype comgenotype = null;
	try {
		Gene[] genes = new DoubleGene[commencfg.getLen()];
		for(int i =0 ; i<= genes.length-1; i++){
			genes[i] = new DoubleGene(comgaConf, commencfg.getInter()[i][0], commencfg.getInter()[i][1]);
		}
		IChromosome sampleChromosome = new Chromosome(comgaConf, genes);
		comgaConf.setSampleChromosome(sampleChromosome);
		comgaConf.setPopulationSize(commencfg.getPopsize());
		comgaConf.setFitnessFunction(new InterMaxFunction(commencfg.getMaxfit()));
		comgenotype = Genotype.randomInitialGenotype(comgaConf);
	} catch (InvalidConfigurationException e) {
		e.printStackTrace();
		System.exit(-2);
	}
	int progress = 0;
	int percentEvolution = commencfg.getMaxgen() / 10;
	for (int i = 0; i < commencfg.getMaxgen(); i++) {
		comgenotype.evolve();
	}
	// Print summary.
	// --------------
//	IChromosome fittest = comgenotype.getFittestChromosome();
//	double[] fited = Bin2Dec.binstr2decstr(fittest, commencfg.getP(), commencfg.getInter());
//	System.out.print("Fittest Chromosome has fitness "+ (commencfg.getMaxfit()-fittest.getFitnessValue()));
//	DecimalFormat myformat = new DecimalFormat("#0.00");
//	System.out.print("   :   ");
//	for (int i = 0; i < fited.length; i++) {
//		System.out.print(myformat.format(fited[i])+"  ");
//	}
//	System.out.println();
	return comgenotype;
}


public double evaluate(IChromosome a_subject, IChromosome[] chorms) {
    try {
		Thread.sleep(0);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		Double[][] cominters = new Double[a_subject.size()][2];
		for(int b = 0; b<= inters.length-1; b++){
			int index = (Integer) a_subject.getGene(b).getAllele();
			double min = inters[b][0];
			double max = inters[b][1];
			cominters[b][0] = min + index*(max-min)/p;
			cominters[b][1] = min + (index+1)*(max-min)/p;                           
		}
		commencfg.setInter(cominters);
		commencfg.setLen(cominters.length);
		genotype = commenga(commencfg);

    
    Population pop = genotype.getPopulation();
    double avgfit = 0;
   for(int i = 0; i<=pop.size()-1;i++){
	   avgfit+=pop.getChromosome(i).getFitnessValue();
   }
   chorms[index] = genotype.getFittestChromosome();
//    return avgfit/pop.size();
   return genotype.getFittestChromosome().getFitnessValue();
  }

@Override
protected double evaluate(IChromosome a_subject) {
	// TODO Auto-generated method stub
	return -1.0;
}
}
