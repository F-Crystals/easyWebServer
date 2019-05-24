package webServer.lzz463011;

import java.net.Socket;
import java.util.HashMap;

//处理http请求的类，用于解析http请求标头，主要为了得到http的url和请求方法
public class LzzRequest {
	String method;
	String url;

	public LzzRequest(Socket socket,HashMap<String, LzzServlet> lzzTomcatMap) throws Exception {
		byte[] buff = new byte[1024];
		String content = "";
		int len = 0;
		if ((len = socket.getInputStream().read(buff)) >0){
			content = new String(buff,0,len);
		}
		System.out.println(content);
		String line1 = content.split("\\n")[0];
		String[] arr = line1.split("\\s");
		this.method = arr[0];
		if(arr.length>2) this.url = arr[1].split("\\?")[0];
	}

	public String getUrl() {
		return this.url;
	}

	public String getMethod() {
		return this.method;
	}
	//用不到set方法
}
