package com;
import java.io.InputStream;  

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
import org.apache.poi.openxml4j.opc.OPCPackage;  
import org.apache.poi.xssf.eventusermodel.XSSFReader;  
import org.apache.poi.xssf.model.SharedStringsTable;  
import org.apache.poi.xssf.usermodel.XSSFRichTextString;  
import org.xml.sax.Attributes;  
import org.xml.sax.InputSource;  
import org.xml.sax.SAXException;  
import org.xml.sax.XMLReader;  
import org.xml.sax.helpers.DefaultHandler;  
import org.xml.sax.helpers.XMLReaderFactory;  
  
public class ExcelUtil extends DefaultHandler {  
      
    private SharedStringsTable sst;  
    private String lastContents;  
    private boolean nextIsString;  
  
    private int sheetIndex = -1;  
    private List<String> rowlist = new ArrayList<String>();  
    private int curRow = 0;  
    private int curCol = 0;  
      
    
      
    /** 
     * ��ȡ��һ������������ڷ��� 
     * @param path 
     */  
    public void readOneSheet(String path) throws Exception {  
        OPCPackage pkg = OPCPackage.open(path);       
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
              
        XMLReader parser = fetchSheetParser(sst);  
              
        InputStream sheet = r.getSheet("rId1");  
  
        InputSource sheetSource = new InputSource(sheet);  
        parser.parse(sheetSource);  
              
        sheet.close();        
    }  
      
      
    /** 
     * ��ȡ���й���������ڷ��� 
     * @param path 
     * @throws Exception 
     */  
    public void process(String path) throws Exception {  
        OPCPackage pkg = OPCPackage.open(path);  
        XSSFReader r = new XSSFReader(pkg);  
        SharedStringsTable sst = r.getSharedStringsTable();  
  
        XMLReader parser = fetchSheetParser(sst);  
  
        Iterator<InputStream> sheets = r.getSheetsData();  
        while (sheets.hasNext()) {  
            curRow = 0;  
            sheetIndex++;  
            InputStream sheet = sheets.next();  
            InputSource sheetSource = new InputSource(sheet);  
            parser.parse(sheetSource);  
            sheet.close();  
        }  
    }  
      
    /** 
     * �÷����Զ������ã�ÿ��һ�е���һ�Σ��ڷ�����д�Լ���ҵ���߼����� 
     * @param sheetIndex ��������� 
     * @param curRow �����ڼ��� 
     * @param rowList ��ǰ�����е����ݼ��� 
     */  
    public void optRow(int sheetIndex, int curRow, List<String> rowList) {  
        String temp = "";  
        for(String str : rowList) {  
            temp += str + "_";  
        }  
        System.out.println(temp);  
    }  
      
      
    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {  
        XMLReader parser = XMLReaderFactory  
                .createXMLReader("org.apache.xerces.parsers.SAXParser");  
        this.sst = sst;  
        parser.setContentHandler(this);  
        return parser;  
    }  
      
    public void startElement(String uri, String localName, String name,  
            Attributes attributes) throws SAXException {  
        // c => ��Ԫ��  
        if (name.equals("c")) {  
            // �����һ��Ԫ���� SST ����������nextIsString���Ϊtrue  
            String cellType = attributes.getValue("t");  
            if (cellType != null && cellType.equals("s")) {  
                nextIsString = true;  
            } else {  
                nextIsString = false;  
            }  
        }  
        // �ÿ�  
        lastContents = "";  
    }  
      
      
    public void endElement(String uri, String localName, String name)  
            throws SAXException {  
        // ����SST������ֵ�ĵ���Ԫ�������Ҫ�洢���ַ���  
        // ��ʱcharacters()�������ܻᱻ���ö��  
        if (nextIsString) {  
            try {  
                int idx = Integer.parseInt(lastContents);  
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))  
                        .toString();  
            } catch (Exception e) {  
  
            }  
        }  
  
        // v => ��Ԫ���ֵ�������Ԫ�����ַ�����v��ǩ��ֵΪ���ַ�����SST�е�����  
        // ����Ԫ�����ݼ���rowlist�У�����֮ǰ��ȥ���ַ���ǰ��Ŀհ׷�  
        if (name.equals("v")) {  
            String value = lastContents.trim();  
            value = value.equals("") ? " " : value;  
            rowlist.add(curCol, value);  
            curCol++;  
        } else {  
            // �����ǩ����Ϊ row ����˵���ѵ���β������ optRows() ����  
            if (name.equals("row")) {  
                optRow(sheetIndex, curRow, rowlist);  
                rowlist.clear();  
                curRow++;  
                curCol = 0;  
            }  
        }  
    }  
  
    public void characters(char[] ch, int start, int length)  
            throws SAXException {  
        // �õ���Ԫ�����ݵ�ֵ  
        lastContents += new String(ch, start, length);  
    }  
  
    public static void main(String[] args) throws Exception {
		new ExcelUtil().process("C:/Users/Administrator/Desktop/����һ.xlsx");
	}
}  