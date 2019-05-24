package webServer.lzz463011;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

//创建线程类，单独开线程去处理用户的http请求
public class RequestThread extends Thread{

	private Socket socket;//声明私有成员变量—socket(用于该线程处理socket的对接) 
	private HashMap<String, LzzServlet> lzzTomcatMap;//声明私有成员变量—HashMap(获取最初的“全局”HashMap) 
	private OutputStream out; //声明私有成员变量—out(输出流) 
	
	//将所需的变量值对应的赋值给该线程内的变量，用于后续处理
	public RequestThread(Socket socket,HashMap<String, LzzServlet> lzzTomcatMap) {
		this.socket = socket;
		this.lzzTomcatMap = lzzTomcatMap;
		try {
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//线程处理主函数，但是会创建对象，通过对象调用方法去处理请求，以及响应
	public void process(Socket socket,HashMap<String, LzzServlet> lzzTomcatMap) throws Exception {
		LzzRequest lzzRequest = new LzzRequest(socket, lzzTomcatMap);
		LzzResponse lzzResponse = new LzzResponse(out);	
		//看HashMap中是否有该对应关系，没有则直接返回错误信息，有则响应
		if (lzzTomcatMap.containsKey(lzzRequest.getUrl())) {
			lzzTomcatMap.get(lzzRequest.getUrl()).service(lzzRequest, lzzResponse);
		} else {
			lzzResponse.write("404 Not Found");
		}
		socket.close();
	}

	//重写run,调用process函数
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			process(socket,lzzTomcatMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
