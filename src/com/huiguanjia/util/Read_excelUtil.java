package com.huiguanjia.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.huiguanjia.util.QiniuyunUtil;


//import jxl.*; 
public class Read_excelUtil{
//    public static List read_excel(String fileName) {
//        int i=3;
//        int j=0;
//        List<String> list = new ArrayList<String>();
//        List<List> listAll = new ArrayList<List>();
//
//        Sheet sheet;
//        Workbook book;
////        Cell cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9;
//        String cellVal1,cellVal2,cellVal3,cellVal4,cellVal5,cellVal6,cellVal7,cellVal8,cellVal9;
//        try { 
//    
//            //t.xls为要读取的excel文件名
//        	File file = new File("d:"+File.separator+fileName);
////          book= Workbook.getWorkbook(new File("stuff_template.xls"));                    
//            book= Workbook.getWorkbook(file);
//            //获得第一个工作表对象(ecxel中sheet的编号从0开始,0,1,2,3,....)
//            sheet=book.getSheet(0); 
////            获取左上角的单元格            
////            System.out.println("标题："+sheet.getCell(0,0).getContents()); 
//            
//            
//            while(true)
//            {
//                //获取每一行的单元格 
//            	cellVal1=sheet.getCell(0,i).getContents();//（列，行）
//            	cellVal2=sheet.getCell(1,i).getContents();
//            	cellVal3=sheet.getCell(2,i).getContents();
//            	cellVal4=sheet.getCell(3,i).getContents();
//            	cellVal5=sheet.getCell(4,i).getContents();
//            	cellVal6=sheet.getCell(5,i).getContents();
//            	cellVal7=sheet.getCell(6,i).getContents();
//            	cellVal8=sheet.getCell(7,i).getContents();
//            	cellVal9=sheet.getCell(8,i).getContents();
//             
//                if("".equals(cellVal1)==true || "".equals(cellVal2)==true || "".equals(cellVal3)==true 
//                		|| "".equals(cellVal4)==true || "".equals(cellVal5)==true)    
//                	//如果读取的数据为空
//                	break;
//                
//                list.add(cellVal1);
//                list.add(cellVal2);
//                list.add(cellVal3);
//                list.add(cellVal4);
//                list.add(cellVal5);
//                list.add(cellVal6);
//                list.add(cellVal7);
//                list.add(cellVal8);
//                list.add(cellVal9);
//                listAll.add(list);
////                System.out.println(cellVal1,cellVal2,cellVal3,cellVal4,cellVal5,cellVal6,cellVal7,cellVal8,cellVal9);
////                System.out.println(cellVal2);
////                List a = new List(cell1.getContents(),cell2.getContents(),cell3.getContents()
////                		,cell4.getContents(),cell5.getContents(),cell6.getContents()
////                		,cell7.getContents(),cell8.getContents(),cell9.getContents()); 
//                i++;
//                j++;                
//            }
//            System.out.println(listAll.get(0).get(0));
//              
//            System.out.println(listAll.size());
//            book.close(); 
//        }
//        catch(Exception e)  { }
//        return listAll;
//
//    }
//    
    
//    
//    public static void main(String[] args) throws IOException {  
//    	Read_excelUtil.read_excel("stuff2.xls");
//    }  
}