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
	JSONArray ja = new JSONArray();
	
	public ListIO(JSONObject object){
		initListIO();
		listPath = object.getString("listPath");
	}
	
	public ListIO(){
		initListIO();
	}
	
	public void initListIO(){
		FileReader fr = null;
		FileWriter fw = null;
		BufferedReader reader = null;
		String temp = null;
		String jarray = null;
		System.out.println("asdasdads");
		File f = new File("file/lists.data");
		if(!f.exists()){						//如果文件不存在，就创建一个新的
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			fr = new FileReader("file/lists.data");
			fw = new FileWriter("file/lists.data");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {									//读取文件内容
			reader = new BufferedReader(fr);
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
		if(jarray==null){						//如果文件内容为空,则添加默认内容
			JSONObject objectTemp = new JSONObject();
			JSONArray jaa = null;
			objectTemp.put("name","默认列表");
			objectTemp.put("listPath", "file/默认列表.data");
			jarray = objectTemp .toString();
			createMusicList(objectTemp);			//并且创建默认歌曲列表
			jaa= JSONArray.fromObject("["+jarray+"]");
			BufferedWriter writer = new BufferedWriter(fw);
			try {									//将默认内容添加到列表的列表
				writer.write(jaa.toString());
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ja = jaa;
		}else
			ja= JSONArray.fromObject(jarray);
	}
	
	public JSONArray getMusicList(JSONObject object){
		System.out.println("object "+object.toString());
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
				System.out.println("jarray   "+jarray);
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
		System.out.println("list内容：  "+ja.toString());
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
			System.out.println("add   "+str);
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
	public void updateLists(JSONArray jaa){
		ja = jaa;
		BufferedWriter writer = null;
		String str = ja.toString();
		try {
			writer = new BufferedWriter(new FileWriter("file/lists.data"));
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createMusicList(JSONObject object){
		BufferedWriter writer = null;
		if(new File(object.getString("listPath")).exists())
			return;
		try {
			System.out.println("create list   "+object.toString());
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
