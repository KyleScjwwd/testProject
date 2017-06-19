package com.bhz.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test {

	public static String sendPost(String url, byte[] param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static void sendPost2(InputStream in) {
		PostMethod post = new PostMethod("http://localhost:8080/bhz_ly/loginAction!testService.action");// 请求地址
		post.setRequestBody(in);// 这里添加xml字符串

		// 指定请求内容的类型
		post.setRequestHeader("Content-type", "text/xml; charset=GBK");
		HttpClient httpclient = new HttpClient();// 创建 HttpClient 的实例
		int result;
		try {
			result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);// 返回200为成功
			System.out.println("Response body: ");
			System.out.println(post.getResponseBodyAsString());// 返回的内容
			post.releaseConnection();// 释放连接
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			File f = new File("d://data_10k.xml");
			InputStream in = new FileInputStream(f);
			/*if (f.exists()) {
				System.out.println("size1=" + in.available());
				String str = Test
						.sendPost(
								"http://localhost:8080/bhz_ly/loginAction!testService.action",
								new byte[in.available()]);
				System.out.println(str);
			} else {
			}*/
			Test.sendPost2(in);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
