package com.huiguanjia.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {
        @SuppressWarnings("unused")
		private static final String DEFAULT_CHARSET_NAME = "UTF-8";

        public static <T> String serialize(T object) {
        	SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect; 
//            byte[] bytes = JSON.toJSONBytes(object,feature);
            String str = JSON.toJSONString(object,feature);
            return str;
        }

        public static <T> T deserialize(String string, Class<T> clz) {
            return JSON.parseObject(string, clz);
        }
        
        public static <T> List<T> deserializeToList(String string,Class<T> clz)
        {
        	return JSON.parseArray(string, clz);
        }
}