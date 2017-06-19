package com.bhz.init;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.bhz.service.MainService;
import com.bhz.service.MessageService;
import com.bhz.util.ConstantUtil;

public class InitDataListener implements InitializingBean, ServletContextAware{
	private MessageService messageService;
	private MainService mainService;

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public void afterPropertiesSet() throws Exception {  
        //在这个方法里面写 初始化的数据也可以。  
    }  
    
    public void setServletContext(ServletContext arg0) { 
    	System.out.println("init start.........");
    	try {
    		ConstantUtil.filePath = arg0.getRealPath("/jfreeChartFile");
    		ConstantUtil.rootPath = arg0.getRealPath("/");
    		messageService.setBdCache();
    		messageService.setBhzCache();
    		messageService.setBaoJingCache();
    		messageService.setBaoJingCacheMerge();
    		messageService.setMessageUserCache();
    		mainService.setMergeColsOrderCache();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("init end.........");
    }
}
