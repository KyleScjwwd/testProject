package com.bhz.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhz.dao.TbBaseDao;
import com.bhz.service.MessageService;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import com.bhz.pojo.TbBase;

public class JDomReaderXml {
	//private static Integer a = 1001;
	
	public static Map<Integer,TbBase> getTbBaseData(TbBaseDao tbBaseDao,byte[] byt)throws Exception{
		Map<Integer,TbBase> returnMap = new HashMap<Integer,TbBase>();
		SAXBuilder builder = new SAXBuilder();
		InputStream is = new ByteArrayInputStream(byt);
		Document doc = builder.build(is);
		Element foo = doc.getRootElement();    
		List rootList = foo.getChildren();
		TbBase b = null;
		for(int i=0;i<rootList.size();i++) {
			b = new TbBase();
			Element rootEle = (Element)rootList.get(i);
			List childList = rootEle.getChildren();
			for(int j=0;j<childList.size();j++) {
				Element childEle = (Element)childList.get(j);
				Util.setObjectValue(b, childEle.getName(), childEle.getValue());
			}
			String hql="from TbBase where groupId='"+b.getGroupId()+"' and m1='"+b.getM1()+"' and m2='"+b.getM2()+"' and m6='"+b.getM6()+"'";
			List<TbBase> tbBases=tbBaseDao.queryByHql(hql);
			if(null==tbBases || tbBases.size()==0){
				returnMap.put(i, b);
			}
		}
		return returnMap;
	}
	
	public static boolean writeFile(InputStream is,String fileObj)throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        FileOutputStream out = null;
        boolean flag = false;
        try {
        	File file=new File(fileObj);
        	if(!file.exists())
                file.createNewFile();
        	out = new FileOutputStream(file,false);
            while ((line = reader.readLine()) != null) {
                 sb.append(line + "\n");
            }
            out.write(sb.toString().getBytes());
            flag = true;
         } catch (Exception e) {
        	 flag = false;
        	 throw new Exception();
         } finally {
        	 out.close();
        	 is.close();
         }
         return flag;
	}
	
	public static void main(String[] args)throws Exception{
		File f1 = new File("E:\\workSpace\\classes\\artifacts\\Hz_Bhz_war_exploded\\jfreeChartFile\\ds.xml");
		InputStream is = new FileInputStream(f1);
	    byte[] byt = new byte[is.available()];
	    is.read(byt);
		//Map<Integer,TbBase> map = JDomReaderXml.getTbBaseData(byt);
		//TbBase t = map.get(1);
		//TbBase t1 = map.get(2);

	}
}
