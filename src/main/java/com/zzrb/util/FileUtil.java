package com.zzrb.util;

import com.alibaba.fastjson.JSONObject;
import com.zzrb.ecc.AnboECCDecrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    //从classpath中获取文件
    public static String getKeyStrByResources(String fileName, String keyType, String value) throws Exception {
        InputStream is = AnboECCDecrypt.class.getClassLoader().getResourceAsStream(fileName);
        if(is == null){
            throw new Exception("私钥文件不存在");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s="";
        StringBuilder configContentStr = new StringBuilder();
        try {
            while((s=br.readLine())!=null) {
                configContentStr.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(configContentStr.toString());
        return JSONObject.parseObject(jsonObject.get(keyType).toString()).get(value).toString();
    }
}
