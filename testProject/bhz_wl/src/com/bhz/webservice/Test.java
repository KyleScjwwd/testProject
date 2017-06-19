package com.bhz.webservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import com.bhz.util.sendMessage.Client;

public class Test {
	
	public static void testXFire(){
		Service serviceModel = new ObjectServiceFactory()
		.create(GetDataService.class);
		XFireProxyFactory factory = new XFireProxyFactory(XFireFactory
				.newInstance().getXFire());
		String url = "http://localhost:8080/bhz_ly/services/GetDataService";
		try {
			File f1 = new File("d://data_10k.xml");
			InputStream is = new FileInputStream(f1);
			byte[] byt = new byte[is.available()];
		    is.read(byt);
			GetDataService gs = (GetDataService) factory.create(serviceModel,url);
			gs.saveTbBase(byt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMsg(){
		String sn = "SDK-DLS-010-00442";
		String pwd = "396004";
		try {
			Client client = new Client(sn,pwd);
			String result = client.mt("15116177043", "第2标段1号拌合站水泥实际用量超过实际值25%，理论值低于标准-短信发送测试(拓东软件)", "", "", "");
			System.out.println("发送结果:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		Test.sendMsg();
	}
}
