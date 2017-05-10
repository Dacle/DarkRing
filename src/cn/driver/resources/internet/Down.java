package cn.driver.resources.internet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Down implements Runnable{

	private String url1;
	private String name;
	private String path;
	private String type;


	public Down(String url1,String name,String path,String type){
		this.url1=url1;
		this.path=path;
		this.name=name;
		this.type=type;
		
	}
	
	
	public  void down(){
		
		try{
			URL url = new URL(url1);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setDoInput(true);
		    conn.setRequestMethod("GET");
		    if (!path.endsWith("/"))
		    	path += "/";
		    // 根据内容类型获取扩展名
		    // String  contentType=conn.getHeaderField("Content-Type");
		    // System.out.println(contentType);
		    //String s[]=  contentType.split("/");
		    String   filePath = path + name +"."+type;
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
			    fos.write(buf, 0, size);
			fos.close();
			bis.close();
			conn.disconnect();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void run() {
		down();
	}
}
