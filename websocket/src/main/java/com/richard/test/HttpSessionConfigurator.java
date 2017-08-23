package com.richard.test;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月23日 下午2:56:59
*/
public class HttpSessionConfigurator extends Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		HttpSession session = (HttpSession) request.getHttpSession();
		sec.getUserProperties().put(HttpSession.class.getName(), session);
	}
}
