package com.huiguanjia.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.opensymphony.xwork2.ActionSupport;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.PutPolicy;

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
}
