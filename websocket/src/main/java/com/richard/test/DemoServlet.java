package com.richard.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date Mar 23, 2017 1:37:53 PM
*/
@SuppressWarnings("deprecation")
public class DemoServlet extends WebSocketServlet {

	private static final long serialVersionUID = 5398125388513204329L;
	
	private static List<MyMessageInbound> mmiList = new ArrayList<DemoServlet.MyMessageInbound>();

	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest arg1) {
		return new MyMessageInbound();
	}
	
	private class MyMessageInbound extends MessageInbound {
		WsOutbound myOutbound;

		@Override
		protected void onOpen(WsOutbound outbound) {
			try {
				System.out.println("Open Clinet");
				this.myOutbound = outbound;
				mmiList.add(this);
				outbound.writeTextMessage(CharBuffer.wrap("Hello guys!"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		protected void onClose(int status) {
			System.out.println("Close Client");
			mmiList.remove(this);
		}
		
		@Override
		public int getReadTimeout() {
			return 0;
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
			
		}

		@Override
		protected void onTextMessage(CharBuffer arg0) throws IOException {
			System.out.println("Accept message: " + arg0);
			for(MyMessageInbound mmib : mmiList) {
				CharBuffer buffer = CharBuffer.wrap(arg0);
				mmib.myOutbound.writeTextMessage(buffer);
				mmib.myOutbound.flush();
			}
		}
		
	}

}
