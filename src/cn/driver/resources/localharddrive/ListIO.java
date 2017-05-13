package cn.driver.resources.localharddrive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ListIO {
	
	String listPath;
	
	public ListIO(JSONObject object){
		listPath = object.getString("listPath");
	}
	
	public ListIO(){
		
	}
	
	public JSONArray getMusicList(JSONObject object){
		JSONArray ja = new JSONArray();
		File file = new File(object.getString("listPath"));
		BufferedReader reader = null;
		String temp = null;
		String jarray = null;
		if(file.exists()){
		try {
			reader = new BufferedReader(new FileReader(file));
			while((temp = reader.readLine())!=null)
				jarray = jarray+temp;
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		ja.element(jarray);
		System.out.println(ja.toString());
		return ja;
	}
	/**
	 * 从本地获取歌曲列表的列表
	 * @return
	 */
	public JSONArray getLists(){
		JSONArray ja = new JSONArray();
		BufferedReader reader = null;
		String temp = null;
		String jarray = null;
		try {
			reader = new BufferedReader(new FileReader("E:\\Music\\lists.data"));
			while((temp = reader.readLine())!=null)
				jarray = jarray+temp;
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ja.element(jarray);
		System.out.println(ja.toString());
		return ja;
	}
	/**
	 * 更新音乐列表
	 * @param ja 音乐列表信息
	 */
	public void updateMusicList(JSONArray ja,JSONObject jo){
		BufferedWriter writer = null;
		String str = ja.toString();
		try {
			writer = new BufferedWriter(new FileWriter(jo.getString("listPath")));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新音乐列表的列表
	 * @param ja 音乐列表信息
	 */
	public void updateLists(JSONArray ja){
		BufferedWriter writer = null;
		String str = ja.toString();
		try {
			writer = new BufferedWriter(new FileWriter("E:\\Music\\lists.data"));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
