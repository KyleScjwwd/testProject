package com.bhz.intercept;

import java.util.Map;
import com.bhz.action.LoginAction;
import com.bhz.util.Util;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionIterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			ActionContext actionContext = ActionContext.getContext();
			if (invocation.getAction() instanceof LoginAction) {  
	            return invocation.invoke();  
	        }
			Map session = actionContext.getSession();  
			Object obj = session.get("username");
			if(obj == null){
				return Action.LOGIN;
			}
			String username = obj.toString();
			if(Util.isEmpty(username)){
				return Action.LOGIN;
			}
		} catch (Exception e) {
			return Action.LOGIN;
		}
		return invocation.invoke();
	}
}
