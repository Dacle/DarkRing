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
		String jarray = "";
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
		if(!jarray.equals(""))
			ja = JSONArray.fromObject(jarray);		
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
		jarray = jarray.substring(4);
		ja= JSONArray.fromObject(jarray);
		return ja;
	}
	
	/**
	 * 更新音乐列表
	 * @param ja 音乐列表中音乐信息
	 * @param jo 音乐列表基本信息
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
	public void createMusicList(JSONObject object){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(object.getString("listPath")));
			writer.write("[]");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray ja = getLists();
		ja.add(object);
		updateLists(ja);
	}

	public void renameMusicList(String oldname,String newname) {
		// TODO Auto-generated method stub
		JSONArray ja = getLists();
		File file = null;   //指定文件名及路径
		String rootPath = null;
		String path = null;
		JSONObject temp = null;
		for(int i=0;i<ja.size();i++){
			temp = ja.getJSONObject(i);
			if(temp.getString("name").equals(oldname)){
				path = temp.getString("listPath");
				file=new File(path);
				rootPath = file.getParent();
				temp.remove("name");
				temp.put("name", newname);
				path = rootPath+File.separator+newname+".data";
				temp.remove("listPath");
				temp.put("listPath", path);
				break;
			}
		}
		updateLists(ja);
		if(!file.exists() || file.renameTo(new File(path))){
			System.out.println("改名成功");    
		}else{
			System.out.println("改名失败"); 
		}
	}

	public void deleteMusicList(JSONObject temp) {
		// TODO Auto-generated method stub
		JSONArray ja = getLists();
		ja.remove(temp);
		updateLists(ja);
		File file = new File(temp.getString("listPath"));
		if(file.exists()){
			file.delete();
		}
		if(file.exists()){
			System.out.println("删除失败");
		}else{
			System.out.println("删除成功");
		}
	}
}
