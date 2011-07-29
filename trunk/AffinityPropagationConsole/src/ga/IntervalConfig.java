package ga;

public class IntervalConfig {

	/**
	 * @param maxgen ����������
	 * @param popsize Ⱥ����Ŀ
	 * @param maxfit �����Ӧ��
	 * @param inter ÿ������������
	 * @param p �������ȣ����೤�����ƴ���ʾһ������
	 * @param len ���в������ٸ�
	 */
	private int maxgen; 
	private int popsize; 
	private Double maxfit;  
	private Double[][] inter; 
	private int p; 
	private int len;
	
	public int getMaxgen() {
		return maxgen;
	}
	public void setMaxgen(int maxgen) {
		this.maxgen = maxgen;
	}
	public int getPopsize() {
		return popsize;
	}
	public void setPopsize(int popsize) {
		this.popsize = popsize;
	}
	public Double getMaxfit() {
		return maxfit;
	}
	public void setMaxfit(Double maxfit) {
		this.maxfit = maxfit;
	}
	public Double[][] getInter() {
		return inter;
	}
	public void setInter(Double[][] inter) {
		this.inter = inter;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public IntervalConfig(int maxgen, int popsize, Double maxfit,
			Double[][] inter, int p, int len) {
		super();
		this.maxgen = maxgen;
		this.popsize = popsize;
		this.maxfit = maxfit;
		this.inter = inter;
		this.p = p;
		this.len = len;
	}
	public IntervalConfig(int maxgen, int popsize, Double maxfit, int p) {
		super();
		this.maxgen = maxgen;
		this.popsize = popsize;
		this.maxfit = maxfit;
		this.p = p;
	}

}
