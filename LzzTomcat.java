package webServer.lzz463011;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class LzzTomcat extends Thread {

	private ServerSocket serverSocket;
	private int port;
	private HashMap<String, LzzServlet> servletMapping = new HashMap<String, LzzServlet>();
	private Properties webXml = new Properties();

	public HashMap<String, LzzServlet> getServletMapping() {
		return servletMapping;
	}
	//用不到set方法
	
	//服务器、HashMap等的初始化
	private void init() {
		// 加载webServer.properties,初始化servletMapping对象
		try {
			FileInputStream fis = new FileInputStream("config/webServer.properties");
			webXml.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//循环遍历properties，为每一个url建立映射
		for (Object k : webXml.keySet()) {
			//把每一个对象转换为string做string相关操作
			String key = k.toString();
			if (key.endsWith(".url")) {
				//将配置文件中的以.url结尾的key，去掉，再和className对应，建立一对一关系
				String servletName = key.replaceAll(".url", "");
				//获取key对应的属性，即url的值
				String url = webXml.getProperty(key);
				String className = webXml.getProperty(servletName + ".className");
				//System.out.println(className);
				LzzServlet obj = null;//创建servlet对象，将在HashMap中与key建立对应关系
				try {
					//使用反射创建类，便于运行时对外部类信息读取和创建。本计网结课项目中并没体现出反射的方法创建类的优势。
					obj = (LzzServlet) Class.forName(className).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				servletMapping.put(url, obj);
			}
		}
		//System.out.println(servletMapping.keySet());
	}
	
	//重写run
	@Override
	public void run() {
		// 1.初始化
		init();
		try {
			//满足项目要求3.允许用户对webServer的Port进行设置，在服务器运行开始时进行
			Scanner in = new Scanner(System.in);
			String webServerPort = "设置WebServer的监听端口(请避免使用系统默认占用的端口)：";
			System.out.println(webServerPort);
			this.port = in.nextInt();
			//该关的流关掉
			if(in!=null) in.close();
			serverSocket = new ServerSocket(this.port);
			//输出必要用户须知信息
			System.out.println("LzzTomcat has started and the Port you used is " + this.port);
			
			// 2.死循环等待用户请求
			while (true) {
				Socket client = serverSocket.accept();
				RequestThread rth = new RequestThread(client, servletMapping);
				rth.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LzzTomcat().run();
	}
}