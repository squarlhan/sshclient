/**
 * 
 */
package cn.edu.jlu.ccst.sshclient.model;

import java.util.Date;
import java.util.List;

import cn.edu.jlu.ccst.sshclient.inter.BaseAction;
import cn.edu.jlu.ccst.sshclient.inter.BaseOperation;

/**�������ʵ����
 * @author Woden
 *
 */
public class SSHGroup extends BaseClass implements BaseAction, BaseOperation {

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param memo
	 * @param creatdate
	 */
	
	private List<SSHTask> sts;
	private SSHComputer cp;
	
	public SSHGroup(String id, String name, byte type, String memo,
			Date creatdate) {
		super(id, name, type, memo, creatdate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 * @param type
	 * @param creatdate
	 */
	public SSHGroup(String name, byte type, Date creatdate) {
		super(name, type, creatdate);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public SSHGroup() {
		// TODO Auto-generated constructor stub
	}
	
	
	
public SSHGroup(String id, String name, byte type, String memo,
			Date creatdate, List<SSHTask> sts, SSHComputer cp) {
		super(id, name, type, memo, creatdate);
		this.sts = sts;
		this.cp = cp;
	}

public List<SSHTask> getSts() {
		return sts;
	}

	public void setSts(List<SSHTask> sts) {
		this.sts = sts;
	}

	public SSHComputer getCp() {
		return cp;
	}

	public void setCp(SSHComputer cp) {
		this.cp = cp;
	}

	//����ķ�����Ҫʵ��
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

         @Override
        public String toString(){
            return "group--id:"+id+";name:"+name+";memo:"+memo+";date:"+creatdate;
        }

}
