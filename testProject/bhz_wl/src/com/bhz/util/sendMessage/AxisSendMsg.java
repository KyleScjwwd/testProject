package com.bhz.util.sendMessage;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class AxisSendMsg {
	private String serviceURL = "http://117.40.178.51:28083/CmppWebServiceJax/CmppWebservice?wsdl";
	private String nameSpaceUri = "http://com.win.mgx/";
	private String sn = "";// 序列号
	private String pwd = "";// 密码
	
	public AxisSendMsg(String sn,String pwd){
		this.sn = sn;
		this.pwd = pwd;
	}
	
	//查余额
	public String getBalance() {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(serviceURL));
			call.setOperationName(new QName(nameSpaceUri, "getSurplus")); // 你要调用的方法名称
			call.addParameter("arg0", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.addParameter("arg1", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.setReturnType(XMLType.XSD_STRING); // 返回参数的类型
			String result = (String)(call.invoke(new Object[] {sn,pwd}));// 获取返回值
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}
	
	//发短信
	public String mt(String mobile, String content) {
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(serviceURL));
			call.setOperationName(new QName(nameSpaceUri, "sendSms")); // 你要调用的方法名称
			call.addParameter("arg0", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.addParameter("arg1", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.addParameter("arg2", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.addParameter("arg3", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.addParameter("arg4", XMLType.XSD_STRING,ParameterMode.IN); // 传进去的参数值
			call.setReturnType(XMLType.XSD_STRING); // 返回参数的类型
			String result = (String)(call.invoke(new Object[] {sn,pwd,mobile,content,""}));// 获取返回值
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}
	
	public static void main(String[] args) throws Exception{
		AxisSendMsg as = new AxisSendMsg("320102","n8r74k2k");
		String result = as.getBalance();//as.mt("", "【经纬万达】test............12");
		System.out.println(result);
	}
	
}
