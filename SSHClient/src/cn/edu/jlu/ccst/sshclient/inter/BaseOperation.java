package cn.edu.jlu.ccst.sshclient.inter;

import java.util.List;

import cn.edu.jlu.ccst.sshclient.model.BaseClass;

/***
 * 这个接口声明一些基本的操作
 * @author Woden
 *
 */

public interface BaseOperation {
	
	/**
	 * 在配置文件中创建一个实体配置，并在界面上显示出来
	 * @return true标识正确创建
	 */
	boolean creat();
	/**
	 * 在配置文件中删除一个实体配置，并在界面上删除
	 * @return true标识正确删除
	 */
	boolean remove();
	/**
	 * 在配置文件中修改一个实体配置，并在界面上显示出来
	 * @return true标识正确更改
	 */
	boolean update();
	/**
	 * 在配置文件中根据id确定一个实体配置
	 * @param bc 具体的实体的id
	 * @return 找到的实体，否则返回 null
	 */
	BaseClass load(String id);
	/**
	 * 在配置文件中根据name查找实体配置
	 * @param bc 具体的实体的name
	 * @return 找到的实体序列，否则返回空序列，而不是null
	 */
	List<BaseClass> find(String name);
}
