/**
 * 
 */
package cn.edu.jlu.ccst.sshclient.model;

import java.util.Date;
import java.util.List;

import cn.edu.jlu.ccst.sshclient.inter.BaseOperation;

/** 计算机的实体类
 * @author Woden
 *
 */
public class SSHComputer extends BaseClass implements BaseOperation{

	private String username;
	private String password;
	private String host;
	private List<SSHGroup> gps;
		
	public SSHComputer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SSHComputer(String name, byte type, Date creatdate) {
		super(name, type, creatdate);
		// TODO Auto-generated constructor stub
	}
	public SSHComputer(String id, String name, byte type, String memo,
			Date creatdate) {
		super(id, name, type, memo, creatdate);
		// TODO Auto-generated constructor stub
	}	
	
	public SSHComputer(String id, String name, byte type, String memo,
			Date creatdate, String username, String password, String host,
			List<SSHGroup> gps) {
		super(id, name, type, memo, creatdate);
		this.username = username;
		this.password = password;
		this.host = host;
		this.gps = gps;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public List<SSHGroup> getGps() {
		return gps;
	}
	public void setGps(List<SSHGroup> gps) {
		this.gps = gps;
	}
	//下面的方法需要实现
	@Override
	public boolean creat() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<BaseClass> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BaseClass load(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
