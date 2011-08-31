package ga.graph.modeltool;


public class BMConstraint {
	/**
	 * 元数
	 */
	public int ary;
	/**
	 * 这条约束中包含的变量
	 */
	public int[] varIds;
	/**
	 * 这条约束的relation
	 */
	public int relId ;
	/**
	 * id
	 */
	public int id;
	
	public BMConstraint(String s) {
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
				this.ary=number;
				varIds=new int[ary];
				charid++;			
				continue;
			}
			if(charid>1&&charid<(2+ary)){				
				varIds[charid-2]=number;
				index1=index2;	
				charid++;
				continue;
			}
			if(charid==(2+ary)){				
				relId=number;
				index1=index2;	
				charid++;
				continue;
			}
		}	
		
	
	}
}
