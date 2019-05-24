package webServer.lzz463011;

public abstract class LzzServlet {
	
	//通过解析出来的html请求，service选择doGet或是doPost
	public void service(LzzRequest request, LzzResponse response)throws Exception{
        if("Get".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet (LzzRequest request, LzzResponse response);

    public abstract void doPost (LzzRequest request, LzzResponse response);

}