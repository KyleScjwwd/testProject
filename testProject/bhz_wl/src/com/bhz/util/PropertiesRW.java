package com.bhz.util;

import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.OutputStream;  
import java.util.Properties;

public class PropertiesRW {
	
	private String path;
  
    public PropertiesRW(String path) {
		this.path = path;
	}

	public String readValue(String key) {  
        Properties props = new Properties();  
        FileInputStream fis = null;  
        try {  
            fis = new FileInputStream(path);  
            props.load(fis);  
            String value = props.getProperty(key);  
            return value;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (null != fis) {  
                try {  
                    fis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return null;  
    }  
      
    public void writeProperties(String parameterName, String parameterValue) {  
        Properties prop = new Properties(); 
        FileInputStream fis = null;  
        OutputStream fos = null;  
        try {  
            fis = new FileInputStream(path);  
            prop.load(fis);  
            fis.close();//一定要在修改值之前关闭fis  
            fos = new FileOutputStream(path);  
            prop.setProperty(parameterName, parameterValue);  
            prop.store(fos, null);//第二个参数为注释  
            fos.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (null != fos) {  
                try {  
                    fos.close();  
                    if(null != fis) fis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
}
