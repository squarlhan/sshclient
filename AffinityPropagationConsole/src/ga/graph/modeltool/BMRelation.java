package ga.graph.modeltool;


public class BMRelation {
	/**
	 * Ԫ��
	 */
	public int ary;
	/**
	 * type=0��ӦΥ��ֵ�ԣ�1��Ӧ֧��ֵ��
	 */
	public int type;
	/**
	 * ������ϵ���漰��domains
	 */
	public int[] domIds;
	/**
	 * ֵ�Ը���
	 */
	public int tupleCount;
	/**
	 * ֵ������
	 */
	public int[][] tuples;
	/**
	 * id
	 */
	public int id;

	public BMRelation(String s) {
		int index1 = 0;
		int index2 = 0;
		int charid = 1;// ��ʾ��ǰ�Ѿ��������Ч�ַ���
		int tupleIndex = 0;
		while (index1 < s.length()) {
			index2 = index1 + 1;
			int index3 = index2 - 1;
			while (s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index2++;
				index3++;
			}
			while (!s.substring(index3, index2).equals(" ")
					&& index2 < s.length()) {
				index2++;
				index3++;
			}
			int number = StringTool.getnumber(index1, index2, s);
			if (charid == 1) {//id
				index1 = index2;
				charid++;
				id = number;
				continue;
			}
			if (charid == 2) {//type
				type = number;
				index1 = index2;
				charid++;
				continue;
			}
			if (charid == 3) {//ary
				index1 = index2;
				charid++;
				ary = number;
				domIds = new int[ary];
				continue;
			}
			if (charid > 3 && charid < (4 + ary)) {
				domIds[charid - 4] = number;
				index1 = index2;
				charid++;
				continue;
			}
			if (charid == (4 + ary)) {
				tupleCount = number;
				index1 = index2;
				charid++;
				tuples = new int[ary][tupleCount];
				continue;
			}
			if (charid > (4 + ary) && charid < (5 + ary + ary * tupleCount)) {
				int loc = charid - 5 - ary;
				loc = loc % ary;
				index1 = index2;
				charid++;
				tuples[loc][tupleIndex] = number;
				if (loc == ary - 1)
					tupleIndex++;
				continue;
			}

		}

	}
}
