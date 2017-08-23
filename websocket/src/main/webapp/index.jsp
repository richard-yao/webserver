<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Tomcat WebSocket Chat</title>
<script>
	var clientId = "1234567";
	var ws = new WebSocket("ws://10.12.22.201:8092/websocket/websocket/" + clientId);
	ws.onopen = function() {
	};
	ws.onmessage = function(message) {
		document.getElementById("chatlog").textContent += message.data + "\n";
	};
	function postToServer() {
		ws.send(document.getElementById("msg").value);
		document.getElementById("msg").value = "";
	}
	function closeConnect() {
		ws.close();
	}
</script>
</head>
<body>
<%
	request.getSession().setAttribute("clientName", "richardyao");
%>

<textarea id="chatlog" readonly></textarea>
<br />
<input id="msg" type="text" />
<button type="submit" id="sendButton" onClick="postToServer()">Send!</button>
<button type="submit" id="sendButton" onClick="closeConnect()">End</button>
</body>
</html>