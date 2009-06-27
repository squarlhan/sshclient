/**
 * 
 */
package cn.edu.jlu.ccst.sshclient.model;

import java.io.File;
import java.util.Date;
import java.util.List;

import cn.edu.jlu.ccst.sshclient.inter.BaseAction;
import cn.edu.jlu.ccst.sshclient.inter.BaseOperation;

/**
 * @author Woden
 *
 */
public class SSHTask extends BaseClass implements BaseAction, BaseOperation {

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param memo
	 * @param creatdate
	 */
	
	private String cmd;
	private List<String> params;
	private File in;
	private File out;
	private SSHGroup gp;
	
	public SSHTask(String id, String name, byte type, String memo,
			Date creatdate) {
		super(id, name, type, memo, creatdate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param type
	 * @param creatdate
	 */
	public SSHTask(String name, byte type, Date creatdate) {
		super(name, type, creatdate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public SSHTask() {
		// TODO Auto-generated constructor stub
	}	
	
    public SSHTask(String id, String name, byte type, String memo,
			Date creatdate, String cmd, List<String> params, File in, File out,
			SSHGroup gp) {
		super(id, name, type, memo, creatdate);
		this.cmd = cmd;
		this.params = params;
		this.in = in;
		this.out = out;
		this.gp = gp;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public List<String> getParams() {
		return params;
	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public File getIn() {
		return in;
	}

	public void setIn(File in) {
		this.in = in;
	}

	public File getOut() {
		return out;
	}

	public void setOut(File out) {
		this.out = out;
	}

	public SSHGroup getGp() {
		return gp;
	}

	public void setGp(SSHGroup gp) {
		this.gp = gp;
	}

	//下面的方法需要实现
	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseAction#start()
	 */
	@Override
	public String start() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseAction#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseOperation#creat()
	 */
	@Override
	public boolean creat() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseOperation#find(java.lang.String)
	 */
	@Override
	public List<BaseClass> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseOperation#load(java.lang.String)
	 */
	@Override
	public BaseClass load(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseOperation#remove()
	 */
	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.jlu.ccst.sshclient.inter.BaseOperation#update()
	 */
	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

}
