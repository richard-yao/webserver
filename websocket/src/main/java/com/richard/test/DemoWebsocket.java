package com.richard.test;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date Mar 23, 2017 2:41:50 PM
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class DemoWebsocket {

	private static int onlineCount = 0;

	private static CopyOnWriteArraySet<DemoWebsocket> webSocketSet = new CopyOnWriteArraySet<DemoWebsocket>();

	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		addOnlineCount();
		try {
			sendMessage("Hello guys!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		// 群发消息
		for (DemoWebsocket item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		DemoWebsocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		DemoWebsocket.onlineCount--;
	}
}
