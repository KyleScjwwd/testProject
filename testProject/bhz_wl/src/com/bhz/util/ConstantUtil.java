package com.bhz.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bhz.pojo.TbBaoJingMain;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbMessageUser;
import com.bhz.pojo.TdMergeColsOrder;


/***
 * 公共常量类
 * @author 
 *
 */

public class ConstantUtil {
	public final static String fun_State = "BHZ1";
	public final static String fun_Data = "BHZ2";
	public final static String fun_Msg = "BHZ3";
	public final static String fun_Count = "BHZ4";
	public final static String fun_Sys = "BHZ5";
	/*
	public final static String fun_Sys = "fun01";
	public final static String fun_Sys_01 = "fun0101";
	public final static String fun_Sys_02 = "fun0102";
	public final static String fun_Sys_03 = "fun0103";
	public final static String fun_Sys_04 = "fun0104";
	public final static String fun_Sys_05 = "fun0105";
	public final static String fun_Sys_06 = "fun0106";
	public final static String fun_Sys_07 = "fun0107";
	public final static String fun_Sys_08 = "fun0108";
	public final static String fun_Sys_09 = "fun0109";
	public final static String fun_Sys_10 = "fun01010";
	public final static String fun_Count = "fun02";
	public final static String fun_Count_01 = "fun0201";
	public final static String fun_Count_02 = "fun0202";
	public final static String fun_Msg = "fun03";
	public final static String fun_Msg_01 = "fun0301";
	public final static String fun_Msg_02 = "fun0302";
	public final static String fun_Msg_03 = "fun0303";
	public final static String fun_Msg_04 = "fun0304";
	public final static String fun_Msg_05 = "fun0305";
	public final static String fun_Msg_06 = "fun0306";
	public final static String fun_Data = "fun04";
	public final static String fun_State = "fun05";
	*/
	//联网状态判断与当前时间相差分数
	public final static Integer webMinute = 15;
	//出料时间与上传到Web端时的日期相差的分钟数
	public final static long MinuteDiff = 30;
	//读取文件行数变量
	public final static Integer lineNum = 100;
	//读写文件地址
	public static String filePath = "";
	//WebRoot的地址
	public static String rootPath;
	//init变量
	public static Map<String, TbBd> bdMap;
	public static Map<String, TbBhz> bhzMap;
	public static Map<String, List<TbBaoJingMain>> bjMainMap;
	public static Map<String, List<TbBaoJingMainMerge>> bjMainMergeMap;
	public static Map<String, List<TbBaoJingSub>> bjSubMap;
	public static Map<String, List<TbBaoJingSubMerge>> bjSubMergeMap;
	public static Map<Integer, TbMessageUser> messageUserMap;
	public static Map<String, TdMergeColsOrder> mergeColsMap;
	public static Map<String,String> bjTypeMap = new HashMap<String,String>();
	
	static {
		bjTypeMap.put("0", "初级");
		bjTypeMap.put("1", "中级");
		bjTypeMap.put("2", "高级");
	}
}
