package com.bhz.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

import com.bhz.pojo.TbBaoJingBase;
import com.bhz.pojo.TbBaoJingMain;
import com.bhz.pojo.TbBaoJingMainMerge;
import com.bhz.pojo.TbBaoJingPersonBase;
import com.bhz.pojo.TbBaoJingSub;
import com.bhz.pojo.TbBaoJingSubMerge;
import com.bhz.pojo.TbBd;
import com.bhz.pojo.TbBhz;
import com.bhz.pojo.TbMessageUser;
import com.bhz.pojo.TbParamSet;
import com.bhz.pojo.TdMergeColsOrder;
import com.bhz.service.BaoJingBaseService;
import com.bhz.service.BaoJingPersonBaseService;
import com.bhz.service.CountService;
import com.bhz.service.MainService;
import com.bhz.service.MessageService;
import com.bhz.util.ConstantUtil;
import com.bhz.util.PropertiesRW;
import com.bhz.util.Util;
import com.bhz.util.PageUtils.Page;
import com.bhz.util.sendMessage.Client;
import com.googlecode.jsonplugin.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class MessageAction extends ActionSupport implements ModelDriven<TbMessageUser>{
	
	private static final long serialVersionUID = 1L;
	private MainService mainService;
	private MessageService messageService;
	private CountService countService;
	private BaoJingBaseService baoJingBaseService;
	private BaoJingPersonBaseService baoJingPersonBaseService;
	private TbMessageUser messageUser = new TbMessageUser();
	private List<TbMessageUser> msgUserJson;
	private String hint;

	private Page page;
	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	public void setCountService(CountService countService) {
		this.countService = countService;
	}
	public void setBaoJingBaseService(BaoJingBaseService baoJingBaseService) {
		this.baoJingBaseService = baoJingBaseService;
	}
	public void setBaoJingPersonBaseService(BaoJingPersonBaseService baoJingPersonBaseService) {
		this.baoJingPersonBaseService = baoJingPersonBaseService;
	}
	public List<TbMessageUser> getMsgUserJson() {
		return msgUserJson;
	}
    public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getHint() {
		return hint;
	}
	
	@JSON(serialize = false)
	public String getMessageUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String realName = request.getParameter("realName");
		String telphone = request.getParameter("telphone");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			Map<String,String> bdMap = new HashMap<String,String>();
			List<TbBd> bdList = mainService.queryTbBd(request);
			page = messageService.queryPageMessageUser(page,bdCode, realName, telphone);
			for(TbBd b:bdList)
				bdMap.put(b.getBdCode(), b.getBdName());
			request.setAttribute("bCode", bdCode);
			request.setAttribute("realName", realName);
			request.setAttribute("telphone", telphone);
			request.setAttribute("bdList", bdList);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("objPage", page);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "messageuser";
	}
	
	public String addMessageUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			messageService.insertTbMessageUser(messageUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return getMessageUser();
	}
	
	public String delMessageUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			messageService.deleteTbMessageUser(messageUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return getMessageUser();
	}
	
	public String setMessage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		String phbQd = request.getParameter("phbQd");
		if (request.getAttribute("phbQd") != null) {
			phbQd = request.getAttribute("phbQd").toString();
		}
		TbBhz tbBhz = new TbBhz();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			tbBhz = mainService.getTbBhz(bhzCode);
			List<TbBd> bdList = mainService.queryTbBd(request);
			List<String> qdList = countService.getDistQiangDu();
			if(!Util.isEmpty(bdCode) && !Util.isEmpty(bhzCode)){
				if (tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())) {
					List<TdMergeColsOrder> mergeColsList = mainService.queryMergeColsByCode(bdCode, bhzCode);
					Map<String, TbBaoJingMainMerge> mainMergeMap = messageService.queryTbBaoJingMainMerge(bdCode, bhzCode, phbQd);
					Map<String, TbBaoJingSubMerge> subMergeMap = messageService.queryTbBaoJingSubMerge(bdCode, bhzCode, phbQd);					
					request.setAttribute("mergeColsList", mergeColsList);
					request.setAttribute("mainMergeMap", mainMergeMap);
					request.setAttribute("subMergeMap", subMergeMap);
				} else {
					List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
					Map<String, TbBaoJingMain> mainMap = messageService.queryTbBaoJingMain(bdCode, bhzCode, phbQd);
					Map<String, TbBaoJingSub> subMap = messageService.queryTbBaoJingSub(bdCode, bhzCode, phbQd);
					request.setAttribute("paramList", paramList);
					request.setAttribute("mainMap", mainMap);
					request.setAttribute("subMap", subMap);
				}
			
				request.setAttribute("nodata", "1");
			}
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
			request.setAttribute("phbQd", phbQd);
			request.setAttribute("qdList", qdList);
			request.setAttribute("bdList", bdList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())) {
			return "setmessageMerge";
		} else {
			return "setmessage";			
		}
	}
	
	public String setBaoJingBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			Page allBaoJingBase = messageService.getBaoJingBase(request, page);
			request.setAttribute("objPage", allBaoJingBase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setBaoJingBase";
	}
	
	public String setBaoJingPersonBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bd");
		String bhzCode = request.getParameter("bhz");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			List<TbBd> allBD = messageService.queryTbBd(request);
			Map<String, String> bdMap = messageService.getBdMap();
			Map<String, String> bhzMap = messageService.getBhzMap();
			Page allBaoJingPersonBase = messageService.getBaoJingPersonBase(request, page, bdCode, bhzCode);
			List<TbMessageUser> messageUserList = messageService.getDistinctAllMessageUser();
			
			request.setAttribute("BDs", allBD);
			request.setAttribute("objPage", allBaoJingPersonBase);
			request.setAttribute("bdMap", bdMap);
			request.setAttribute("bhzMap", bhzMap);
			request.setAttribute("messageUserList", messageUserList);
			request.setAttribute("bdCode", bdCode);
			request.setAttribute("bhzCode", bhzCode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setBaoJingPersonBase";
	}
	
	public String setMessageSendType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			PropertiesRW rw = new PropertiesRW(ConstantUtil.rootPath + "WEB-INF/classes/config.properties");
			String msgBaoJingType = rw.readValue("msgBaoJingType");
			if (Util.isEmpty(msgBaoJingType)){
				msgBaoJingType = "1";
			}
			String msgTemplate1 = rw.readValue("msgTemplate1");
			String msgTemplate2 = rw.readValue("msgTemplate2");
			request.setAttribute("msgBaoJingType", msgBaoJingType);
			request.setAttribute("msgTemplate1", msgTemplate1);
			request.setAttribute("msgTemplate2", msgTemplate2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "setMessageSendType";
	}
	
	public String toAddBaoJingBasePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toAddBaoJingBasePage";
	}
	
	public String toAddBaoJingPersonBasePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			List<TbBd> bdList = mainService.queryTbBd(request);
			
			request.setAttribute("bdList", bdList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toAddBaoJingPersonBasePage";
	}
	
	public String toUpdatePage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			String id = request.getParameter("id");
			TbBaoJingBase b = baoJingBaseService.getBaoJingBaseById(id);
			request.setAttribute("b", b);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toUpdateBaoJingBasePage";
	}
	
	public String toUpdateBaoJingPersonPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			String id = request.getParameter("id");
			TbBaoJingPersonBase p = baoJingPersonBaseService.getBaoJingPersonBaseById(id);
			List<TbBd> bdList = mainService.queryTbBd(request);
			List<TbMessageUser> messageUserList = messageService.getDistinctAllMessageUser();
			
			request.setAttribute("p", p);
			request.setAttribute("bdList", bdList);
			request.setAttribute("messageUserList", messageUserList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "toUpdateBaoJingPersonBasePage";
	}
	
	public String addBaoJingBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String clName = request.getParameter("clName");
		String isBaojing = request.getParameter("isBaojing");
		String msgContent = request.getParameter("msgContent");
		String downValueCj = request.getParameter("downValueCj");
		String upValueCj = request.getParameter("upValueCj");
		String downValueZj = request.getParameter("downValueZj");
		String upValueZj = request.getParameter("upValueZj");
		String downValueGj = request.getParameter("downValueGj");
		String upValueGj = request.getParameter("upValueGj");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			TbBaoJingBase baoJingBase = new TbBaoJingBase(clName, isBaojing, msgContent, downValueCj, upValueCj, downValueZj, upValueZj, downValueGj, upValueGj);
			baoJingBaseService.addBaoJingBase(baoJingBase);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingBase();
	}
	
	public String addBaoJingPersonBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String userMobileCj = request.getParameter("userMobileCj");
		String userMobileZj = request.getParameter("userMobileZj");
		String userMobileGj = request.getParameter("userMobileGj");
		String mobileCJ_bdCode = request.getParameter("mobileCJ_bdCode");		
		String mobileZJ_bdCode = request.getParameter("mobileZJ_bdCode");		
		String mobileGJ_bdCode = request.getParameter("mobileGJ_bdCode");	
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			TbBaoJingPersonBase baoJingPersonBase = new TbBaoJingPersonBase(bdCode, bhzCode, userMobileCj, userMobileZj, userMobileGj, mobileCJ_bdCode, mobileZJ_bdCode, mobileGJ_bdCode);
			baoJingPersonBaseService.addBaoJingPersonBase(baoJingPersonBase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingPersonBase();
	}
	
	public String saveMessageSendType() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String msgBaoJingType = request.getParameter("msgBaoJingType");
			String msgTemplate1 = request.getParameter("msgTemplate1");
			String msgTemplate2 = request.getParameter("msgTemplate2");

			PropertiesRW rw = new PropertiesRW(ConstantUtil.rootPath + "WEB-INF/classes/config.properties");
			rw.writeProperties("msgBaoJingType", msgBaoJingType);
			if (!Util.isEmpty(msgTemplate1)){
				rw.writeProperties("msgTemplate1", msgTemplate1);
			}
			if (!Util.isEmpty(msgTemplate2)){
				rw.writeProperties("msgTemplate2",msgTemplate2);
			}
			request.setAttribute("hint", "保存成功！");
		} catch (Exception e) {
			request.setAttribute("hint", "保存失败！");
			System.out.println(e.getMessage());
		}
		return setMessageSendType();
	}

	public String updateBaoJingBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String clName = request.getParameter("clName");
		String isBaojing = request.getParameter("isBaojing");
		String msgContent = request.getParameter("msgContent");
		String downValueCj = request.getParameter("downValueCj");
		String upValueCj = request.getParameter("upValueCj");
		String downValueZj = request.getParameter("downValueZj");
		String upValueZj = request.getParameter("upValueZj");
		String downValueGj = request.getParameter("downValueGj");
		String upValueGj = request.getParameter("upValueGj");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			TbBaoJingBase baoJingBase = new TbBaoJingBase(clName, isBaojing, msgContent, downValueCj, upValueCj, downValueZj, upValueZj, downValueGj, upValueGj);
			baoJingBase.setId(Integer.valueOf(id));
			baoJingBaseService.updateBaoJingBase(baoJingBase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingBase();
	}
	
	public String updateBaoJingPersonBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String userMobileCj = request.getParameter("userMobileCj");
		String userMobileZj = request.getParameter("userMobileZj");
		String userMobileGj = request.getParameter("userMobileGj");		
		String mobileCJ_bdCode = request.getParameter("mobileCJ_bdCode");		
		String mobileZJ_bdCode = request.getParameter("mobileZJ_bdCode");		
		String mobileGJ_bdCode = request.getParameter("mobileGJ_bdCode");		
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if (!Util.isUserAuthor(request, key, "2")) {
				return "noauth";
			}
			TbBaoJingPersonBase baoJingPersonBase = new TbBaoJingPersonBase(bdCode, bhzCode, userMobileCj, userMobileZj, userMobileGj, mobileCJ_bdCode, mobileZJ_bdCode, mobileGJ_bdCode);
			baoJingPersonBase.setId(Integer.valueOf(id));
			baoJingPersonBaseService.updateBaoJingPersonBase(baoJingPersonBase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingPersonBase();
	}
	
	public String deleteBaoJingPersonBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String id = request.getParameter("id");
			baoJingPersonBaseService.deleteBaoJingPersonBaseById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingPersonBase();
	}
	
	public String deleteBaoJingBase() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		try {
			TbBaoJingBase baoJingBase = baoJingBaseService.getBaoJingBaseById(id);
			baoJingBaseService.deleteBaoJingBase(baoJingBase);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return setBaoJingBase();
	}
	
	//添加报警基本信息时，检查表中是否已存在该材料名称
	public String checkClName_Add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String clName = request.getParameter("clName");
		try {
			if (baoJingBaseService.checkClName_Add(clName)) {
				hint = "1";  //1表示该材料名称已存在
			} else {
				if (!mainService.checkClName(clName))
					hint = "2";  //2表示该材料名称在合并列设置里不存在
				else
					hint = "";
			}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "jsonString";
	}
	
	//添加报警人员基本信息时，根据bdCode和bhzCode检查表中是否已存在该记录
	public String checkBaoJingPersonBase_Add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		try {
			if (baoJingPersonBaseService.checkExist_Add(bdCode, bhzCode)) {
				hint = "该标段和拌合站的记录已存在！";
			} else {
				hint = "";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "jsonString";
	}

	//修改报警基本信息时，检查表中是否已存在该材料名称
	public String checkClName_Update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String clName = request.getParameter("clName");
		String id = request.getParameter("ID");
		try {
			if (baoJingBaseService.checkClName_Update(clName, id)) {
				hint = "1";  //1表示该材料名称已存在
			} else {
				if (!mainService.checkClName(clName))
					hint = "2";  //2表示该材料名称在合并列设置里不存在
				else
					hint = "";
			}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "jsonString";
	}
	
	//修改报警人员基本信息时，根据bdCode、bhzCode、ID检查表中是否已存在该记录
	public String checkBaoJingPersonBase_Update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String bhzCode = request.getParameter("bhzCode");
		String id = request.getParameter("ID");
		try {
			if (baoJingPersonBaseService.checkExist_Update(bdCode, bhzCode, id)) {
				hint = "该标段和拌合站的记录已存在！";
			} else {
				hint = "";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "jsonString";
	}
	
	public String saveMessage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String bdCode = request.getParameter("bd");
			String bhzCode = request.getParameter("bhz");
			String phbQd = request.getParameter("phbQd");
			TbBhz tbBhz = new TbBhz();
			tbBhz = mainService.getTbBhz(bhzCode);
			if(!Util.isEmpty(phbQd))
				phbQd = new String(phbQd.getBytes("ISO-8859-1"), "UTF-8");
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			if (tbBhz!=null && tbBhz.getMergeClos()!=null && "1".equals(tbBhz.getMergeClos())) {
				List<TdMergeColsOrder> mergeColsList = mainService.queryMergeColsByCode(bdCode, bhzCode);
				messageService.insertMessageSetMerge(request, bdCode, bhzCode, phbQd, mergeColsList);
			} else {
				List<TbParamSet> paramList = mainService.getParamByItem(bdCode, bhzCode);
				messageService.insertMessageSet(request, bdCode, bhzCode, phbQd, paramList);				
			}
			request.setAttribute("msg", "保存成功!");
			request.setAttribute("phbQd", phbQd);
		} catch (Exception e) {
			request.setAttribute("msg", "保存失败!");
			System.out.println(e.getMessage());
		}
		return setMessage();
	}
	
	public String queryMessageUser(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		try {
			msgUserJson = messageService.queryMessageUserByBd(bdCode, null, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "getmessageuser";
	}
	
	public String msgHistory(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String bdCode = request.getParameter("bdCode");
		String msgContent = request.getParameter("msgContent");
		String mobile = request.getParameter("mobile");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		try {
			String key = Util.getUserName(request)+"-base"+ConstantUtil.fun_Msg;
			if(!Util.isUserAuthor(request, key, "1") && !Util.isUserAuthor(request, key, "2")){
				return "noauth";
			}
			Map<String,String> map = new HashMap<String,String>();
			List<TbBd> bdList = messageService.queryTbBd(request);
			for(TbBd t : bdList){
				map.put(t.getBdCode(), t.getBdName());
			}			
			Page objpage = messageService.queryMsgLog(page, bdCode, msgContent, mobile, sdate, edate);
			Map<String,String> realNameMap = messageService.getMessageUserRealName();
			PropertiesRW rw = messageService.getConfigFileValue();
			String sn = rw.readValue("sn");
			String password = rw.readValue("password");
			//调用短信接口
			Client client = new Client(sn, password);
			String balanceNum = client.getBalance();
			request.setAttribute("map", map);
			request.setAttribute("bdList", bdList);
			request.setAttribute("objPage", objpage);
			request.setAttribute("sCD", bdCode);
			request.setAttribute("msgContent", msgContent);
			request.setAttribute("mobile", mobile);
			request.setAttribute("sdate", sdate);
			request.setAttribute("edate", edate);
			request.setAttribute("realNameMap", realNameMap);
			request.setAttribute("balanceNum", balanceNum);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "msghistory";
	}
	
	public String sendMessage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String mobile = request.getParameter("mobile");
		String content = request.getParameter("msgContent");
		try {
			System.out.println("模拟发送短信...........");
			boolean result = messageService.testSendMessage(mobile, content);
			if(result){
				request.setAttribute("msgInfo", "发送成功!");
			}else{
				request.setAttribute("msgInfo", "发送失败!");				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return msgHistory();
	}
	
	@JSON(serialize = false)
	public TbMessageUser getModel() {
		// TODO Auto-generated method stub
		return messageUser;
	}

}