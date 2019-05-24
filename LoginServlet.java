package webServer.lzz463011;

public class LoginServlet extends LzzServlet {
	//从父类中得知改用doGet还是doPost，下同，doGet就继续后面的操作
	@Override
    public void doGet(LzzRequest request, LzzResponse response) {
        try {
            response.readFile(request.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	//自己没写用doPost方法的html，所以这个方法用不到，但是也写出来，完整一点，强迫症
	//随便试doPost的html请求也不行，配置文件里没有这个文件就直接在上一步返回错误信息了
    @Override
    public void doPost(LzzRequest request, LzzResponse response) {
        try {
            response.write("用户发出http请求时使用了doPost方法提交表单参数！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
