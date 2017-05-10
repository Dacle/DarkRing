package cn.driver.resources.internet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Down implements Runnable{
	/**
	 * 下载地址
	 */
	private String url1;
	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 文件类型
	 */
	private String type;

	/**
	 * 下载进程的构造函数
	 * @param url1 下载地址
	 * @param name 文件名称
	 * @param path 文件路径
	 * @param type 文件类型
	 */
	public Down(String url1,String name,String path,String type){
		this.url1 = url1;
		this.path = path;
		this.name = name;
		this.type = type;
		
	}
	
	
	public  void down(){
		try{
			URL url = new URL(url1);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setDoInput(true);
		    conn.setRequestMethod("GET");
		    if (!path.endsWith("\\"))
		    	path += "\\";
		    // 根据内容类型获取扩展名
		   /* String  contentType=conn.getHeaderField("Content-Type");
		    System.out.println(contentType);*/
		    String   filePath = path + name +"."+type;
			 System.out.println("下载到"+filePath);
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[1024];
			int size = 0;
			while ((size = bis.read(buf)) != -1){
			    fos.write(buf, 0, size);
			    System.out.println(size);
			}
			fos.close();
			bis.close();
			 System.out.println("下载结束");
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
