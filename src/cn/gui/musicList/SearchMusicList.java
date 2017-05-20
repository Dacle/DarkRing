package cn.gui.musicList;

import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.gui.musicEntry.SearchMusicEntry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 继承JList类，作为搜索结果列表，层数高于mainResult,为MODAL_LAYER
 * @author Dacle
 * @since 2017-5-11
 * @modify by Dacle
 *
 */
public class SearchMusicList extends JList<JPanel>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JSONArray musicInfoList;
	
	/**
	 * 构造函数
	 * @param ja 私有json对象音乐信息列表
	 */
	public SearchMusicList(JSONArray ja){
		this.musicInfoList=ja;
		initList();
	}
	
	public SearchMusicList(){
		initList();
	}
	
	private void initList(){
		SearchMusicEntry musicEntry=null;
		for(int i=0;i<musicInfoList.size();i++){
			JSONObject jo = musicInfoList.getJSONObject(i);
			String artistTemp = jo.getString("artists");
			artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
			jo.remove("artists");
			jo.put("artists", artistTemp);
			musicEntry = new SearchMusicEntry(jo);
			this.add(musicEntry,JLayeredPane.POPUP_LAYER);
			musicEntry.setOpaque(false);
			musicEntry.setBounds(0, (i+1)*20, 310, 20);
			musicEntry.setLayout(new BorderLayout());
		}
	}
	
	public void shouList(){
	}
}
