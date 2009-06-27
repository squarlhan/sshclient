/**
 * 
 */
package cn.edu.jlu.ccst.sshclient.inter;

/**
 * @author Woden
 * 对任务组和任务的动作接口
 */
public interface BaseAction {
	
	/**
	 * 启动一个任务(组)
	 * @return 这个任务的pid
	 */
	String start();
	/**
	 * 终止一个任务(组)
	 */
	void stop();

}
