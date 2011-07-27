package ga;

	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.text.DecimalFormat;

	import org.jgap.*;
	import org.jgap.impl.*;

	/**
	 * ����������㷨����ʵ�������Ŵ��㷨�����ȰѶ�����ֳɼ���С���䣬���������С�����ڷֱ����Ŵ��㷨�Ż�����
	 * ÿ��N�����������Ŵ��㷨�������䣬�����ܱ���ֲ���Сֵ
	 * @author Wu Chunguo
	 * @author Han Xiaosong
	 * @since 1.0
	 */
	public class IntervalEvolvement {
		/**
		 * һ����ͨ���Ŵ��㷨
		 * @param commencfg ���ڸ��Ŵ��㷨������
		 * @return ���س�����ֹǰ����Ⱥ
		 */
		public static Genotype commenga(IntervalConfig commencfg){
			
			Configuration gaConf = new DefaultConfiguration();
			gaConf.setPreservFittestIndividual(true);
			gaConf.setKeepPopulationSizeConstant(false);
			Genotype genotype = null;
			try {
				IChromosome sampleChromosome = new Chromosome(gaConf,
						new BooleanGene(gaConf), commencfg.getP()*commencfg.getLen());
				gaConf.setSampleChromosome(sampleChromosome);
				gaConf.setPopulationSize(commencfg.getPopsize());
				gaConf.setFitnessFunction(new InterMaxFunction(commencfg.getInter(), commencfg.getP() , commencfg.getMaxfit()));
				genotype = Genotype.randomInitialGenotype(gaConf);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
				System.exit(-2);
			}
			int progress = 0;
			int percentEvolution = commencfg.getMaxgen() / 10;
			for (int i = 0; i < commencfg.getMaxgen(); i++) {
				genotype.evolve();
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
//			IChromosome fittest = genotype.getFittestChromosome();
//			double[] fited = Bin2Dec
//					.binstr2decstr(fittest, commencfg.getP(), commencfg.getInter());
//			System.out.println("Fittest Chromosome has fitness "
//					+ (commencfg.getMaxfit()-fittest.getFitnessValue()));
//			DecimalFormat myformat = new DecimalFormat("#0.00");
//			for (int i = 0; i < fited.length; i++) {
//
//				System.out.println(myformat.format(fited[i]));
//
//			}
			
			return genotype;
		}
		
		/**
		 * ����������Ŵ��㷨
		 * @param intervalcfg �������������
		 * @param commencfg �����ڲ����Ŵ��㷨������
		 * @return �����������Ⱦɫ�壬��һ��������Ⱦɫ�壬�ڶ����Ǵ������µ���Ѳ���Ⱦɫ��
		 */
		public static IChromosome[] intervalga(IntervalConfig intervalcfg, IntervalConfig commencfg){
			
			IChromosome[] result = new IChromosome[2]; 
			
			Configuration gaConf = new DefaultConfiguration();
			gaConf.setPreservFittestIndividual(true);
			gaConf.setKeepPopulationSizeConstant(false);
			Genotype genotype = null;
			Genotype[] intergt = new Genotype[intervalcfg.getPopsize()];
			Genotype bestinter = null;
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
				for(int a = 0; a<=intervalcfg.getPopsize()-1;a++ ){
					double[] inters = Bin2Dec.binstr2decstr_interval(
							genotype.getPopulation().getChromosome(a), 
							intervalcfg.getP(), 
							intervalcfg.getInter()
							);
					Double[][] cominters = new Double[2][inters.length/2];
					for(int b = 0; b<= inters.length-1; b+=2){
						cominters[0][b] = inters[b];
						cominters[1][b] = inters[b] + inters[b+1];                           
					}
					commencfg.setInter(cominters);
					commencfg.setLen(intervalcfg.getLen()/2);
					intergt[a] = commenga(commencfg);
				}
				
				genotype.evolve();
				// Print progress.
				// ---------------
				if (percentEvolution > 0 && i % percentEvolution == 0) {
					progress++;
					IChromosome fittest = genotype.getFittestChromosome();
					double fitness = fittest.getFitnessValue();
					System.out.println("Currently fittest Chromosome has fitness "+ fitness);
				}
			}
			// Print summary.
			// --------------
			IChromosome fittest = genotype.getFittestChromosome();
			double[] fited = Bin2Dec
					.binstr2decstr_interval(fittest, intervalcfg.getP(), intervalcfg.getInter());
			System.out.println("Fittest Chromosome has fitness "
					+ (fittest.getFitnessValue()));
			DecimalFormat myformat = new DecimalFormat("#0.00");
			for (int i = 0; i < fited.length; i++) {

				System.out.println(myformat.format(fited[i]));

			}
			result[0] = fittest;
			for(int i = 0; i<=genotype.getPopulation().size()-1; i++ ){
				if(genotype.getPopulation().getChromosome(i).equals(fittest))
					result[1] = intergt[i].getFittestChromosome();
			}
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
				System.out.println("��������ʱ�䣺 " + (endTime - startTime) + "ms");
				output.write("��������ʱ�䣺 " + (endTime - startTime) + "ms"+"\n");
				output.close();
		}
		
		public static void main(String[] args) {
			
			Double[][] inter = {
					{-5.12, 5.12},
					{-5.12, 5.12},
					{-5.12, 5.12},
					{-5.12, 5.12}
			};
			Double maxfit = 4 * Math.pow(5.12, 2.0);
			IntervalConfig commencfg = new IntervalConfig(200, 20,  maxfit,  inter, 20, 4);
			commenga(commencfg);
				
				
		}
	}

