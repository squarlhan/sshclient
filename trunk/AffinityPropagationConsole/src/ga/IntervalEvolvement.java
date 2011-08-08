package ga;

	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.text.DecimalFormat;

	import org.jgap.*;
import org.jgap.impl.*;

	/**
	 * 定义域进化算法，其实是两个遗传算法，首先把定义域分成几个小区间，在这个几个小区间内分别用遗传算法优化参数
	 * 每隔N代，再利用遗传算法进化区间，这样能避免局部极小值
	 * @author Wu Chunguo
	 * @author Han Xiaosong
	 * @since 1.0
	 */
	public class IntervalEvolvement {
		
		private IChromosome[] tempcroms = null;
		public IChromosome[] getTempcroms() {
			return tempcroms;
		}

		public void setTempcroms(IChromosome[] tempcroms) {
			this.tempcroms = tempcroms;
		}

		/**
		 * 一个普通的遗传算法
		 * @param commencfg 关于该遗传算法的配置
		 * @return 返回程序终止前的种群
		 */
		public Genotype commenga(IntervalConfig commencfg){
			
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
				// Print progress.
				// ---------------
//				if (percentEvolution > 0 && i % percentEvolution == 0) {
//					progress++;
//					IChromosome fittest = genotype.getFittestChromosome();
//					double fitness = fittest.getFitnessValue();
//					System.out.println("Currently fittest Chromosome has fitness "+ fitness);
//				}
			}
			// Print summary.
			// --------------
			IChromosome fittest = comgenotype.getFittestChromosome();
			System.out.println("Fittest Chromosome has fitness "+ (commencfg.getMaxfit()-fittest.getFitnessValue()));
			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < fittest.size(); i++) {
				System.out.print(myformat.format(fittest.getGene(i).getAllele())+"  ");
			}
			System.out.println();
			return comgenotype;
		}
		
		/**
		 * 区间进化的遗传算法
		 * @param intervalcfg 区间进化的配置
		 * @param commencfg 区间内参数遗传算法的配置
		 * @return 返回两个最佳染色体，第一个是区间染色体，第二个是此区间下的最佳参数染色体
		 */
		public  IChromosome[] intervalga(IntervalConfig intervalcfg, IntervalConfig commencfg){
			
			IChromosome[] result = new IChromosome[2]; 
			Configuration gaConf = new DefaultConfiguration();
			gaConf.setPreservFittestIndividual(true);
			gaConf.setKeepPopulationSizeConstant(false);
			Genotype genotype = null;
			try {
				Gene[] genes = new IntegerGene[intervalcfg.getLen()];
				for(int i =0 ; i<= genes.length-1; i++){
					genes[i] = new IntegerGene(gaConf, 0, intervalcfg.getP()-1);
				}
				IChromosome sampleChromosome = new Chromosome(gaConf, genes);
				gaConf.setSampleChromosome(sampleChromosome);
				gaConf.setPopulationSize(intervalcfg.getPopsize());
				gaConf.setFitnessFunction(new IntervalFunction());
				genotype = Genotype.randomInitialGenotype(gaConf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
				System.exit(-2);
			}
			int progress = 0;
			int percentEvolution = intervalcfg.getMaxgen() / 10;
			for (int i = 0; i < intervalcfg.getMaxgen(); i++) {
				
				
				genotype.evolve(intervalcfg, commencfg, this);
				// Print progress.
				// ---------------
				if (percentEvolution > 0 && i % percentEvolution == 0) {
					progress++;
					IChromosome fittest = genotype.getFittestChromosome();
					double fitness = fittest.getFitnessValue();
					System.out.println("Currently fittest Domain has fitness "+ fitness);
				}
			}
			// Print domain summary.
			// --------------
			IChromosome fittest = genotype.getFittestChromosome();
			result[0] = fittest;
			
			System.out.println("Fittest Domain has fitness "+ (fittest.getFitnessValue()));
			Double[][] cominters = new Double[fittest.size()][2];
			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < fittest.size(); i++) {
				int index = (Integer) fittest.getGene(i).getAllele();
				double min = intervalcfg.getInter()[i][0];
				double max = intervalcfg.getInter()[i][1];
				if(i%2==0){					
					cominters[i][0] = min + index*(max-min)/intervalcfg.getP();
					System.out.print(myformat.format(cominters[i][0])+"		");
				}else{
					cominters[i][1] = min + (index+1)*(max-min)/intervalcfg.getP();
					System.out.println(myformat.format(cominters[i][1])+"   ");
				}
			}
			System.out.println("pop size: "+genotype.getPopulation().size());
			System.out.println("chrom size: "+tempcroms.length);
			for(int i = 0; i<=genotype.getPopulation().size()-1; i++ ){
				if(genotype.getPopulation().getChromosome(i).equals(fittest)){
					result[1] = tempcroms[i];
					break;
				}	
			}

			System.out.println("Fittest Parameter has fitness "+ (commencfg.getMaxfit()-result[1].getFitnessValue()));
			for (int i = 0; i < result[1].size(); i++) {		
					System.out.print(myformat.format(result[1].getGene(i).getAllele())+" ");
			}
			System.out.println();
			return result;
		}
		
		public static void originga() throws IOException{
			File result = new File("purega.txt");
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
			

				long startTime = System.currentTimeMillis();
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
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
					System.exit(-2);
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
						System.out.println("Currently fittest Chromosome has fitness "+ fitness);
						output.write(String.valueOf(fitness)+"\n");
					}
				}
				// Print summary.
				// --------------
				IChromosome fittest = genotype.getFittestChromosome();
				double[] fited = Bin2Dec
						.binstr2decstr(fittest, 20, 5.12, -5.12);
				System.out.println("Fittest Chromosome has fitness "
						+ (maxFitness-fittest.getFitnessValue()));
				output.write("Fittest Chromosome has fitness "
						+ (maxFitness-fittest.getFitnessValue())+"\n");
				DecimalFormat myformat = new DecimalFormat("#0.00");
				for (int i = 0; i < fited.length; i++) {

					System.out.println(myformat.format(fited[i]));
					output.write(String.valueOf(myformat.format(fited[i]))+"\n");

				}
				long endTime = System.currentTimeMillis();
				System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
				output.write("程序运行时间： " + (endTime - startTime) + "ms"+"\n");
				output.close();
		}
		
		public static void main(String[] args) {
			
			Double[][] inter = new Double[30][2];
			for(int a = 0; a<=29; a++){
				inter[a][0] = -10.0;
				inter[a][1] = 10.0;
			}
			Double maxfit = 222.0;
			maxfit = 3330.0;
			IntervalConfig intervalcfg = new IntervalConfig(10, 20,  maxfit,  inter, 20, 30);
			IntervalConfig commencfg = new IntervalConfig(100, 20,  maxfit);
			IntervalEvolvement ie = new IntervalEvolvement();
			ie.intervalga(intervalcfg, commencfg);		
			
			Double[][] cominter = new Double[30][2];
			for(int a = 0; a<=29; a++){
				cominter[a][0] = -10.0;
				cominter[a][1] = 10.0;
			}
			//Double commaxfit = 222.0;
//			maxfit = 3330.0;
			IntervalConfig comcfg = new IntervalConfig(10000, 20,  maxfit, cominter, 30);
//			ie.commenga(comcfg);		
		}
	}

