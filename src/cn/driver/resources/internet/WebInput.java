package cn.driver.resources.internet;

import java.io.*;
import java.net.*;

import cn.gui.MainView;

public class WebInput {
	Socket socket = null;
	InputStream is = null;
    OutputStream os = null;
    public void getConnect(){
    	String url="http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s="+MainView.searchField.getText()+"&type=1&offset=0&total=true&limit=1";
    	int port=80;
    	try {
			socket= new Socket(url,port);
			os=socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
