package webServer.lzz463011;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LzzResponse {
	private OutputStream outs;
	//声明并初始化静态最终类字符串常量---服务器提供文件存储位置，在其他机器上运行时需要对应修改OWN_PATH的值。ComputerWeb是项目的根目录
	public static final String OWN_PATH="D:\\YourOwnPath";
	public static final String WEB_ROOT=OWN_PATH + "\\CW-WebServer\\config";
	
	
	//从函数调用的时候传入outputstream中的内容，追溯到最开始的创建线程是的socket的getOutputStream()
    public LzzResponse(OutputStream outs){
        this.outs=outs;
    }
    
    //将需要直接写入输出流的信息写入输出流，显示在html页面上
    public void write(String outString)throws Exception{
        outs.write(outString.getBytes("UTF-8"));
    }
    
    //读取文件内容，若发生错误抛出所有异常，并将异常处理交给调用者 
    public void readFile(String fileName) throws IOException {
		File file=new File(LzzResponse.WEB_ROOT+fileName);//实例化文件对象并 与实际文件建立关联 
		if (!file.exists()) { //判断文件是否存在 
			outs.write("404 Not Found".getBytes());//文件不存在则输出错误信息
			return;
		} 
		InputStream in=new FileInputStream(file);//创建文件输入流对象并打开 源文件 
		byte content[]=new byte[(int) file.length()]; //实例化字节数组         
		in.read(content); //从输入流对象中读入数据到字节数组content中
		outs.write(content); //将字节数组内容写入输出流—真正的请求文件信息 
		outs.flush(); //强制输出缓冲区数据 
		outs.close(); //关闭打印流 
		in.close(); //关闭输入流 
	}

}
