/**
 * 
 */
package cn.edu.jlu.ccst.sshclient.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

/**
 * @author Woden
 * 针对ssh连接的操作接口
 */
public class SSHCommand{
	
	private SSHCommand() {
	}
	/**
	 * 从服务器获取文件
	 * @param host ip
	 * @param username
	 * @param password
	 * @param remoteFile
	 * @param localDir
	 */

	public static void scpGet(String host, String username, String password, String remoteFile, String localDir) throws IOException {

		Connection conn = getOpenedConnection(host, username, password);
		SCPClient client = new SCPClient(conn);
		client.get(remoteFile, localDir);
		conn.close();

	}

	/**
	 * 向服务器发送文件
	 * @param host ip
	 * @param username
	 * @param password
	 * @param localFile
	 * @param remoteDir
	 */

	public static void scpPut(String host, String username, String password, String localFile, String remoteDir) throws IOException {

		Connection conn = getOpenedConnection(host, username, password);
		SCPClient client = new SCPClient(conn);
		client.put(localFile, remoteDir);
		conn.close();

	}

	/**
	 * 在服务器上运行命令
	 * @param host
	 * @param username
	 * @param password
	 * @param cmd
	 * @return exit status
	 */

	public static int runSSH(String host, String username, String password, String cmd) throws IOException {

		Connection conn = getOpenedConnection(host, username, password);
		Session sess = conn.openSession();
		sess.execCommand(cmd);		
		String out;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sess.getStdout()));     
		while((out=bufferedReader.readLine())!=null)     
		System.out.println(out);
		sess.close();
		conn.close();
		return sess.getExitStatus().intValue();

	}

	/**
	 * 得到一个打开的连接
	 * @param host
	 * @param username
	 * @param password
	 * @return
	 */

	private static Connection getOpenedConnection(String host, String username, String password) throws IOException {

		Connection conn = new Connection(host);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(username, password);
		if (isAuthenticated == false)
			throw new IOException("Authentication failed.");
		return conn;

	}

	/**
	 * 在本地机器上运行命令
	 * @param cmd
	 * @return exit status 
	 */


	public static int runLocal(String cmd) throws IOException {

		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(cmd);		
		String out;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));     
		while((out=bufferedReader.readLine())!=null)     
		System.out.println(out);       
		return p.exitValue();

	}
}
