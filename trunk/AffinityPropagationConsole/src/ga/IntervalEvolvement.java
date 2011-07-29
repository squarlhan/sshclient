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
		
		public static IChromosome[] tempcroms = null;
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
				IChromosome sampleChromosome = new Chromosome(comgaConf,
						new BooleanGene(comgaConf), commencfg.getP()*commencfg.getLen());
				comgaConf.setSampleChromosome(sampleChromosome);
				comgaConf.setPopulationSize(commencfg.getPopsize());
				comgaConf.setFitnessFunction(new InterMaxFunction(commencfg.getInter(), commencfg.getP() , commencfg.getMaxfit()));
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
			double[] fited = Bin2Dec.binstr2decstr(fittest, commencfg.getP(), commencfg.getInter());
			System.out.println("Fittest Chromosome has fitness "+ (commencfg.getMaxfit()-fittest.getFitnessValue()));
			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < fited.length; i++) {
				System.out.print(myformat.format(fited[i])+"  ");
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
		public static IChromosome[] intervalga(IntervalConfig intervalcfg, IntervalConfig commencfg){
			
			IChromosome[] result = new IChromosome[2]; 
			Configuration gaConf = new DefaultConfiguration();
			gaConf.setPreservFittestIndividual(true);
			gaConf.setKeepPopulationSizeConstant(false);
			Genotype genotype = null;
			try {
				IChromosome sampleChromosome = new Chromosome(gaConf,
						new BooleanGene(gaConf), intervalcfg.getP()*intervalcfg.getLen());
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
				
				
				genotype.evolve(intervalcfg, commencfg);
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
			
			double[] fited = Bin2Dec.binstr2decstr_interval(fittest, intervalcfg.getP(), intervalcfg.getInter());
			System.out.println("Fittest Domain has fitness "+ (fittest.getFitnessValue()));
			Double[][] cominters = new Double[fited.length/2][2];
			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < fited.length; i++) {
				if(i%2==0){
					System.out.print(myformat.format(fited[i])+"  ");
					cominters[i/2][0] = fited[i];
				}else{
					System.out.println(myformat.format(fited[i]+fited[i-1])+"   ");
					cominters[(i-1)/2][1] = fited[i]+fited[i-1];
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
			double[] interfited = Bin2Dec.binstr2decstr(result[1], commencfg.getP(), cominters);
			System.out.println("Fittest Parameter has fitness "+ (commencfg.getMaxfit()-result[1].getFitnessValue()));
			for (int i = 0; i < interfited.length; i++) {		
					System.out.print(myformat.format(interfited[i])+" ");
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
			
			Double[][] inter = new Double[60][2];
			for(int a = 0; a<=59; a++){
				inter[a][0] = -10.0;
				inter[a][1] = 10.0;
			}
			Double maxfit = 222.0;
			maxfit = 3330.0;
			IntervalConfig intervalcfg = new IntervalConfig(10, 10,  maxfit,  inter, 20, 60);
			IntervalConfig commencfg = new IntervalConfig(100, 20,  maxfit, 20);
			intervalga(intervalcfg, commencfg);		
			
			Double[][] cominter = new Double[30][2];
			for(int a = 0; a<=29; a++){
				cominter[a][0] = -10.0;
				cominter[a][1] = 10.0;
			}
			//Double commaxfit = 222.0;
			maxfit = 3330.0;
			IntervalConfig comcfg = new IntervalConfig(100, 20,  maxfit, cominter, 20, 30);
//			commenga(comcfg);		
		}
	}

