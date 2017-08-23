package com.richard.test;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月23日 下午3:13:12
* 注册未监听器让所有的request请求都带上HttpSession
*/
public class RequestListener implements ServletRequestListener {

	public RequestListener() {}
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		
	}
	
	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		//将所有request请求都携带上httpSession
		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
		request.getSession(); // getSession方法的含义包括： 1. Return the session associated with this Request 2. Creating one if necessary and requested.
		// 因此调用此方法会让所有的request请求都和httpSession关联起来，即便是css/js这些静态资源的请求
	}
}
