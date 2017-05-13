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
	public JSONArray readLists(){
		ListIO listIO = new ListIO();
		JSONArray ja = listIO.getLists();
		return ja;
	}
	public JSONArray readMusicList(JSONObject o){
		ListIO listIO = new ListIO();
		JSONArray ja = listIO.getMusicList(o);
		return ja;
	}
	
	public void updateLists(JSONArray ja){
		ListIO listIO = new ListIO();
		listIO.updateLists(ja);
	}
	public void updateMusicList(JSONArray ja,JSONObject jo){
		ListIO listIO = new ListIO();
		listIO.updateMusicList(ja, jo);
	}
}
