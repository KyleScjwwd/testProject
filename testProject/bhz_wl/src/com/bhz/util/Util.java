package com.bhz.util;

import com.bhz.pojo.TbBase;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class Util {
	public final static String charSetName = "GBK";
	public final static String[] paramInitVal = {"水泥1","煤灰","砂1","砂2","碎石1","碎石2","减水剂1","水","水2",
		"水泥2","碎石3","矿粉","粉料5","粉料6","减水剂2","外加剂3","外加剂4","","",""};
	
	public final static String[] countParamVal = {"水泥","砂","石","粉煤灰","减水剂","外加剂"};	//材料用量明细统计初始值
	public static String tmpBhzCarOrder = null;
	
	//获取登陆用户名
	public static String getUserName(HttpServletRequest request)throws Exception{
		Object obj = request.getSession().getAttribute("username");
		if(obj==null){
			return null;
		}
		String username = obj.toString();
		if(isEmpty(username)){
			return null;
		}
		return username;
	}
	
	//获取projectId
	public static String getProjectId(HttpServletRequest request)throws Exception{
		Object obj = request.getSession().getAttribute("pid");
		if(obj==null){
			return "";
		}
		String pid = obj.toString();
		if(isEmpty(pid)){
			return "";
		}
		return pid;
	}
	
	//获取指定格式的当前日期 如：yyyy-MM-dd HH:mm:ss
	public static String getCurrentDate(String pattern)throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		String date = sf.format(new Date());
		return date;
	}
	
	//格式化日期
	public static String getPatternDate(Object date,String pattern)throws Exception{
		if(date==null)
			return "";
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(date);
	}
	
	//格式化日期
	 public static Date getPatternDate(String date,String pattern)throws Exception{
		 if(Util.isEmpty(date))
			 return null;
		 SimpleDateFormat sf = new SimpleDateFormat(pattern);
		 return sf.parse(date);
	 }
	 
	 //获得指定日期和当前日期之间差多少分钟
	 public static long getMinuteDiff(Date targetDate) {
		 if (targetDate == null)
			 return 10000;
		 Date currTime = new Date();
		 long c = Math.abs(currTime.getTime() - targetDate.getTime());
		 long result = c / (60 * 1000);
		 return result;
	 }

	//判断字符串是否为空
	public static boolean isEmpty(String str)throws Exception{
		if(str == null)
			return true;
		if(str.trim().equals(""))
			return true;
		return false;
	}
	
	//首字母大写
	public static String toFirstLetterUpperCase(String str)throws Exception{   
	    if(str == null || str.length() < 2){   
	        return str;   
	    }
	    String firstLetter = str.substring(0, 1).toUpperCase();
	    return firstLetter + str.substring(1, str.length());   
	}
	
	//四舍五入保留小数位
	public static Double toDecimal(double d,Integer num)throws Exception{
		BigDecimal b = new BigDecimal(d);
		double result = b.setScale(num,BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	// 判断文件夹是否存在，如果不存在则新建
	public static void isChartPathExist(String chartPath) {
		File file = new File(chartPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
	//对传入的字符串数组去重，并将结果用逗号串起来返回
	public static String uniqueStringArr(String[] sArr) throws Exception {
		String result = "";
		if (sArr == null || sArr.length == 0)
			return result;
		
		List<String> list = new LinkedList<String> ();
		for (int i = 0; i < sArr.length; i++) {
			if (sArr[i] != null) {
				String t = sArr[i].trim();
				if (!Util.isEmpty(t) && !list.contains(t))
					list.add(t);				
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (!Util.isEmpty(list.get(i))) {
				if (i == list.size()-1)
					result += list.get(i);
				else
					result += list.get(i) + ",";				
			}
		}		
		return result;
	}
	
	//反射获取值
	public static double getObjectValue(Object getObj,String fieldName)throws Exception{
		double result = 0;
		Class<?> getClass = getObj.getClass();
		String getMethodName = "get"+Util.toFirstLetterUpperCase(fieldName);
		Object value = getClass.getMethod(getMethodName).invoke(getObj);
		if(value!=null){
			String str = value.toString();
			if(!Util.isEmpty(str)){
				result=Double.valueOf(str);
			}
		}
		return result;
	}
	
	//反射设置值
	public static void setObjectValue(Object setObj,String fieldName,String value)throws Exception{
		Class<?> setClass = setObj.getClass();
		Field field = setClass.getDeclaredField(fieldName);
		String fieldType = field.getType().getName();
		String setMethodName = "set"+toFirstLetterUpperCase(fieldName);
		Method method = setClass.getMethod(setMethodName,field.getType());
		if(!isEmpty(value)){
			if (fieldType.equals("java.lang.String")) {
				method.invoke(setObj, value);
			}else if (fieldType.equals("java.util.Date")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date s_date =(Date)sdf.parse(value);
				method.invoke(setObj, s_date);
			}else if(fieldType.equals("java.lang.Double")){
				method.invoke(setObj, Double.valueOf(value));
			}else if(fieldType.equals("java.lang.Float")){
				method.invoke(setObj,Float.parseFloat(value));
			}
		}else{
			if (fieldType.equals("java.lang.String")) {
				method.invoke(setObj, "");
			}
		}
	}
	
	//反射设置值2
	public static void setObjectValue2(Object setObj,String fieldName,String value)throws Exception{
			Class<?> setClass = setObj.getClass();
			Field field = setClass.getDeclaredField(fieldName);
			String fieldType = field.getType().getName();
			String setMethodName = "set"+toFirstLetterUpperCase(fieldName);
			Method method = setClass.getMethod(setMethodName,field.getType());
			if(!isEmpty(value)){
				if (fieldType.equals("java.lang.String")) {
					method.invoke(setObj, value);
				}else if (fieldType.equals("java.util.Date")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date s_date =(Date)sdf.parse(value);
					method.invoke(setObj, s_date);
				}else if(fieldType.equals("java.lang.Double")){
					method.invoke(setObj, Double.valueOf(value));
				}
			}else{
				method.invoke(setObj, "");
			}
	}
	
	/**
	 * 反射对象赋值
	 * 第一个数组 为 属性 
	 * 第二个数组 为 属性的值
	 */
	public static Object setObjectValue (Object setObj, String[] fieldsNames, String[] fieldsValue)throws Exception{
		Class<?> setClass = setObj.getClass();
		Field[] fields = setClass.getDeclaredFields();
		for(Field field:fields){
			String name = field.getName();
			for(int i=0;i<fieldsValue.length;i++){
				if((name.toLowerCase()).equals((fieldsNames[i]).toLowerCase())){
		            String setMethodName = "set"+toFirstLetterUpperCase(name);
		            try {
		            	String fieldTypeName = field.getType().getName();
		                Method method = setClass.getMethod(setMethodName, field.getType());
		                if(!"".equals(fieldsValue[i])){
			                if (fieldTypeName.equals("java.lang.Integer")) {
			                		method.invoke(setObj, Integer.parseInt(fieldsValue[i]));
							}else if (fieldTypeName.equals("java.lang.String")) {
								method.invoke(setObj, fieldsValue[i]);
							}else if (fieldTypeName.equals("java.util.Date")) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								Date s_date =(Date)sdf.parse(fieldsValue[i]);
								method.invoke(setObj, s_date);
							}else if(fieldTypeName.equals("java.lang.long")){
								method.invoke(setObj, Long.parseLong(fieldsValue[i]));
							}
		                }
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		return setObj;
	}

	public static TbBase creatNewbSJ(TbBase bSJ,TbBase tbBase){
		bSJ.setGroupId(tbBase.getGroupId());
		if(null==bSJ.getM10()){
			bSJ.setM10(tbBase.getM10());
		}else{
			bSJ.setM10(bSJ.getM10()+tbBase.getM10());
		}
		if(null==bSJ.getA1()){
			bSJ.setA1(tbBase.getA1());
		}else{
			bSJ.setA1((Float.parseFloat(bSJ.getA1())+Float.parseFloat(tbBase.getA1()))+"");
		}
		if(null==bSJ.getA2()){
			bSJ.setA2(tbBase.getA2());
		}else{
			bSJ.setA2((Float.parseFloat(bSJ.getA2())+Float.parseFloat(tbBase.getA2()))+"");
		}
		if(null==bSJ.getA3()){
			bSJ.setA3(tbBase.getA3());
		}else{
			bSJ.setA3((Float.parseFloat(bSJ.getA3())+Float.parseFloat(tbBase.getA3()))+"");
		}
		if(null==bSJ.getA4()){
			bSJ.setA4(tbBase.getA4());
		}else{
			bSJ.setA4((Float.parseFloat(bSJ.getA4())+Float.parseFloat(tbBase.getA4()))+"");
		}
		if(null==bSJ.getA5()){
			bSJ.setA5(tbBase.getA5());
		}else{
			bSJ.setA5((Float.parseFloat(bSJ.getA5())+Float.parseFloat(tbBase.getA5()))+"");
		}
		if(null==bSJ.getA6()){
			bSJ.setA6(tbBase.getA6());
		}else{
			bSJ.setA6((Float.parseFloat(bSJ.getA6())+Float.parseFloat(tbBase.getA6()))+"");
		}if(null==bSJ.getA7()){
			bSJ.setA7(tbBase.getA7());
		}else{
			bSJ.setA7((Float.parseFloat(bSJ.getA7())+Float.parseFloat(tbBase.getA7()))+"");
		}
		if(null==bSJ.getA8()){
			bSJ.setA8(tbBase.getA8());
		}else{
			bSJ.setA8((Float.parseFloat(bSJ.getA8())+Float.parseFloat(tbBase.getA8()))+"");
		}
		if(null==bSJ.getA9()){
			bSJ.setA9(tbBase.getA9());
		}else{
			bSJ.setA9((Float.parseFloat(bSJ.getA9())+Float.parseFloat(tbBase.getA9()))+"");
		}
		if(null==bSJ.getA10()){
			bSJ.setA10(tbBase.getA10());
		}else{
			bSJ.setA10((Float.parseFloat(bSJ.getA10())+Float.parseFloat(tbBase.getA10()))+"");
		}
		if(null==bSJ.getA11()){
			bSJ.setA11(tbBase.getA11());
		}else{
			bSJ.setA11((Float.parseFloat(bSJ.getA11())+Float.parseFloat(tbBase.getA11()))+"");
		}
		if(null==bSJ.getA12()){
			bSJ.setA12(tbBase.getA12());
		}else{
			bSJ.setA12((Float.parseFloat(bSJ.getA12())+Float.parseFloat(tbBase.getA12()))+"");
		}
		if(null==bSJ.getA13()){
			bSJ.setA13(tbBase.getA13());
		}else{
			bSJ.setA13((Float.parseFloat(bSJ.getA13())+Float.parseFloat(tbBase.getA13()))+"");
		}
		if(null==bSJ.getA14()){
			bSJ.setA14(tbBase.getA14());
		}else{
			bSJ.setA14((Float.parseFloat(bSJ.getA14())+Float.parseFloat(tbBase.getA14()))+"");
		}
		if(null==bSJ.getA15()){
			bSJ.setA15(tbBase.getA15());
		}else{
			bSJ.setA15((Float.parseFloat(bSJ.getA15())+Float.parseFloat(tbBase.getA15()))+"");
		}
		if(null==bSJ.getA16()){
			bSJ.setA16(tbBase.getA16());
		}else{
			bSJ.setA16((Float.parseFloat(bSJ.getA16())+Float.parseFloat(tbBase.getA16()))+"");
		}
		if(null==bSJ.getA17()){
			bSJ.setA17(tbBase.getA17());
		}else{
			bSJ.setA17((Float.parseFloat(bSJ.getA17())+Float.parseFloat(tbBase.getA17()))+"");
		}
		if(null==bSJ.getA18()){
			bSJ.setA18(tbBase.getA18());
		}else{
			bSJ.setA18((Float.parseFloat(bSJ.getA18())+Float.parseFloat(tbBase.getA18()))+"");
		}
		if(null==bSJ.getA19()){
			bSJ.setA19(tbBase.getA19());
		}else{
			bSJ.setA19((Float.parseFloat(bSJ.getA19())+Float.parseFloat(tbBase.getA19()))+"");
		}
		if(null==bSJ.getA20()){
			bSJ.setA20(tbBase.getA20());
		}else{
			bSJ.setA20((Float.parseFloat(bSJ.getA20())+Float.parseFloat(tbBase.getA20()))+"");
		}
		bSJ.setM1(tbBase.getM1());
		bSJ.setM2(tbBase.getM2());
		bSJ.setM3(tbBase.getM3());
		bSJ.setM4(tbBase.getM4());
		bSJ.setM5(tbBase.getM5());
		bSJ.setM6(tbBase.getM6());
		bSJ.setM7(tbBase.getM7());
		bSJ.setM8(tbBase.getM8());
		bSJ.setM9(tbBase.getM9());

		bSJ.setBhzCarNum(tbBase.getBhzCarNum());
		bSJ.setBhzCarOrder(tbBase.getBhzCarOrder());
		bSJ.setBhzOperator(tbBase.getBhzOperator());
		bSJ.setBhzTotalQt(tbBase.getBhzTotalQt());

		return bSJ;
	}

	public static List<Object []> createNewObjArray(List<Object[]> objects,Object[] o,int length){
		List<Object []> tmpList=new ArrayList<Object[]>();
		Object [] a1= new Object[length];
		Object [] a2= new Object[length];
		Object [] a3= new Object[length];
		for (int i = 0; i <objects.size() ; i++) {
			Object [] b=objects.get(i);
			if("0".equals(b[0])){
				for (int j = 0; j <length ; j++) {
					if(j>=o.length){
						for(int k=2;k<b.length;k++){
							a1[j]=b[k];
							j++;
						}
					}else{
						a1[j]=o[j];
					}
				}
			}else if ("1".equals(b[0])){
				for (int j = 2; j <b.length ; j++) {
					if(j+8<a2.length){
						if(null==a2[j+8]){
							a2[j+8]=b[j];
						}else{
							if(j+8<=length){
								if(null!=b[j]){
									Double n=  Double.parseDouble(a2[j+8].toString());
									Double m=  Double.parseDouble(b[j].toString());
									a2[j+8]=n+m;
								}
							}
						}
					}
				}
			}else if("2".equals(b[0])){
				for (int j = 2; j <b.length ; j++) {
					if(j+8<a3.length){
						if(null==a3[j+8]){
							a3[j+8]=b[j];
						}else{
							if(j+8<=length){
								if(null!=b[j]){
									Double n=  Double.parseDouble(a3[j+8].toString());
									Double m=  Double.parseDouble(b[j].toString());
									a3[j+8]=n+m;
								}
							}
						}
					}
				}
			}
		}
		tmpList.add(a1);
		tmpList.add(a2);
		tmpList.add(a3);
		return tmpList;
	}

	public static Map findObjBhzCarOrder(Map tmp,List<Object[]> b,String bhzCarOrder){
		List<Object[]> a=new ArrayList<Object[]>();
		for(int i=0;i<b.size();i++){
			Object[] objects=b.get(i);
			if(null!=objects[0]){
				String bhzCarOrderKey= (String) objects[1];
				if(bhzCarOrder.equals(bhzCarOrderKey)){
					a.add(objects);
				}
			}
		}
		tmp.put(bhzCarOrder,a);
		return tmp;
	}

	public static Map createNewMap(Map tmp,List baseList){
		for(int i=0;i<baseList.size();i++){
			Object[] objs = (Object[])baseList.get(i);
			String bhzCarOrderKey="";
			if(objs[0]!=null){
				bhzCarOrderKey= (String) objs[1];
				tmp=Util.findObjBhzCarOrder(tmp,baseList,bhzCarOrderKey);
			}
		}
		return tmp;
	}

	//进行包含的查询 如某某特大桥 桩基
	public static String getNewString(String fieldName, String paramValue)
			throws Exception {
		String hql = "";
		if (paramValue == null || paramValue.equals(""))
			return hql;
		String[] values = paramValue.trim().split(" ");
		if (values.length > 0) {
			boolean flag = false;
			for (int i = 0; i < values.length; i++) {
				if (flag)
					hql += values[i].trim().length() == 0 ? "" : " and "
							+ fieldName + " like '%" + values[i].trim() + "%'";
				else {
					hql += values[i].trim().length() == 0 ? "" : " and("
							+ fieldName + "  like '%" + values[i].trim() + "%'";
					flag = true;
				}
			}
			hql += hql.length() > 0 ? ")" : "";
		}
		return hql;
	}

	/**
	 * 无秘钥加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encodeString(String str) throws Exception {
		if(isEmpty(str))
			return "";
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String encodedStr = new String(encoder.encodeBuffer(str.getBytes()));
		String temp = encodedStr.replace('=', '.').replace('+', '*').replace('/', '-');
		return temp.trim();
	}

	/**
	 * 无秘钥解密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String decodeString(String str) throws Exception {
		if(isEmpty(str))
			return "";
		String temp = str.replace('.', '=').replace('*', '+').replace('-', '/');
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		String value = new String(dec.decodeBuffer(temp));
		return value;
	}
	
	/**
	 * 检测是否具有权限 true：有 false：无
 	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean isUserAuthor(HttpServletRequest request,String key,String value){
		try {
			if(!isEmpty(getUserName(request)) && getUserName(request).equals("admin"))
				return true;
			Object obj = request.getSession().getAttribute("userauthor");
			if(obj==null)
				return false;
			Map<String,String> authMap = (Map<String,String>)obj;
			if(authMap==null || authMap.size()==0)
				return false;
			if(authMap.get(key)!=null && authMap.get(key).equals(value))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static String readProperties(String key)throws Exception{
		if(isEmpty(key))
			return "";
		ResourceBundle rb = ResourceBundle.getBundle("config");
		if(!rb.containsKey(key))
			return "";
		return rb.getString(key);
	}
	
	public static String readProperties(String fileName,String key)throws Exception{
		if(isEmpty(key))
			return "";
		ResourceBundle rb = ResourceBundle.getBundle(fileName);
		if(!rb.containsKey(key))
			return "";
		return rb.getString(key);
	}
}
