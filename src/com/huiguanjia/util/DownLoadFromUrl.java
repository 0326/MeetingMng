package com.huiguanjia.util;

import java.io.*;
import java.net.*;
import java.util.*;

public class DownLoadFromUrl {
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public void downLoadFromUrl(String urlStr,String fileName) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = new byte[4 * 1024];
  
        //文件保存位置  
        File saveDir = new File("d:");  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        System.out.println("info:"+url+" download success");   
  
    }  
  

//    public static void downLoadFromUrlTest() throws IOException{  
//        URL url = new URL("http://7u2j45.com1.z0.glb.clouddn.com/stuff_template.xls");    
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
//                //设置超时间为3秒  
//        conn.setConnectTimeout(3*1000);  
//        //防止屏蔽程序抓取而返回403错误  
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
//  
//        //得到输入流  
//        InputStream inputStream = conn.getInputStream();    
//        //获取自己数组  
//        byte[] getData = readInputStream(inputStream);  
//  
//        //文件保存位置  
//        File saveDir = new File("d:");  
//        if(!saveDir.exists()){  
//            saveDir.mkdir();  
//        }  
//        File file = new File(saveDir+File.separator+"stuff2.xls");      
//        FileOutputStream fos = new FileOutputStream(file);       
//        fos.write(getData);   
//        if(fos!=null){  
//            fos.close();    
//        }  
//        if(inputStream!=null){  
//            inputStream.close();  
//        }  
//  
//  
//        System.out.println("info:"+url+" download success");   
//  
//    }  
    
    
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    
    /** 
     * @param args 
     * @throws IOException 
     */  
    public static void main(String[] args) throws IOException {  
  
//    	DownLoadFromUrl.downLoadFromUrl();  
    }  
}
