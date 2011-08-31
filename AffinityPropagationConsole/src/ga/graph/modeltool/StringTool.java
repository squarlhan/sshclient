package ga.graph.modeltool;

public class StringTool {
	
	
	
	
	/**
	 * ����������ַ���������̨
	 * @param s
	 */
	public static void outputString(String s){
		System.out.println(s);
	}
	
	/**
	 * ���s�ַ�����begin��end֮����Ӵ�ת��������
	 * @param index1
	 * @param index2
	 * @param s
	 * @return
	 */
	public static int getnumber(int begin,int end,String s){
		return Integer.parseInt(s.substring(begin,end).trim());
	}
	
	/**
	 * ��sת����int������޷�ת��������-10000��
	 * @param s
	 * @return
	 */
	public static int parseToInteger(String s){
		int result;
		try{
			result=Integer.parseInt(s.trim());
		}catch(Exception e){
			result=-10000;
		}
		return result;
	}
	
	public static String getNameFromPath(String filePathName){
		int index1=filePathName.length()-2;
		int index2=index1+1;
		while(!filePathName.substring(index1, index2).equals("/")){
			index1--;
			index2--;
		}
		return filePathName.substring(index1+1,filePathName.length()-4);
		
		
	}
	
}
