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
		return listIO.getLists();
	}
	public JSONArray readMusicList(JSONObject o){
		return listIO.getMusicList(o);
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

	public void deleteMusicList(JSONObject temp) {
		// TODO Auto-generated method stub
		listIO.deleteMusicList(temp);
	}
}
