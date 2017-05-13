package cn.driver.resources.internet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 网络连接类
 * @author Dacle
 * @since 2017-5-10
 */
public class HttpUtil {

	/*
	 * 通过关键词，获取列表
	 * String keywords
	 */
	public JSONArray searchMusicInfoList(String keywords){
		//		String url="http://mp3.baidu.com/dev/api/?tn=getinfo&ct=0&word=%E6%B5%81%E6%B5%AA%E8%AE%B0&ie=utf-8&format=json";
		  String body = null;
		StringBuffer sb = new StringBuffer();
		sb.append("http://s.music.163.com/search/get/?type=1&limit=9&s=");
		
		System.out.println(keywords+"  GetBaiDuMp3");
		
		try {
			sb.append(URLEncoder.encode(keywords, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sb.append("&sub=false");
		//System.out.println(sb.toString());
		CloseableHttpClient h = HttpClients.createDefault();
		HttpPost get=new HttpPost(sb.toString());
		
		System.out.println(keywords+"  httpGet");
		
		CloseableHttpResponse response=null;
		try {
			response = h.execute(get);
			HttpEntity output = response.getEntity();
			body = EntityUtils.toString(output, Consts.UTF_8);
			  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.close();
				h.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		//对字符串进行截取，以适应JSON.parseArray的格式
		body=body.substring(body.indexOf("ongs\":")+6, body.lastIndexOf("},\"co"));
		JSONArray musicInfoList = JSONArray.fromObject(body);
		System.out.println(musicInfoList.toString());
		return musicInfoList;
	}
	
	/**
	 * 通过歌曲id 获取歌曲下载地址
	 * @param id
	 * @param w 音乐的音质，1代表128  2代表320
	 */
	public static  void GetDownloadMp3Url(String  id,int w){
		//		String url="http://mp3.baidu.com/dev/api/?tn=getinfo&ct=0&word=%E6%B5%81%E6%B5%AA%E8%AE%B0&ie=utf-8&format=json";
		  String body = null;
		StringBuffer sb=new StringBuffer();
		sb.append("http://music.163.com/api/search/get/web?csrf_token=&hlpretag=&hlposttag=&s=");
		sb.append(id);
		
		if(w==2){
			sb.append("&");
			sb.append("type=1&offset=0&total=true&limit=2");
		}
		
		System.out.println(id);
		System.out.println(sb.toString());
		
		CloseableHttpClient h = HttpClients.createDefault();
		HttpGet get=new HttpGet(sb.toString());
		CloseableHttpResponse response=null;
		try {
	
		response = h.execute(get);
			  HttpEntity output = response.getEntity();
			  body = EntityUtils.toString(output, Consts.UTF_8);			  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.close();
				h.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		System.out.println(body);
		
		JSONObject a= JSONObject.fromObject(body);
		if(a.getInt("errorCode")==22000){
			JSONObject b=(JSONObject) a.get("data");
			JSONArray c= b.getJSONArray("songList"); //可以在这一行下断点，debug一下看c里面的内容

			for (int i = 0; i <c.size(); i++) {
				String name=c.getJSONObject((i)).getString("songName");
				String downloadurl=c.getJSONObject((i)).getString("songLink");
				String songlrc=c.getJSONObject((i)).getString("lrcLink");
				System.out.println("歌曲名称："+name+"   长的下载地址: "+downloadurl);
				try {
					
					String s=downloadurl.substring(0,98);
					System.out.println("短的真实下载地址："+s);
//					Down down=new Down(downloadurl.substring(0,98), name,"d:\\1\\");
//					down.down();
				//	Down.down(s, name, "d:\\1\\");   //下载文件
					ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
					Down down=new Down(s, name,"E:\\Music\\","mp3"); //下载歌曲到E盘
					fixedThreadPool.execute(down);
					if(!"".equals(songlrc) && null!=songlrc){
						StringBuffer lrc=new StringBuffer();
						lrc.append("http://ting.baidu.com");
						lrc.append(songlrc);
						System.out.println("歌词:"+lrc.toString());
						Down downlrc=new Down(lrc.toString(), name,"e:\\1\\","lrc"); //下载歌词到D盘 lrc代表歌词 mp3代表音乐
						fixedThreadPool.execute(downlrc);
					}else{
						System.out.println("该歌曲没有歌词");
					}
					
					fixedThreadPool.shutdown();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("这条记录出错了"+a);
		}	
	}
	
	public static  void getMp3InfoList(JSONObject  object,int w){
		
	}
	
	public void downMp3(String musicName) {
		
		JSONArray jsonlist=searchMusicInfoList(musicName);
		for(int i=0;i<jsonlist.size();i++){
			JSONObject object = jsonlist.getJSONObject(i);
			System.out.println(musicName+"  GetDownloadMp3Url "+object.getString("name")+"  object  "+object.toString());
				
			GetDownloadMp3Url(object.getString("song_id"),2);

		}
		
	}
}
