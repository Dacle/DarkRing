package cn.controller.listCtrl;

import cn.driver.resources.localharddrive.ListIO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 中间件，避免UI直接访问底层文件
 * @author Dacle
 *
 */
public class Lists {
	
	ListIO listIO;
	
	public Lists(){
		listIO = new ListIO();
	}
	
	public JSONArray readLists(){
		JSONArray ja = listIO.getLists();
		return ja;
	}
	public JSONArray readMusicList(JSONObject o){
		JSONArray ja = listIO.getMusicList(o);
		return ja;
	}
	
	public void updateLists(JSONArray ja){
		listIO.updateLists(ja);
	}
	public void updateMusicList(JSONArray ja,JSONObject jo){
		listIO.updateMusicList(ja, jo);
	}
	public void createMusicList(JSONObject o){
		listIO.createMusicList(o);
	}
	public void renameMusicList(String old,String newName){
		listIO.renameMusicList(old,newName);
	}
}
