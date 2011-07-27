package ga;

public class TestAny {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a[][] = {{1,2,3,4}, {123}};
	    for (int i = 0; i <= a[0].length-1; i+=2) {      
	    	System.out.println("a: "+a[0][i]);
	    }
		System.out.println("a.lenth: "+a.length);
		System.out.println("a[1].lenth: "+a[1].length);

	}

}
