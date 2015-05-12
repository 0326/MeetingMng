package com.huiguanjia.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.EncoderException;
import org.json.JSONException;

import com.huiguanjia.test.MeetingTest;
import com.opensymphony.xwork2.ActionSupport;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.URLUtils;

@SuppressWarnings("serial")
public class QiniuyunUtil extends ActionSupport{
	
	private Map<String,Object> jsonData;
	
	public Map<String,Object> getJsonData(){return jsonData; }
	
	public String upToken() throws AuthException, JSONException{
        Config.ACCESS_KEY = "1yki9A2Zm_UyYCD5Ne7vV8AUbnGWlPYvYC2sxv74";
        Config.SECRET_KEY = "-lZIMqcU58ymr9CgXGNhGkM2y19BrunyHBKHwQxD";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        String bucketName = "huiguanjia";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);

        jsonData = new HashMap<String,Object>();
        jsonData.put("uptoken", uptoken);
        return SUCCESS;
    }
	
	public void upTokenFile(String localFile) throws AuthException, JSONException{
        Config.ACCESS_KEY = "1yki9A2Zm_UyYCD5Ne7vV8AUbnGWlPYvYC2sxv74";
        Config.SECRET_KEY = "-lZIMqcU58ymr9CgXGNhGkM2y19BrunyHBKHwQxD";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        String bucketName = "huiguanjia";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        PutExtra extra = new PutExtra();
        String key = localFile;
        String localFile1 ="d:"+File.separator+ localFile;
//        String localFile1 = "d:\\qrcode.gif";
        PutRet ret = IoApi.putFile(uptoken, key, localFile1, extra);
//        PutRet ret = IoApi.Put(uptoken, key, reader, extra);
	}
	
//	public static String downloadFile(String domain, String key) throws AuthException, JSONException, EncoderException{
//        Config.ACCESS_KEY = "1yki9A2Zm_UyYCD5Ne7vV8AUbnGWlPYvYC2sxv74";
//        Config.SECRET_KEY = "-lZIMqcU58ymr9CgXGNhGkM2y19BrunyHBKHwQxD";
//        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
//        
//        String domain1 = "huiguanjia.qiniudn.com";
//        String key1 = "gif";
//        String baseUrl = URLUtils.makeBaseUrl(domain1,key1);
//        GetPolicy getPolicy = new GetPolicy();
//        String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
//        System.out.println("success");
//        System.out.println(downloadUrl);
//        return downloadUrl;
//        
//	}

	public String downloadFile(String key) throws AuthException, JSONException, EncoderException{
        String key1 = "FnQqf7uyT6Arz9XC0ai-efHruCoF";
        String downloadUrl = "http://7u2j45.com1.z0.glb.clouddn.com/" + key1;
        System.out.println(downloadUrl);
        return downloadUrl;  
	}
	
	

//	public static void main(String[] args) throws AuthException, JSONException, EncoderException {
//		QiniuyunUtil.downloadFile(null);
//		
//	}
}
        

