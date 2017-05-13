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
 * ����������
 * @author Dacle
 * @since 2017-5-10
 */
public class HttpUtil {

	/*
	 * ͨ���ؼ��ʣ���ȡ�б�
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
		//���ַ������н�ȡ������ӦJSON.parseArray�ĸ�ʽ
		body=body.substring(body.indexOf("ongs\":")+6, body.lastIndexOf("},\"co"));
		JSONArray musicInfoList = JSONArray.fromObject(body);
		System.out.println(musicInfoList.toString());
		return musicInfoList;
	}
	
	/**
	 * ͨ������id ��ȡ�������ص�ַ
	 * @param id
	 * @param w ���ֵ����ʣ�1����128  2����320
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
			JSONArray c= b.getJSONArray("songList"); //��������һ���¶ϵ㣬debugһ�¿�c���������

			for (int i = 0; i <c.size(); i++) {
				String name=c.getJSONObject((i)).getString("songName");
				String downloadurl=c.getJSONObject((i)).getString("songLink");
				String songlrc=c.getJSONObject((i)).getString("lrcLink");
				System.out.println("�������ƣ�"+name+"   �������ص�ַ: "+downloadurl);
				try {
					
					String s=downloadurl.substring(0,98);
					System.out.println("�̵���ʵ���ص�ַ��"+s);
//					Down down=new Down(downloadurl.substring(0,98), name,"d:\\1\\");
//					down.down();
				//	Down.down(s, name, "d:\\1\\");   //�����ļ�
					ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
					Down down=new Down(s, name,"E:\\Music\\","mp3"); //���ظ�����E��
					fixedThreadPool.execute(down);
					if(!"".equals(songlrc) && null!=songlrc){
						StringBuffer lrc=new StringBuffer();
						lrc.append("http://ting.baidu.com");
						lrc.append(songlrc);
						System.out.println("���:"+lrc.toString());
						Down downlrc=new Down(lrc.toString(), name,"e:\\1\\","lrc"); //���ظ�ʵ�D�� lrc������ mp3��������
						fixedThreadPool.execute(downlrc);
					}else{
						System.out.println("�ø���û�и��");
					}
					
					fixedThreadPool.shutdown();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("������¼������"+a);
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
