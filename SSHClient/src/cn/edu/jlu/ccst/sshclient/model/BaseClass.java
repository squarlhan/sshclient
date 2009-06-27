package cn.edu.jlu.ccst.sshclient.model;

import java.util.Date;

public class BaseClass {

	protected String id;
	protected String name;
	protected byte type;
	protected String memo;
	protected Date creatdate;	
	
	public BaseClass(String id, String name, byte type, String memo, Date creatdate) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.memo = memo;
		this.creatdate = creatdate;
	}
	
	public BaseClass(String name, byte type, Date creatdate) {
		super();
		this.name = name;
		this.type = type;
		this.creatdate = creatdate;
	}	

	public BaseClass() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}
	
	
}
