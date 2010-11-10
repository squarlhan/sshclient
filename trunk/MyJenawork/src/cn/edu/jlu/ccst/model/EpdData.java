package cn.edu.jlu.ccst.model;

public class EpdData {

	private String epdid;
	private String geneid;
	private String mrnaid;
	private String proteinid;
	
	public EpdData() {
		super();
		// TODO Auto-generated constructor stub
		epdid = "";
		geneid = "";
		mrnaid = "";
		proteinid = "";
	}

	public EpdData(String epdid, String geneid, String mrnaid, String proteinid) {
		super();
		this.epdid = epdid;
		this.geneid = geneid;
		this.mrnaid = mrnaid;
		this.proteinid = proteinid;
	}

	public String getEpdid() {
		return epdid;
	}

	public void setEpdid(String epdid) {
		this.epdid = epdid;
	}

	public String getGeneid() {
		return geneid;
	}

	public void setGeneid(String geneid) {
		this.geneid = geneid;
	}

	public String getMrnaid() {
		return mrnaid;
	}

	public void setMrnaid(String mrnaid) {
		this.mrnaid = mrnaid;
	}

	public String getProteinid() {
		return proteinid;
	}

	public void setProteinid(String proteinid) {
		this.proteinid = proteinid;
	}
		
}
