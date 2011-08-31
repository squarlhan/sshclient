package ga.graph.modeltool;


public class BMVariable {
	public int id;
	/**
	 * 值域
	 */
	public int domId;
	
	public BMVariable(String s) {
		int index1=0;
		int index2=0;
		int charid=1;//表示当前已经读入的有效字符数
		while(index1<s.length()){
			index2=index1+1;
			int index3=index2-1;
			while(s.substring(index3, index2).equals(" ")&&index2<s.length()){
				index2++;
				index3++;
			}
			while(!s.substring(index3, index2).equals(" ")&&index2<s.length()){
				index2++;
				index3++;
			}			
			int number=StringTool.getnumber(index1,index2,s);
			if(charid==1){
				index1=index2;
				charid++;
				id=number;
				continue;
			}
			if(charid==2){
				domId=number;
				index1=index2;			
				continue;
			}
		}	
		
	
	}
	
}
