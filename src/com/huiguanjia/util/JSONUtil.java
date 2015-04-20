package com.huiguanjia.util;

import com.alibaba.fastjson.JSON;


public class JSONUtil {
        @SuppressWarnings("unused")
		private static final String DEFAULT_CHARSET_NAME = "UTF-8";

        public static <T> String serialize(T object) {
            return JSON.toJSONString(object);
        }

        public static <T> T deserialize(String string, Class<T> clz) {
            return JSON.parseObject(string, clz);
        }
        
}