package com.base.util;

import java.io.*;
import jxl.*;
import jxl.write.*;
import jxl.format.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel.Student;

import java.awt.Color;
import jxl.write.Number;
import jxl.write.Boolean;






public class ExcelTest {
	

	 public static  List list=new ArrayList();   
	
	//�������X��Y�᣻ �Զ����������
//	public static void writeExcel(OutputStream os,int x,int y,List content) throws Exception {
//		 RegUser user=new RegUser();
//		 String str="";
//		jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
//		jxl.write.WritableSheet ws = wwb.createSheet("Sheet", 0);
//		
////		jxl.write.Label labelC = new jxl.write.Label(x, y,content);
////		labelC = new jxl.write.Label(4, 4,content);
////		
//		if (ws != null) {
//
//			// ���濪ʼ��ӵ�Ԫ��
//			for (int i = 0; i < x; i++) {
//				
//				
//				for (int j = 0; j < y; j++) {
//					// ������Ҫע����ǣ���Excel�У���һ��������ʾ�У��ڶ�����ʾ��
//					user=(RegUser)content.get(i);
//					
//					if(j==0){
//						str=user.getUserName();
//					}else if(j==1){
//						str=user.getPassword();
//					}else if(j==2){
//						str=user.getUserContent();
//					}else if(j==3){
//						str=user.getFlag();
//					}else if(j==4){
//						str=user.getUserTime();
//					}else if(j==5){
//						str=String.valueOf(user.getId());
//					}
//					
//					
//					jxl.write.Label labelC = new Label(j, i,str);
//					try {
//						// �����ɵĵ�Ԫ����ӵ���������
//						ws.addCell(labelC);
//					} catch (RowsExceededException e) {
//						e.printStackTrace();
//					} catch (WriteException e) {
//						e.printStackTrace();
//					}
//
//				}
//			}
//
//		}
//
//
//
//		/*
//		 * �Զ��嵥Ԫ������ʽ��
//		 */
//		// jxl.write.WritableFont wfc = new
//		// jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
//		// false,UnderlineStyle.NO_UNDERLINE);
//		// jxl.write.WritableCellFormat wcfFC = new
//		// jxl.write.WritableCellFormat(wfc);
//		//��Ԫ��Ӻ�
//		//wcfFC.setBackground(jxl.format.Colour.RED);
//		//labelC = new jxl.write.Label(6, 0, "�й����� ", wcfFC);
//		
//		
//		
//		//ws.addCell(labelC);
//		// д��Exel������
//		wwb.write();
//		// �ر�Excel����������
//		wwb.close();
//	
//	}
	

	
	
	
	
	
	//new file ()
	
	/**
     * ��ȡExcel
     *
     * @param filePath
     */
    public static void readExcel(String filePath)
    {
    	 String data2="";
    	 //DateTime tem=new DateTime("");
   		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            InputStream is = new FileInputStream(filePath);
            Workbook rwb = Workbook.getWorkbook(is);
            //Sheet st = rwb.getSheet("0")���������ַ�����ȡsheet��,1Ϊ���֣���Ϊ�±꣬��0��ʼ
            Sheet st = rwb.getSheet("Sheet1");
            int rs=st.getColumns();
            int rows=st.getRows();
            System.out.println("����===>"+rs+"������"+rows);
            
           
            	for(int k=0;k<rows;k++){//��
            		 for(int i=0 ;i<rs;i++){//��
            			 
                   Cell c00 = st.getCell(i,k);
            //ͨ�õĻ�ȡcellֵ�ķ�ʽ,�����ַ���
            String strc00 = c00.getContents();
            //���cell��������ֵ�ķ�ʽ
            if(c00.getType() == CellType.LABEL)
            {
                LabelCell labelc00 = (LabelCell)c00;
                strc00 = labelc00.getString();
            }
            //excel ����Ϊʱ�����ʹ���;
            if(c00.getType()==CellType.DATE){
            	DateCell dc=(DateCell)c00;  
            	strc00 = sdf.format(dc.getDate());
            	
            }
            //excel ����Ϊ��ֵ���ʹ���;
            /*
            if(c00.getType()==CellType.NUMBER|| c00.getType()==CellType.NUMBER_FORMULA){
            	NumberCell nc=(NumberCell)c00; 
            	strc00=""+nc.getValue(); 
            }*/
            
            //���
            System.out.println(">"+strc00);
            
            list.add(strc00);
            
       
   		 //�У���
//   		 data2=String.valueOf(st.getCell(1,k).getContents());
//   		 data2=data2.replace("/", "-");
//           java.util.Date dt=sdf.parse(data2);	
//           System.out.println(sdf.format(dt));   
//            	           	
            		 }
  		 System.out.println(data2+"======"+list.get(k)+"=========");	 
       }
            
            
            //�ر�
            rwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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

            //��Ӵ�������Formatting����
            WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            Label labelcf = new Label(1,0,"this is a label test",wcf);
            ws.addCell(labelcf);

            //��Ӵ���������ɫ��Formatting����
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

            //��Ӵ���formatting��DateFormat����
            DateFormat df = new DateFormat("dd MM yyyy hh:mm:ss");
            WritableCellFormat wcfDF = new WritableCellFormat(df);
            DateTime labelDTF = new DateTime(1,3,new java.util.Date(),wcfDF);
            ws.addCell(labelDTF);


            //���ͼƬ����,jxlֻ֧��png��ʽͼƬ
            //File image = new File("d://2.png");
           // WritableImage wimage = new WritableImage(0,1,2,2,image);
           // ws.addImage(wimage);
            //д�빤����
            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
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


    //����
    public static void main(String[] args)
    {
        try
        {
            //��Excel
        	//ExcelTest.readExcel("C:/Users/Administrator/Desktop/test23.xls");
            //���Excel
        	/*File fileWrite = new File("C:/Users/Administrator/Desktop/test23.xls");
            fileWrite.createNewFile();
            OutputStream os = new FileOutputStream(fileWrite);
            ExcelTest.writeExcel(os);*/
            //�޸�Excel
          //  excelTest.modifyExcel(new File(""),new File(""));
        	
        	/*String filePath = "C:/Users/Administrator/Desktop/test23.xls";
        	InputStream is = new FileInputStream(filePath);
            Workbook rwb = Workbook.getWorkbook(is);*/
        	String filePath = "C:/Users/Administrator/Desktop/����һ.xlsx";
        	//String filePath = "C:/Users/Administrator/Desktop/test23.xls";
        	String[] columnArray = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        	if("xlsx".equals(filePath.split("\\.")[filePath.split("\\.").length-1])){
        		//2010
        		
        		DecimalFormat df = new DecimalFormat("0");  
                InputStream is = new FileInputStream(filePath);
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
                Student student = null;
                List<Student> list = new ArrayList<Student>();
                // Read the Sheet
                for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
                    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                    if (xssfSheet == null) {
                        continue;
                    }
                    //System.out.println(xssfSheet.getSheetName()+" ���� "+xssfSheet.getPhysicalNumberOfRows() +"  ����"+xssfSheet.getRow(0).getPhysicalNumberOfCells());
                    //System.out.println(xssfSheet.getSheetName()+" ���� "+xssfSheet.getPhysicalNumberOfRows() );
                    if("֧����".equals(xssfSheet.getSheetName())){
                    	int columnNumCount = xssfSheet.getRow(0).getPhysicalNumberOfCells();
                    	int lastRowNum = xssfSheet.getLastRowNum();
                    	XSSFRow xssfRow = xssfSheet.getRow(0);
                    	for(int i=0;i<columnNumCount;i++){
                    		
                    		System.out.println("��1��---��"+xssfRow.getCell(i)+"   ����---��"+columnArray[i]);
                    	}
                    }
                    // Read the Row
                   /* for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                        XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                        if (xssfRow != null) {
                            student = new Student();
                            XSSFCell no = xssfRow.getCell(0);
                            XSSFCell name = xssfRow.getCell(1);
                            XSSFCell age = xssfRow.getCell(2);
                            XSSFCell score = xssfRow.getCell(3);
                            System.out.println(no+" "+name+" "+age+" "+score); 
                        }
                    }*/
                }
        	}else{
        		//2007
        		DecimalFormat df = new DecimalFormat("0");  
        		InputStream is = new FileInputStream(filePath);
        		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        		Student student = null;
        		List<Student> list = new ArrayList<Student>();
        		// Read the Sheet
        		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
        			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
        			if (hssfSheet == null) {
        				continue;
        			}else{
        				 if("֧����".equals(hssfSheet.getSheetName())){
                         	int columnNumCount = hssfSheet.getRow(0).getPhysicalNumberOfCells();
                         	int lastRowNum = hssfSheet.getLastRowNum();
                         	HSSFRow hssfRow = hssfSheet.getRow(0);
                         	for(int i=0;i<columnNumCount;i++){
                         		
                         		System.out.println("����---��"+hssfRow.getCell(i)+"   ����---��"+columnArray[i]);
                         	}
                         }
        				/*for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
        					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
        					if (hssfRow != null) {
        						student = new Student();
        						HSSFCell no = hssfRow.getCell(0);
        						HSSFCell name = hssfRow.getCell(1);
        						HSSFCell age = hssfRow.getCell(2);
        						HSSFCell score = hssfRow.getCell(3);
        						System.out.println(no+" "+name+" "+age+" "+score); 
        					}
        				}*/
        			}
        			
        		}
        	}
        	}
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }

	

}
