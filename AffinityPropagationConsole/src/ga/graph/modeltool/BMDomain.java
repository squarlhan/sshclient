package ga.graph.modeltool;


public class BMDomain {
	/**
	 * ֵ���е�ֵ
	 */
	public int[] values;
	
	/**
	 * ֵ���С
	 */
	public int size;
	
	/**
	 * id
	 */
	public int id;
	
	public BMDomain(String s){
		int index1=0;
		int index2=0;
		int charid=1;//��ʾ��ǰ�Ѿ��������Ч�ַ���
		int valueIndex=0;
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
				size=number;
				index1=index2;
				values=new int[size];
				charid++;
				continue;
			}
			values[valueIndex]=number;
			valueIndex++;
			index1=index2;
		}		
	
	}

}
