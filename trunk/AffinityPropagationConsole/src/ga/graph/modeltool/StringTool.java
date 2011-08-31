package ga.graph.modeltool;

public class StringTool {
	
	
	
	
	/**
	 * 输出给定的字符串到控制台
	 * @param s
	 */
	public static void outputString(String s){
		System.out.println(s);
	}
	
	/**
	 * 获得s字符串在begin和end之间的子串转换成数字
	 * @param index1
	 * @param index2
	 * @param s
	 * @return
	 */
	public static int getnumber(int begin,int end,String s){
		return Integer.parseInt(s.substring(begin,end).trim());
	}
	
	/**
	 * 将s转换成int，如果无法转换，返回-10000；
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
