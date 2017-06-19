package com.bhz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.bhz.dao.TbBaseDao;
import com.bhz.dao.TbMessageUserDao;
import com.bhz.dao.TbMessgeSendTypeDao;
import com.bhz.dao.TbParamSetDao;
import com.bhz.dao.TbSendMsgLogDao;
import com.bhz.pojo.TbBaoJingMain;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.pojo.TbBase;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbMessageUser;
import com.bhz.pojo.TbMessgeSendType;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TbSendMsgLog;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.util.ConstantUtil;
import com.bhz.util.FileUtil;
import com.bhz.util.PropertiesRW;
import com.bhz.util.JDomReaderXml;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.PageUtils.PagingFactory;
import com.bhz.util.sendMessage.AxisSendMsg;
import com.bhz.util.sendMessage.Client;

public class MessageService extends BaseService{
	private TbBaseDao tbBaseDao;
	private PagingFactory pageDao;
	private TbMessageUserDao tbMessageUserDao;
	private TbSendMsgLogDao tbSendMsgLogDao;
	private TbParamSetDao tbParamSetDao;
	private TbMessgeSendTypeDao tbMessgeSendTypeDao;
	
	public void setTbSendMsgLogDao(TbSendMsgLogDao tbSendMsgLogDao) {
		this.tbSendMsgLogDao = tbSendMsgLogDao;
	}
	public void setTbBaseDao(TbBaseDao tbBaseDao) {
		this.tbBaseDao = tbBaseDao;
	}
	public void setTbMessageUserDao(TbMessageUserDao tbMessageUserDao) {
		this.tbMessageUserDao = tbMessageUserDao;
	}
	public void setTbParamSetDao(TbParamSetDao tbParamSetDao) {
		this.tbParamSetDao = tbParamSetDao;
	}
	public void setTbMessgeSendTypeDao(TbMessgeSendTypeDao tbMessgeSendTypeDao) {
		this.tbMessgeSendTypeDao = tbMessgeSendTypeDao;
	}
	public void setPageDao(PagingFactory pageDao) {
		this.pageDao = pageDao;
	}

	public List<TbMessageUser> queryMessageUserByBd(String bdCode,String realName,String telphone)
		throws Exception{
		String hql = "from TbMessageUser where 1=1";
		if(!Util.isEmpty(bdCode))
			hql+=" and bdCode='"+bdCode+"'";
		if(!Util.isEmpty(realName))
			hql+=" and realName like '%"+realName+"%'";
		if(!Util.isEmpty(telphone))
			hql+=" and telphone='"+telphone+"'";
		return tbMessageUserDao.queryByHql(hql);
	}
	
	public List<TbMessageUser> getAllMessageUser() throws Exception {
		String hql = "from TbMessageUser";
		return tbMessageUserDao.queryByHql(hql);
	}
	
	public Map<String,String> getMessageUserRealName()throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		List<TbMessageUser> list = getAllMessageUser();
		for(TbMessageUser t : list){
			result.put(t.getTelphone(), t.getRealName());
		}
		return result;
	}
	
	//获得所有的“姓名和号码不重复”的报警联系人
	public List<TbMessageUser> getDistinctAllMessageUser() throws Exception {
		String hql = "from TbMessageUser";
		List<TbMessageUser> resultList = tbMessageUserDao.queryByHql(hql);
		Map<String, TbMessageUser> map = new HashMap<String, TbMessageUser>();
		for (TbMessageUser u : resultList) {
			String key = u.getRealName() + u.getTelphone();
			if (!map.containsKey(key)) {
				map.put(key, u);
			}
		}
		
		List<TbMessageUser> list =  new ArrayList<TbMessageUser>();
		for (String s : map.keySet()) {
			list.add(map.get(s));
		}
		return list;
	}
	
	public void setMessageUserCache() throws Exception {
		List<TbMessageUser> messageUserList = getDistinctAllMessageUser();
		Map<Integer, TbMessageUser> messageUserMap = new HashMap<Integer, TbMessageUser>();
		for (TbMessageUser u : messageUserList) {
			messageUserMap.put(u.getId(), u);
		}
		ConstantUtil.messageUserMap = messageUserMap;
		System.out.println("messageUserMap size=" + ConstantUtil.messageUserMap.size());
	}
	
	public Page queryPageMessageUser(Page page,String bdCode,String realName,String telphone)
		throws Exception{
		if(page==null)
			page = new Page();
		String hql = "TbMessageUser where 1=1";
		if(!Util.isEmpty(bdCode))
			hql+=" and bdCode='"+bdCode+"'";
		if(!Util.isEmpty(realName))
			hql+=" and realName like '%"+realName+"%'";
		if(!Util.isEmpty(telphone))
			hql+=" and telphone='"+telphone+"'";
		page.setObjName(hql);
		return pageDao.queryObjList(page);
	}
	
	public void insertTbMessageUser(TbMessageUser t)throws Exception{
		tbMessageUserDao.insert(t);
		setMessageUserCache();
	}
	
	public void deleteTbMessageUser(TbMessageUser t)throws Exception{
		tbMessageUserDao.delete(t);
		setMessageUserCache();
	}
	
	public Map<String,TbBaoJingMain> queryTbBaoJingMain(String bdCode,String bhzCode,String phbQd)throws Exception{
		String hql = "from TbBaoJingMain where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
		List<TbBaoJingMain> mainList = tbBaoJingMainDao.queryByHql(hql);
		Map<String,TbBaoJingMain> returnMap = new HashMap<String,TbBaoJingMain>();
		for(TbBaoJingMain m:mainList){
			returnMap.put(m.getMainId(), m);
		}
		return returnMap;
	}
	
	public Map<String, TbBaoJingMainMerge> queryTbBaoJingMainMerge(String bdCode, String bhzCode, String phbQd)throws Exception{
		String hql = "from TbBaoJingMainMerge where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
		List<TbBaoJingMainMerge> mainList = tbBaoJingMainMergeDao.queryByHql(hql);
		Map<String, TbBaoJingMainMerge> returnMap = new HashMap<String, TbBaoJingMainMerge>();
		for(TbBaoJingMainMerge m : mainList){
			returnMap.put(m.getMainId(), m);
		}
		return returnMap;
	}	
	
	public Map<String,TbBaoJingSub> queryTbBaoJingSub(String bdCode,String bhzCode,String phbQd)throws Exception{
		String hql = "from TbBaoJingSub where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
		List<TbBaoJingSub> subList = tbBaoJingSubDao.queryByHql(hql);
		Map<String,TbBaoJingSub> returnMap = new HashMap<String,TbBaoJingSub>();
		for(TbBaoJingSub s:subList){
			returnMap.put(s.getMainId()+"-"+s.getSendType(),s);
		}
		return returnMap;
	}
	
	public Map<String, TbBaoJingSubMerge> queryTbBaoJingSubMerge(String bdCode, String bhzCode, String phbQd)throws Exception{
		String hql = "from TbBaoJingSubMerge where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
		List<TbBaoJingSubMerge> subList = tbBaoJingSubMergeDao.queryByHql(hql);
		Map<String, TbBaoJingSubMerge> returnMap = new HashMap<String, TbBaoJingSubMerge>();
		for(TbBaoJingSubMerge s : subList){
			returnMap.put(s.getMainId() + "-" + s.getSendType(), s);
		}
		return returnMap;
	}
	
	public Map<String,TbBaoJingSub> getBaoJingSub(String bdCode,String bhzCode)throws Exception{
		String hql = "from TbBaoJingSub where bdCode = '"+bdCode+"' and bhzCode='"+bhzCode+"' and sendType='0'";
		List<TbBaoJingSub> subList = tbBaoJingSubDao.queryByHql(hql);
		Map<String,TbBaoJingSub> returnMap = new HashMap<String,TbBaoJingSub>();
		for(TbBaoJingSub s:subList){
			returnMap.put(s.getMainId(),s);
		}
		return returnMap;
	}
	
	public void insertMessageSet(HttpServletRequest request,String bdCode,String bhzCode,String phbQd,
			List<TbParamSet> paramList)throws Exception{
		if(!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode)){
			//删除主子表
			String delHqlMain = "delete from TbBaoJingMain where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
			String delHqlSub = "delete from TbBaoJingSub where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and phbQd='"+phbQd+"'";
			tbBaoJingMainDao.deleteTbBaoJingMain(delHqlMain);
			tbBaoJingSubDao.deleteTbBaoJingSub(delHqlSub);
			//添加主子表
			if(paramList.size()>0){
				TbParamSet t = new TbParamSet();
				t.setPname("bht");
				paramList.add(t);
				TbBaoJingMain mainObj = null;
				TbBaoJingSub subObj = null;
				for(TbParamSet s : paramList){
					mainObj = new TbBaoJingMain();
					String clName = s.getPname();
					String isBaojing = request.getParameter("isBaojing-"+clName);
					String msgContent = request.getParameter("msgContent-"+clName);
					String mainId = bdCode+"-"+bhzCode+"-"+phbQd+"-"+clName;
					for(int i=0;i<3;i++){
						subObj = new TbBaoJingSub();
						String sendType = request.getParameter("sendType-"+clName+"-"+i);
						String mobile = request.getParameter("mobile-"+clName+"-"+i);
						String downValue = request.getParameter("down-"+clName+"-"+i);
						String upValue = request.getParameter("up-"+clName+"-"+i);
						subObj.setMainId(mainId);
						subObj.setBdCode(bdCode);
						subObj.setBhzCode(bhzCode);
						subObj.setPhbQd(phbQd);
						subObj.setSendType(sendType);
						subObj.setMobile(mobile);
						subObj.setDownValue(downValue);
						subObj.setUpValue(upValue);
						tbBaoJingSubDao.insert(subObj);
					}
					mainObj.setMainId(mainId);
					mainObj.setBdCode(bdCode);
					mainObj.setBhzCode(bhzCode);
					mainObj.setPhbQd(phbQd);
					mainObj.setClName(clName);
					mainObj.setIsBaojing(isBaojing);
					mainObj.setMsgContent(msgContent);
					tbBaoJingMainDao.insert(mainObj);
				}
				//重新设置进入缓存
				setBaoJingCache();
			}
		}
	}
	
	public void insertMessageSetMerge(HttpServletRequest request, String bdCode, String bhzCode, String phbQd, List<TdMergeColsOrder> mergeColsList) throws Exception {
		if (!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode)) {
			//删除主子表
			String delHqlMain = "delete from TbBaoJingMainMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and phbQd = '" + phbQd + "'";
			String delHqlSub = "delete from TbBaoJingSubMerge where bdCode = '" + bdCode + "' and bhzCode = '" + bhzCode + "' and phbQd = '" + phbQd + "'";
			tbBaoJingMainMergeDao.deleteTbBaoJingMainMerge(delHqlMain);
			tbBaoJingSubMergeDao.deleteTbBaoJingSubMerge(delHqlSub);
			//添加主子表
			if (mergeColsList.size() > 0) {
				TdMergeColsOrder m = new TdMergeColsOrder();
				m.setMergeCols("bht");
				mergeColsList.add(m);
				TbBaoJingMainMerge mainObj = null;
				TbBaoJingSubMerge subObj = null;
				for(TdMergeColsOrder s : mergeColsList){
					mainObj = new TbBaoJingMainMerge();
					String mergeName = s.getMergeCols();
					String isBaojing = request.getParameter("isBaojing-" + mergeName);
					String msgContent = request.getParameter("msgContent-" + mergeName);
					String mainId = bdCode + "-" + bhzCode + "-" + phbQd + "-" + mergeName;
					for(int i = 0; i < 3; i++){
						subObj = new TbBaoJingSubMerge();
						String sendType = request.getParameter("sendType-" + mergeName + "-" + i);
						String mobile = request.getParameter("mobile-" + mergeName + "-" + i);
						String mobile_bdCode = request.getParameter("mobile_bdCode-" + mergeName + "-" + i);
						String downValue = request.getParameter("down-" + mergeName + "-" + i);
						String upValue = request.getParameter("up-" + mergeName + "-" + i);
						subObj.setMainId(mainId);
						subObj.setBdCode(bdCode);
						subObj.setBhzCode(bhzCode);
						subObj.setPhbQd(phbQd);
						subObj.setSendType(sendType);
						subObj.setMobile(mobile);
						subObj.setMobile_bdCode(mobile_bdCode);
						subObj.setDownValue(downValue);
						subObj.setUpValue(upValue);
						tbBaoJingSubMergeDao.insert(subObj);
					}
					mainObj.setMainId(mainId);
					mainObj.setBdCode(bdCode);
					mainObj.setBhzCode(bhzCode);
					mainObj.setPhbQd(phbQd);
					mainObj.setMergeName(mergeName);
					mainObj.setIsBaojing(isBaojing);
					mainObj.setMsgContent(msgContent);
					tbBaoJingMainMergeDao.insert(mainObj);
				}
				//重新设置进入缓存
				setBaoJingCacheMerge();
			}
		}
	}
	
	public Page getBaoJingBase(HttpServletRequest request,Page page) {
		if(page==null)
			page = new Page();
		page.setObjName("TbBaoJingBase");
		return pageDao.queryObjList(page);
	}
	
	public Page getBaoJingPersonBase(HttpServletRequest request, Page page, String bdCode, String bhzCode) throws Exception {
		if(page==null)
			page = new Page();

		String hql = "TbBaoJingPersonBase where 1=1";
		
		if (!Util.isEmpty(bdCode))
			hql += " and bdCode = '" + bdCode + "'";
		if (!Util.isEmpty(bhzCode))
			hql += " and bhzCode = '" + bhzCode + "'";
		
		page.setObjName(hql);
		return pageDao.queryObjList(page);
	}
	
	public List<TbParamSet> getParamByItem(String bdCode,String bhzCode)
		throws Exception{
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return new ArrayList<TbParamSet>();
		String hql = "from TbParamSet where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"' and flag='1'";
		return tbParamSetDao.queryByHql(hql);
	}
	
	public void saveSendDataOrFile(final String flag,final byte[] byt)throws Exception{
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Map<Integer,TbBase> baseMap = JDomReaderXml.getTbBaseData(tbBaseDao,byt);
					if("1".equals(flag)){
						saveTbBaseData(baseMap);
					}else if("2".equals(flag)){
						setWriteFile(baseMap);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	//写入文件
	public void setWriteFile(Map<Integer,TbBase> map)throws Exception{
		StringBuffer sb = new StringBuffer("");
		//发送短信
		TbBase b = map.get(1);	//实际数据
		sendMessageOperater(b);
		for(int i=0;i<map.size();i++){
			TbBase t = map.get(i);
			String m4 = "";
			if(t.getM4()!=null){
				//m4 = Util.getPatternDate(t.getM4(), "yyyy-MM-dd hh:mm:ss");
				m4=t.getM4();
			}
			//拼写字符串
			sb.append("insert into TbBase (groupId,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10," +
					"a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,bhzCarNum,bhzCarOrder,bhzTotalQt,bhzOperator)");
			sb.append("values ('"+t.getGroupId()+"','"+t.getM1()+"','"+t.getM2()+"','"+t.getM3()+"','"+m4+"'," +
					"'"+t.getM5()+"','"+t.getM6()+"','"+t.getM7()+"','"+t.getM8()+"','"+t.getM9()+"','"+t.getM10()+"'," +
					"'"+t.getA1()+"','"+t.getA2()+"','"+t.getA3()+"','"+t.getA4()+"','"+t.getA5()+"','"+t.getA6()+"'," +
					"'"+t.getA7()+"','"+t.getA8()+"','"+t.getA9()+"','"+t.getA10()+"','"+t.getA11()+"','"+t.getA12()+"'," +
					"'"+t.getA13()+"','"+t.getA14()+"','"+t.getA15()+"','"+t.getA16()+"','"+t.getA17()+"','"+t.getA18()+"'," +
					"'"+t.getA19()+"','"+t.getA20()+"','"+t.getBhzCarNum()+"','"+t.getBhzCarOrder()+"','"+t.getBhzTotalQt()+"','"+t.getBhzOperator()+"')");
		}
		if(!Util.isEmpty(sb.toString())){
			String fileName = ConstantUtil.filePath+"\\tbbase.txt";
			FileUtil.appendLine(fileName, sb.toString());
		}
	}
	
	//单独线程执行
	public void saveTbBaseDataForTread(){
		try {
			System.out.println(Util.getCurrentDate("yyyy-MM-dd hh:mm:ss"));
			String fileName = ConstantUtil.filePath+"\\tbbase.txt";
			StringBuffer sb = FileUtil.readLines(fileName, ConstantUtil.lineNum);
			if(!Util.isEmpty(sb.toString())){
				//批量执行
				tbBaseDao.insertBySql(sb.toString());
				//删除读取行
				FileUtil.deleteLines(fileName, ConstantUtil.lineNum);
			}
			System.out.println("saveTbBaseDataForTread succss..");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//每次调用直接新增数据库
	public void saveTbBaseData(Map<Integer, TbBase> map) throws Exception {
		if (map.size() == 3) {
			//发送短信-基础变量
			TbBase bSJ = map.get(1);	//实际数据
			TbBase bSG = map.get(2);	//施工数据
			TbBhz tbBhz = new TbBhz();
			String bhzCode = bSJ.getM6();
			tbBhz = ConstantUtil.bhzMap.get(bhzCode);
			//固定时间格式
			if(!Util.isEmpty(bSJ.getM4()) && bSJ.getM4().contains("/"))
				bSJ.setM4(bSJ.getM4().replace("/", "-"));
			if(!Util.isEmpty(bSG.getM4()) && bSG.getM4().contains("/"))
				bSG.setM4(bSG.getM4().replace("/", "-"));
			long MinuteDiff = Util.getMinuteDiff(Util.getPatternDate(bSJ.getM4(),"yyyy-MM-dd HH:mm:ss"));	//换算出料时间
			//循环保存至数据库
			for (int i = 0; i < map.size(); i++) {
				//增加数据库记录
				TbBase t = map.get(i);
				if(!Util.isEmpty(t.getM4()) && t.getM4().contains("/"))
					t.setM4(t.getM4().replace("/", "-"));
				//施工配合比是否乘以方量,根据设置
				if("2".equals(t.getM1()) && "1".equals(tbBhz.getSgpbTest())){
					t = getDataMulFl(t, t.getM10());
				}
				tbBaseDao.insert(t);
			}
			String msgBaoJingType = "1";	//短信发送方式			
			PropertiesRW rw = this.getConfigFileValue();
			msgBaoJingType = rw.readValue("msgBaoJingType");
			if (tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())) {
				if("1".equals(msgBaoJingType)){
					//如果出料时间与数据上传过来时的日期小于30分钟以上，则报警
					if (MinuteDiff <= ConstantUtil.MinuteDiff) {
						sendMessageOperaterMerge2(bSJ, bSG);
					}
				}else if("0".equals(msgBaoJingType)){
					Double qt = getQtBybhzCarOrder(bSG.getM2(),bSG.getM6(),bSG.getBhzCarOrder());
					String bhzCarOrder = bSG.getBhzCarOrder();
					if(Util.tmpBhzCarOrder!=null && !Util.tmpBhzCarOrder.equals(bhzCarOrder)) {
						sendMessageCz(bSJ,bSG);
					}else if(!Util.isEmpty(Util.tmpBhzCarOrder) && qt>=bSG.getBhzTotalQt()){
						sendMessageCz(bSJ,bSG);
					}
                    Util.tmpBhzCarOrder = bhzCarOrder;
				}
			} else {
				//如果出料时间与数据上传过来时的日期小于30分钟以上，则报警
				if (MinuteDiff <= ConstantUtil.MinuteDiff) {
					sendMessageOperater(bSJ);
				}
			}
		}
	}
	
	public void sendMessageOperater(TbBase b)throws Exception {
		String bdCode = b.getM2();
		String bhzCode = b.getM6();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return;
		String key = bdCode + "-" + bhzCode + "-" + b.getM8();
		List<TbBaoJingMain> mainList = ConstantUtil.bjMainMap.get(key);
		List<TbBaoJingSub> subList = ConstantUtil.bjSubMap.get(key);
		if(mainList!=null && subList!=null){
			for(TbBaoJingMain m : mainList){
				String clName = m.getClName();
				String msgContent = m.getMsgContent();
				for(TbBaoJingSub s : subList){
					if(m.getMainId().equals(s.getMainId())){
						String value = "";
						if("bht".equals(clName)){
							value = b.getM9();
						}else{
							value = String.valueOf(Util.getObjectValue(b, clName));
						}
						sendMessage(s, clName, msgContent, value);
					}
				}
			}
		}
	}

	public void sendMessageOperaterMerge(TbBase bSJ, TbBase bSG) throws Exception {
		String bdCode = bSJ.getM2();
		String bhzCode = bSJ.getM6();
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode))
			return;
		String key = bdCode + "-" + bhzCode + "-" + bSJ.getM8();
		List<TbBaoJingMainMerge> mainList = ConstantUtil.bjMainMergeMap.get(key);
		List<TbBaoJingSubMerge> subList = ConstantUtil.bjSubMergeMap.get(key);
		if (mainList!=null && subList!=null) {
			for (TbBaoJingMainMerge m : mainList) {
				String mergeName = m.getMergeName();
				String msgContent = m.getMsgContent();
				for (TbBaoJingSubMerge s : subList) {
					if (m.getMainId().equals(s.getMainId())) {
						Double sumSJ = 0.0d;
						Double sumSG = 0.0d;
						String value = "";
						String bjType = s.getSendType();
						if ("bht".equals(mergeName)) {
							value = bSJ.getM9();
						} else {
							String[] clNames = mergeName.split("\\+");
							for (String clName : clNames) {
								sumSJ += Util.getObjectValue(bSJ, clName);
								sumSG += Util.getObjectValue(bSG, clName);
							}
							value = String.valueOf(Math.abs(sumSJ - sumSG));
						}
						sendMessageMerge(s, mergeName, msgContent, value, sumSJ, sumSG, bjType, bSJ.getGroupId());
					}
				}
			}
		}
	}

	//车载积累误差报警
	public void sendMessageCz(TbBase bSJ,TbBase bSG) throws Exception{
		String bdCode=bSG.getM2();
		String bhzCode=bSG.getM6();
		PropertiesRW rw = this.getConfigFileValue();
		String msgTemplate = rw.readValue("msgTemplate2");
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || Util.isEmpty(msgTemplate))
			return;
		String key = bdCode + "-" + bhzCode + "-" + bSJ.getM8();
		List<TbBaoJingMainMerge> mainList = ConstantUtil.bjMainMergeMap.get(key);
		List<TbBaoJingSubMerge> subList = ConstantUtil.bjSubMergeMap.get(key);
		Object [] objects=getMinAndMaxTime(bdCode,bhzCode,bSJ.getBhzCarOrder());
		if(mainList!=null && subList!=null){
			String bdName = ConstantUtil.bdMap.get(bdCode).getBdName();
			String bhzName = ConstantUtil.bhzMap.get(bhzCode).getBhzName();
			String bhzCarNum=bSJ.getBhzCarNum();
			String bhzCarOrder=bSJ.getBhzCarOrder();
			String JZBW = bSJ.getM5();  //浇筑部位
			String QDDJ = bSJ.getM8();  //强度等级
			String start=objects[0].toString();	//同一车次开始时间
			String end=objects[1].toString();	//同一车次结束时间
			String FL = bSG.getM10()==null?"":bSG.getM10().toString();
			msgTemplate = msgTemplate.replace("carm1", bdName);
			msgTemplate = msgTemplate.replace("carm2", bhzName);
			msgTemplate = msgTemplate.replace("carm3", JZBW);
			msgTemplate = msgTemplate.replace("carm4", QDDJ);
			msgTemplate = msgTemplate.replace("carm5", bhzCarOrder);
			msgTemplate = msgTemplate.replace("carm6", bhzCarNum);
			msgTemplate = msgTemplate.replace("carm7", start);
			msgTemplate = msgTemplate.replace("carm8", end);
			msgTemplate = msgTemplate.replace("carm9", FL);
			//当时间大于30分钟不能发送短信
			long MinuteDiff = Util.getMinuteDiff(Util.getPatternDate(end,"yyyy-MM-dd HH:mm:ss"));	//换算同一车次结束时间
			if (MinuteDiff > ConstantUtil.MinuteDiff){
				return;
			}
			//定义初中高级变量
			String mobilesCJ = "";
			String mobilesZJ = "";
			String mobilesGJ = "";
			String clCJ = "";
			String clZJ = "";
			String clGJ = "";
			for(TbBaoJingMainMerge m : mainList){
				String mergeName = m.getMergeName();
				Double sumSJ = 0.0d;
				Double sumSG = 0.0d;
				Double value = 0.0d;
				Double value1=0.0d;
				String diffPercent = "";
				String clInfo = "";
				if ("bht".equals(mergeName)) {
					value = Double.valueOf(bSJ.getM9());
				} else {
					String[] clNames = mergeName.split("\\+");
					for (String clName : clNames) {
						sumSJ += Util.getObjectValue(bSJ, clName);
						sumSG += Util.getObjectValue(bSG, clName);
					}
					value = Math.abs(sumSJ - sumSG);
					if (sumSG != 0)
						diffPercent = String.format("%.2f", 100*value/sumSG) + "%";
						value1=Double.parseDouble(String.format("%.2f", (100*value/sumSG)));
				}

				for(TbBaoJingSubMerge s : subList){
					if(m.getMainId().equals(s.getMainId())){
						if (!Util.isEmpty(s.getUpValue()) && !Util.isEmpty(s.getDownValue()) && !Util.isEmpty(s.getMobile())) {
							Double upVal = 0.0d;
							Double downVal = 0.0d;
							if ("bht".equals(mergeName)) {
								upVal = Double.valueOf(s.getUpValue());
								downVal = Double.valueOf(s.getDownValue());
							} else {
								upVal = Double.valueOf(s.getUpValue()); //* sumSG;
								downVal = Double.valueOf(s.getDownValue()) * sumSG;
							}
							if ("0".equals(s.getSendType())) {
								//System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1> upVal*100) {
									if (!mobilesCJ.contains(s.getMobile())) {
										mobilesCJ = mobilesCJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clCJ = clCJ + clInfo + "；";
								}
							} else if ("1".equals(s.getSendType())) {
								//System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1 > upVal*100) {
									if (!mobilesZJ.contains(s.getMobile())) {
										mobilesZJ = mobilesZJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clZJ = clZJ + clInfo + "；";
								}
							} else if ("2".equals(s.getSendType())) {
								//System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1 > upVal*100) {
									if (!mobilesGJ.contains(s.getMobile())) {
										mobilesGJ = mobilesGJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clGJ = clGJ + clInfo + "；";
								}
							}
						}
					}
				}
			}
			if (mobilesCJ.length() > 0 && clCJ.length() > 0) {
				mobilesCJ = Util.uniqueStringArr(mobilesCJ.split(","));
				clCJ = clCJ.substring(0, clCJ.length() - 1);

				String msgCJ = msgTemplate;
				msgCJ = msgCJ.replace("carmA", clCJ);
				sendMessageMerge2(bdCode,bhzCode, mobilesCJ, msgCJ, "0", bSJ.getGroupId(), bhzCarOrder);
			}
			if (mobilesZJ.length() > 0 && clZJ.length() > 0) {
				mobilesZJ = Util.uniqueStringArr(mobilesZJ.split(","));
				clZJ = clZJ.substring(0, clZJ.length() - 1);

				String msgZJ = msgTemplate;
				msgZJ = msgZJ.replace("carmA", clZJ);
				sendMessageMerge2(bdCode,bhzCode, mobilesZJ, msgZJ, "1", bSJ.getGroupId(), bhzCarOrder);
			}
			if (mobilesGJ.length() > 0 && clGJ.length() > 0) {
				mobilesGJ = Util.uniqueStringArr(mobilesGJ.split(","));
				clGJ = clGJ.substring(0, clGJ.length() - 1);

				String msgGJ = msgTemplate;
				msgGJ = msgGJ.replace("carmA", clGJ);
				sendMessageMerge2(bdCode,bhzCode, mobilesGJ, msgGJ, "2", bSJ.getGroupId(), bhzCarOrder);
			}
		}
	}

	//合并各材料的报警一并发送
	public void sendMessageOperaterMerge2(TbBase bSJ, TbBase bSG) throws Exception {
		String bdCode = bSJ.getM2();
		String bhzCode = bSJ.getM6();
		PropertiesRW rw = this.getConfigFileValue();
		String msgTemplate = rw.readValue("msgTemplate1");
		if(Util.isEmpty(bdCode) || Util.isEmpty(bhzCode) || Util.isEmpty(msgTemplate))
			return;
		String key = bdCode + "-" + bhzCode + "-" + bSJ.getM8();
		List<TbBaoJingMainMerge> mainList = ConstantUtil.bjMainMergeMap.get(key);
		List<TbBaoJingSubMerge> subList = ConstantUtil.bjSubMergeMap.get(key);
		if (mainList!=null && subList!=null) {
			String bdName = ConstantUtil.bdMap.get(bdCode).getBdName();
			String bhzName = ConstantUtil.bhzMap.get(bhzCode).getBhzName();
			String JZBW = bSJ.getM5();  //浇筑部位
			String QDDJ = bSJ.getM8();  //强度等级
			//String CLSJ = Util.getPatternDate(bSJ.getM4(), "yyyy-MM-dd HH:mm:ss");  //出料时间
			String CLSJ=bSJ.getM4();
			String FL = bSJ.getM10()==null?"":bSJ.getM10().toString();	//方量
			
			msgTemplate = msgTemplate.replace("sm1", bdName);
			msgTemplate = msgTemplate.replace("sm2", bhzName);
			msgTemplate = msgTemplate.replace("sm3", JZBW);
			msgTemplate = msgTemplate.replace("sm4", QDDJ);
			msgTemplate = msgTemplate.replace("sm5", CLSJ);
			msgTemplate = msgTemplate.replace("sm7", FL);
			
			String mobilesCJ = "";
			String mobilesZJ = "";
			String mobilesGJ = "";
			String clCJ = "";
			String clZJ = "";
			String clGJ = "";

			for(TbBaoJingMainMerge m : mainList){
				String mergeName = m.getMergeName();
				Double sumSJ = 0.0d;
				Double sumSG = 0.0d;
				Double value = 0.0d;
				Double value1=0.0d;
				String diffPercent = "";
				String clInfo = "";
				if ("bht".equals(mergeName)) {
					value = Double.valueOf(bSJ.getM9());
				} else {
					String[] clNames = mergeName.split("\\+");
					for (String clName : clNames) {
						sumSJ += Util.getObjectValue(bSJ, clName);
						sumSG += Util.getObjectValue(bSG, clName);
					}
					value = Math.abs(sumSJ - sumSG);
					if (sumSG != 0)
						diffPercent = String.format("%.2f", 100*value/sumSG) + "%";
					value1=Double.parseDouble(String.format("%.2f", 100*value/sumSG));
				}

				for(TbBaoJingSubMerge s : subList){
					if(m.getMainId().equals(s.getMainId())){
						if (!Util.isEmpty(s.getUpValue()) && !Util.isEmpty(s.getDownValue()) && !Util.isEmpty(s.getMobile())) {
							Double upVal = 0.0d;
							Double downVal = 0.0d;
							if ("bht".equals(mergeName)) {
								upVal = Double.valueOf(s.getUpValue());
								downVal = Double.valueOf(s.getDownValue());
							} else {
								upVal = Double.valueOf(s.getUpValue()); //* sumSG;
								downVal = Double.valueOf(s.getDownValue()) * sumSG;
							}
							if ("0".equals(s.getSendType())) {
								System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1> upVal*100) {
									if (!mobilesCJ.contains(s.getMobile())) {
										mobilesCJ = mobilesCJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clCJ = clCJ + clInfo + "；";
								}
							} else if ("1".equals(s.getSendType())) {
								System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1 > upVal*100) {
									if (!mobilesZJ.contains(s.getMobile())) {
										mobilesZJ = mobilesZJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clZJ = clZJ + clInfo + "；";
								}
							} else if ("2".equals(s.getSendType())) {
								System.out.println(value1+">"+upVal*100+"___>"+(value1>upVal*100));
								if (value1 > upVal*100) {
									if (!mobilesGJ.contains(s.getMobile())) {
										mobilesGJ = mobilesGJ + s.getMobile() + ",";
									}
									clInfo = "[" + ConstantUtil.mergeColsMap.get(bdCode + "-" + bhzCode + "-" + mergeName).getMergeColsName() + "--实际：" + String.format("%.2f", sumSJ) + "；施工：" + String.format("%.2f", sumSG) + "，误差百分比为：" + diffPercent + "]";
									clGJ = clGJ + clInfo + "；";
								}
							}
						}
					}
				}
			}
			
			if (mobilesCJ.length() > 0 && clCJ.length() > 0) {
				mobilesCJ = Util.uniqueStringArr(mobilesCJ.split(","));
				clCJ = clCJ.substring(0, clCJ.length() - 1);
				
				String msgCJ = msgTemplate;
				msgCJ = msgCJ.replace("sm6", clCJ);
				sendMessageMerge2(bdCode,"", mobilesCJ, msgCJ, "0", bSJ.getGroupId(),"");
			}
			if (mobilesZJ.length() > 0 && clZJ.length() > 0) {
				mobilesZJ = Util.uniqueStringArr(mobilesZJ.split(","));
				clZJ = clZJ.substring(0, clZJ.length() - 1);
				
				String msgZJ = msgTemplate;
				msgZJ = msgZJ.replace("sm6", clZJ);
				sendMessageMerge2(bdCode,"", mobilesZJ, msgZJ, "1", bSJ.getGroupId(),"");
			}
			if (mobilesGJ.length() > 0 && clGJ.length() > 0) {
				mobilesGJ = Util.uniqueStringArr(mobilesGJ.split(","));
				clGJ = clGJ.substring(0, clGJ.length() - 1);
				
				String msgGJ = msgTemplate;
				msgGJ = msgGJ.replace("sm6", clGJ);
				sendMessageMerge2(bdCode,"", mobilesGJ, msgGJ, "2", bSJ.getGroupId(),"");
			}			
		}
	}
	
	public void sendMessage(TbBaoJingSub s, String clName, String msgContent, String value) throws Exception {
		PropertiesRW rw = this.getConfigFileValue();
		String sn = rw.readValue("sn");
		String password = rw.readValue("password");
		String companyName = rw.readValue("companyName");
		if(Util.isEmpty(sn) || Util.isEmpty(password) || Util.isEmpty(companyName))
			return;
		if(Util.isEmpty(clName) || Util.isEmpty(msgContent))
			return;
		if(Util.isEmpty(s.getMobile()))
			return;
		if(Util.isEmpty(value))
			return;
		if(Util.isEmpty(s.getUpValue()) || Util.isEmpty(s.getDownValue()))
			return;
		Double val = Double.valueOf(value);
		Double upVal = Double.valueOf(s.getUpValue());
		Double downVal = Double.valueOf(s.getDownValue());
		//条件成立不报警
		if(val<=upVal && val>=downVal)
			return;
		//非成立报警
		String mobile = s.getMobile();
		//替换短信内容有-C的替换为比较值
		msgContent = msgContent.replace("-C", value);
		if (!msgContent.contains(companyName))
			msgContent = "【经纬万达】"+msgContent;
		//增加短信发送记录
		insertSendMsgLog(s.getBdCode(), mobile, msgContent, "1");
		//调用短信接口
		//Client client = new Client(sn, password);
		//String result = client.mt(mobile, msgContent, "", "", "");
		AxisSendMsg as = new AxisSendMsg("320102","n8r74k2k");
		String result = as.mt(mobile, msgContent);
		System.out.println("发送手机:" + mobile + "发送内容:" + msgContent + "发送结果:" + result);
	}
	
	public void sendMessageMerge(TbBaoJingSubMerge s, String mergeName, String msgContent, String value, 
			Double sumSJ, Double bSG, String bjType, String groupId) throws Exception {
		if(Util.isEmpty(mergeName) || Util.isEmpty(msgContent))
			return;
		if(Util.isEmpty(s.getMobile()))
			return;
		if(Util.isEmpty(value))
			return;
		if(Util.isEmpty(s.getUpValue()) || Util.isEmpty(s.getDownValue()))
			return;
		
		PropertiesRW rw = this.getConfigFileValue();
		String companyName = rw.readValue("companyName");
		String sn = rw.readValue("sn");
		String password = rw.readValue("password");
		if(Util.isEmpty(companyName) || Util.isEmpty(sn) || Util.isEmpty(password))
			return;
		
		Double val = Double.valueOf(value);
		Double upVal = 0.0d;
		Double downVal = 0.0d;
		if ("bht".equals(mergeName)) {
			upVal = Double.valueOf(s.getUpValue());
			downVal = Double.valueOf(s.getDownValue());
		} else {
			upVal = Double.valueOf(s.getUpValue()) * bSG;
			downVal = Double.valueOf(s.getDownValue()) * bSG;			
		}
		//条件成立不报警
		if(val <= upVal && val >= downVal)
			return;		
		//非成立报警
		String mobile = s.getMobile();
		//替换短信内容有-C的替换为比较值
		if ("bht".equals(mergeName)) {
			msgContent = msgContent.replace("-C", value);
		} else {
			msgContent = msgContent.replace("-C", String.valueOf(sumSJ));
		}
		
		if (!msgContent.contains(companyName))
			msgContent = "【经纬万达】"+msgContent;
		//增加短信发送记录
		insertSendMsgLog(s.getBdCode(), mobile, msgContent, "1");
		//更新报警类型
		tbBaseDao.updateTbBase("update TbBase set bjType='"+bjType+"' where groupId='"+groupId+"'");
		//调用短信接口
		//Client client = new Client(sn, password);
		//String result = client.mt(mobile, msgContent, "", "", "");
		AxisSendMsg as = new AxisSendMsg("320102","n8r74k2k");
		String result = as.mt(mobile, msgContent);
		System.out.println("发送手机:" + mobile + "发送内容:" + msgContent + "发送结果:" + result);		
	}
	
	public void sendMessageMerge2(String bdCode,String bhzCode,String mobiles, String msgContent,String bjType,String groupId,String bhzCarOrder) throws Exception {
		PropertiesRW rw = this.getConfigFileValue();
		String sn = rw.readValue("sn");
		String password = rw.readValue("password");
		String companyName = rw.readValue("companyName");
		//String msgBaoJingType = rw.readValue("msgBaoJingType");
		if(Util.isEmpty(sn) || Util.isEmpty(password) || Util.isEmpty(companyName))
			return;
		if(Util.isEmpty(mobiles))
			return;
		if (!msgContent.contains(companyName))
			msgContent = "【经纬万达】"+msgContent;
		/*//调用短信接口
		Client client = new Client(sn, password);
		//判断发送短信条数和短信余额作对比
		String[] arrMobiles = mobiles.split(",");
		String balance = client.getBalance();
		if(!Util.isEmpty(balance)){
			Integer num = Integer.parseInt(balance);
			//发送数量大于余额数量,不允许发送
			if(arrMobiles.length>num){
				//增加短信发送记录
				insertSendMsgLog(bdCode, mobiles, "账户余额不足!!!", "0");
			}else{
				//增加短信发送记录
				insertSendMsgLog(bdCode, mobiles, msgContent, "1");
				//删除及新增报警类型
				tbMessgeSendTypeDao.deleteByHql("delete from TbMessgeSendType where groupId='"+groupId+"' and bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'");
				TbMessgeSendType t = new TbMessgeSendType();
				t.setBdCode(bdCode);
				t.setBhzCode(bhzCode);
				t.setGroupId(groupId);
				t.setBhzCarOrder(bhzCarOrder);
				t.setSendType(bjType);
				tbMessgeSendTypeDao.insert(t);
				//发送短信
				String result = client.mt(mobiles, msgContent, "", "", "");
				System.out.println("发送手机:" + mobiles + "发送内容:" + msgContent + "发送结果:" + result);
			}
		}*/
		//增加短信发送记录
		insertSendMsgLog(bdCode, mobiles, msgContent, "1");
		//删除及新增报警类型
		tbMessgeSendTypeDao.deleteByHql("delete from TbMessgeSendType where groupId='"+groupId+"' and bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'");
		TbMessgeSendType t = new TbMessgeSendType();
		t.setBdCode(bdCode);
		t.setBhzCode(bhzCode);
		t.setGroupId(groupId);
		t.setBhzCarOrder(bhzCarOrder);
		t.setSendType(bjType);
		tbMessgeSendTypeDao.insert(t);
		//发送短信
		//String result = client.mt(mobiles, msgContent, "", "", "");
		AxisSendMsg as = new AxisSendMsg("320102","n8r74k2k");
		String result = as.mt(mobiles, msgContent);
		System.out.println("发送手机:" + mobiles + "发送内容:" + msgContent + "发送结果:" + result);
	}
	
	public Page queryMsgLog(Page page,String bdCode,String msgContent,String mobile,String sdate,String edate)
		throws Exception{
		if(page==null)
			page = new Page();
		StringBuffer sql = new StringBuffer("TbSendMsgLog a where 1=1");
		if(!Util.isEmpty(bdCode))
			sql.append(" and a.bdCode='"+bdCode+"'");
		if(!Util.isEmpty(msgContent))
			sql.append(" and a.msgContent like '%"+msgContent+"%'");
		if(!Util.isEmpty(mobile))
			sql.append(" and a.mobile like '%"+mobile+"%'");
		if(!Util.isEmpty(sdate))
			sql.append(" and a.sendDate>='"+sdate+"'");
		if(!Util.isEmpty(edate))
			sql.append(" and a.sendDate<='"+edate+"'");
		page.setOrderBy("sendDate desc");
		page.setObjName(sql.toString());
		return pageDao.queryObjList(page);
	}
	
	public void insertSendMsgLog(String bdCode,String mobiles,String msgContent,String sendFlag)throws Exception{
		TbSendMsgLog msgLog = new TbSendMsgLog();
		msgLog.setBdCode(bdCode);
		msgLog.setMobile(mobiles);
		msgLog.setMsgContent(msgContent);
		msgLog.setSendDate(new Date());
		msgLog.setSendFlag(sendFlag);
		tbSendMsgLogDao.insert(msgLog);
	}
	
	public boolean testSendMessage(String mobile,String content)throws Exception{
		if(Util.isEmpty(mobile) || Util.isEmpty(content))
			return false;
		//调用短信接口
		PropertiesRW rw = this.getConfigFileValue();
		String sn = rw.readValue("sn");
		String password = rw.readValue("password");
		String companyName = rw.readValue("companyName");
		Client client = new Client(sn,password);
		String result = client.mt(mobile, content+companyName, "", "", "");
		System.out.println("发送手机:"+mobile+"发送内容:"+content+"发送结果:"+result);
		if("".equals(result))
			return false;
		else
			return true;
	}
	
	//各配合比乘以方量
	public TbBase getDataMulFl(TbBase t,Double fl)throws Exception{
		if(fl==null){
			//fl = 0d;
			return t;
		}
		String a1 = Util.isEmpty(t.getA1())?"0":t.getA1();
		Double v1 = Double.valueOf(a1)*fl;
		t.setA1(v1.toString());
		
		String a2 = Util.isEmpty(t.getA2())?"0":t.getA2();
		Double v2 = Double.valueOf(a2)*fl;
		t.setA2(v2.toString());
		
		String a3 = Util.isEmpty(t.getA3())?"0":t.getA3();
		Double v3 = Double.valueOf(a3)*fl;
		t.setA3(v3.toString());
		
		String a4 = Util.isEmpty(t.getA4())?"0":t.getA4();
		Double v4 = Double.valueOf(a4)*fl;
		t.setA4(v4.toString());
		
		String a5 = Util.isEmpty(t.getA5())?"0":t.getA5();
		Double v5 = Double.valueOf(a5)*fl;
		t.setA5(v5.toString());
		
		String a6 = Util.isEmpty(t.getA6())?"0":t.getA6();
		Double v6 = Double.valueOf(a6)*fl;
		t.setA6(v6.toString());
		
		String a7 = Util.isEmpty(t.getA7())?"0":t.getA7();
		Double v7 = Double.valueOf(a7)*fl;
		t.setA7(v7.toString());
		
		String a8 = Util.isEmpty(t.getA8())?"0":t.getA8();
		Double v8 = Double.valueOf(a8)*fl;
		t.setA8(v8.toString());
		
		String a9 = Util.isEmpty(t.getA9())?"0":t.getA9();
		Double v9 = Double.valueOf(a9)*fl;
		t.setA9(v9.toString());
		
		String a10 = Util.isEmpty(t.getA10())?"0":t.getA10();
		Double v10 = Double.valueOf(a10)*fl;
		t.setA10(v10.toString());
		
		String a11 = Util.isEmpty(t.getA11())?"0":t.getA11();
		Double v11 = Double.valueOf(a11)*fl;
		t.setA11(v11.toString());
		
		String a12 = Util.isEmpty(t.getA12())?"0":t.getA12();
		Double v12 = Double.valueOf(a12)*fl;
		t.setA12(v12.toString());
		
		String a13 = Util.isEmpty(t.getA13())?"0":t.getA13();
		Double v13 = Double.valueOf(a13)*fl;
		t.setA13(v13.toString());
		
		String a14 = Util.isEmpty(t.getA14())?"0":t.getA14();
		Double v14 = Double.valueOf(a14)*fl;
		t.setA14(v14.toString());
		
		String a15 = Util.isEmpty(t.getA15())?"0":t.getA15();
		Double v15 = Double.valueOf(a15)*fl;
		t.setA15(v15.toString());
		
		String a16 = Util.isEmpty(t.getA16())?"0":t.getA16();
		Double v16 = Double.valueOf(a16)*fl;
		t.setA16(v16.toString());
		
		String a17 = Util.isEmpty(t.getA17())?"0":t.getA17();
		Double v17 = Double.valueOf(a17)*fl;
		t.setA17(v17.toString());
		
		String a18 = Util.isEmpty(t.getA18())?"0":t.getA18();
		Double v18 = Double.valueOf(a18)*fl;
		t.setA18(v18.toString());
		
		String a19 = Util.isEmpty(t.getA19())?"0":t.getA19();
		Double v19 = Double.valueOf(a19)*fl;
		t.setA19(v19.toString());
		
		String a20 = Util.isEmpty(t.getA20())?"0":t.getA20();
		Double v20 = Double.valueOf(a20)*fl;
		t.setA20(v20.toString());
		return t;
	}


	public Double getQtBybhzCarOrder(String bdCode,String bhzCode,String bhzCarOrder) throws Exception {
		String sql="select bhzCarOrder,sum(m10) as qt from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"' and bhzCarOrder='"+bhzCarOrder+"' and m1='1' group by bhzCarOrder";
		List list = tbBaseDao.queryBySql(sql);
		if(list!=null && list.size()>0){
			 Object[] objects= (Object[])list.get(0);
			 if(objects!=null && objects.length>0){
				 Object obj = objects[1];
				 if(obj!=null && !Util.isEmpty(obj.toString())){
					 return Double.valueOf(obj.toString());
				 }
				 return null;
			 }
			 return null;
		}
		return null;
	}

	public String getMaxGroupId() throws Exception {
		String hql=" select max(groupId) from TbBase";
		return tbBaseDao.getMaxGroupId(hql);
	}

	public Object[] getMinAndMaxTime(String bdCode,String bhzCode,String bhzCarOrder) throws Exception {
		String hql="select min(m4),max(m4) from TbBase where m2='"+bdCode+"' and m6='"+bhzCode+"' and  bhzCarOrder='"+bhzCarOrder+"'";
		List list=tbBaseDao.getMinAndMaxTime(hql);
		return (Object[]) list.get(0);
	}
	
	public Map<String,String> getBjType(String bdCode,String bhzCode,String type)throws Exception{
		Map<String,String> result = new HashMap<String,String>();
		String hql = "from TbMessgeSendType where bdCode='"+bdCode+"' and bhzCode='"+bhzCode+"'";
		List<TbMessgeSendType> list = tbMessgeSendTypeDao.queryByHql(hql);
		for(TbMessgeSendType t:list){
			String key = t.getGroupId()+"-"+t.getBdCode()+"-"+t.getBhzCode();
			if(type.equals("1")){
				key = t.getGroupId()+"-"+t.getBdCode()+"-"+t.getBhzCode();
			}else if(type.equals("2")){
				key = t.getBhzCarOrder()+"-"+t.getBdCode()+"-"+t.getBhzCode();
			}
			result.put(key, t.getSendType());
		}
		return result;
	}
	
	public PropertiesRW getConfigFileValue(){
		PropertiesRW rw = new PropertiesRW(ConstantUtil.rootPath + "WEB-INF/classes/config.properties");
		return rw;
	}
}
