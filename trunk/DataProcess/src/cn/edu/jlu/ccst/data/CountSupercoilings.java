package cn.edu.jlu.ccst.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Boolean;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CountSupercoilings {

	
	private static int count(String addr){
		File file = new File(addr);
		int result = 0;
		try {
			InputStreamReader insr = new InputStreamReader(new FileInputStream(file), "gb2312");
			BufferedReader br = new BufferedReader(insr);
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.trim().length() >= 1) 
				{				 
					result+=(Integer.parseInt(line));					
				}
			}
			br.close();
			insr.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Superciolings:"+result);
		return result;	
	}
	
	
	/**
	    * ��ȡExcel
	    *
	    * @param filePath
	    */
	   public static List<String> readExcel(String filePath)
	   {
		   List<String> results = new ArrayList<String>();
	       try
	       {
	           InputStream is = new FileInputStream(filePath);
	           Workbook rwb = Workbook.getWorkbook(is);
	           //Sheet st = rwb.getSheet("0")���������ַ�����ȡsheet��,1Ϊ���֣�2Ϊ�±꣬��0��ʼ
	           Sheet st = rwb.getSheet(0);
	           for(int a = 0; a<= st.getRows()-1; a++){
	        	   Cell c = st.getCell(0,a);
	        	   String strc = c.getContents();
	        	   if(c.getType() == CellType.LABEL) {
	                   LabelCell labelc = (LabelCell)c;
	                   strc = labelc.getString();
	               }
	        	   if(strc==""||strc==null){
	        		   break;
	        	   }
	        	   results.add(strc);
	           }
	           //Cell c00 = st.getCell(0,0);
	           //ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
	           //String strc00 = c00.getContents();
	           //���cell��������ֵ�ķ�ʽ
	           
	           //���
	           System.out.println(st.getRows());
	           //�ر�
	           rwb.close();
	       }
	       catch(Exception e)
	       {
	           e.printStackTrace();
	       }
	       return results;
	   }

	   /**
	    * ���Excel
	    *
	    * @param os
	    */
	   public static void writeExcel(OutputStream os)
	   {
	       try
	       {
	           /**
	            * ֻ��ͨ��API�ṩ�Ĺ�������������Workbook��������ʹ��WritableWorkbook�Ĺ��캯����
	            * ��Ϊ��WritableWorkbook�Ĺ��캯��Ϊprotected����
	            * method(1)ֱ�Ӵ�Ŀ���ļ��ж�ȡWritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile));
	            * method(2)����ʵ����ʾ ��WritableWorkbookֱ��д�뵽�����

	            */
	           WritableWorkbook wwb = Workbook.createWorkbook(os);
	           //����Excel������ ָ�����ƺ�λ��
	           WritableSheet ws = wwb.createSheet("Test Sheet 1",0);

	           //**************�����������������*****************

	           //1.���Label����
	           Label label = new Label(0,0,"this is a label test");
	           ws.addCell(label);

	           //��Ӵ�������formatting����
	           WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
	           WritableCellFormat wcf = new WritableCellFormat(wf);
	           Label labelcf = new Label(1,0,"this is a label test",wcf);
	           ws.addCell(labelcf);

	           //��Ӵ���������ɫ��formatting����
	           WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,
	                   UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
	           WritableCellFormat wcfFC = new WritableCellFormat(wfc);
	           Label labelCF = new Label(1,0,"This is a Label Cell",wcfFC);
	           ws.addCell(labelCF);

	           //2.���Number����
	           Number labelN = new Number(0,1,3.1415926);
	           ws.addCell(labelN);

	           //��Ӵ���formatting��Number����
	           NumberFormat nf = new NumberFormat("#.##");
	           WritableCellFormat wcfN = new WritableCellFormat(nf);
	           Number labelNF = new jxl.write.Number(1,1,3.1415926,wcfN);
	           ws.addCell(labelNF);

	           //3.���Boolean����
	           Boolean labelB = new jxl.write.Boolean(0,2,false);
	           ws.addCell(labelB);

	           //4.���DateTime����
	           jxl.write.DateTime labelDT = new jxl.write.DateTime(0,3,new java.util.Date());
	           ws.addCell(labelDT);

	           //��Ӵ���formatting��Dateformat����
	           DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
	           WritableCellFormat wcfDF = new WritableCellFormat(df);
	           DateTime labelDTF = new DateTime(1,3,new java.util.Date(),wcfDF);
	           ws.addCell(labelDTF);


	           //���ͼƬ����,jxlֻ֧��png��ʽͼƬ
	           File image = new File("f:\\2.png");
	           WritableImage wimage = new WritableImage(0,1,2,2,image);
	           ws.addImage(wimage);
	           //д�빤����
	           wwb.write();
	           wwb.close();
	       }
	       catch(Exception e)
	       {
	           e.printStackTrace();
	       }
	   }
	   
	   public static void writetxt(List<String> mydata,String refile)
	   {
		   try {
				File outer = new File(refile);
				if (outer.exists()) {
					outer.delete();
					if (outer.createNewFile()) {
						System.out.println("result file create success!");
					} else {
						System.out.println("result file create failed!");
					}
				} else {
					if (outer.createNewFile()) {
						System.out.println("result file create success!");
					} else {
						System.out.println("result file create failed!");
					}

				}

				BufferedWriter output = new BufferedWriter(new FileWriter(outer));
				for(int i=0;i<=mydata.size()-1;i++){
					output.write(mydata.get(i));
					output.write("\n");
				}
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
	   
	   /**
	    * ������,�����޸�,����file1Ϊ��copy����file2Ϊ�޸ĺ󴴽��Ķ���
	    * ����Ԫ��ԭ�еĸ�ʽ�������ǲ���ȥ���ģ����ǻ��ǿ��Խ��µĵ�Ԫ�����μ���ȥ��
	    * ��ʹ��Ԫ��������Բ�ͬ����ʽ����
	    * @param file1
	    * @param file2
	    */
	   public static void modifyExcel(File file1,File file2)
	   {
	       try
	       {
	           Workbook rwb = Workbook.getWorkbook(file1);
	           WritableWorkbook wwb = Workbook.createWorkbook(file2,rwb);//copy
	           WritableSheet ws = wwb.getSheet(0);
	           WritableCell wc = ws.getWritableCell(0,0);
	           //�жϵ�Ԫ�������,������Ӧ��ת��
	           if(wc.getType() == CellType.LABEL)
	           {
	               Label label = (Label)wc;
	               label.setString("The value has been modified");
	           }
	           wwb.write();
	           wwb.close();
	           rwb.close();
	       }
	       catch(Exception e)
	       {
	           e.printStackTrace();
	       }
	   } 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "";
		String fileurl = "";
		String rsurl = "";
		for(int i = 1; i<=8;i++){
			filename = String.valueOf(i);
			fileurl = "E:\\supercoli\\exe1\\rs\\"+filename+".xls";
			rsurl = "E:\\supercoli\\exe1\\rs\\"+filename+".tab";
			CountSupercoilings.writetxt(CountSupercoilings.readExcel(fileurl),rsurl);
		}
		


	}

}
