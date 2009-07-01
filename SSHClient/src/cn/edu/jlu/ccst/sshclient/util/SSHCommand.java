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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Woden
 * ���ssh���ӵĲ����ӿ�
 */
public class SSHCommand{
	
	private SSHCommand() {
	}
	/**
	 * �ӷ�������ȡ�ļ�
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
	 * ������������ļ�
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
	 * �ڷ���������������
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
	 * �õ�һ���򿪵�����
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
	 * �ڱ��ػ�������������
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

        public static void main(String[] args) {
        try {
//            SSHCommand.scpPut("10.60.58.194", "wuchunguo", "wucg",
//                    "E:/SSH/Code/NBSSHClient/src/cn/edu/jlu/ccst/sshclient/util/test/Test5m.java",
//                    "squarlhan/");
            //SSHCommand.scpGet("10.60.58.194", "wuchunguo", "wucg", "test.png", "F:/1/");
            //SSHCommand.runSSH("10.60.58.194", "wuchunguo", "wucg", "ls");
            SSHCommand.runSSH("192.168.1.90", "test", "123456", "ls");
        } catch (IOException ex) {
            Logger.getLogger(SSHCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
