package com.bhz.webservice.impl;

import java.util.Map;
import com.bhz.pojo.TbBase;
import com.bhz.service.MessageService;
import com.bhz.util.JDomReaderXml;
import com.bhz.webservice.GetDataService;

public class GetDataServiceImpl implements GetDataService{
	
	private MessageService messageService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public String sayHello()throws Exception{
		System.out.println("测试.....");
		return "您好，欢迎光临";
	}
	
	public void saveTbBase(byte[] byt)throws Exception{
		//Map<Integer,TbBase> baseMap = JDomReaderXml.getTbBaseData(byt);
		//messageService.saveTbBaseData(baseMap);
	}
}
