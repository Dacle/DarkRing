package cn.controller.listCtrl;

import javax.swing.JLayeredPane;

import cn.gui.MainView;
import cn.gui.MusicEntry;
import net.sf.json.JSONArray;

public class SearchListCtrl{
	private JSONArray musicInfoList;
	public  JLayeredPane layeredPane; 
	/**
	 * 构造函数
	 * @param ja 私有json对象音乐信息列表
	 */
	public SearchListCtrl(JSONArray ja){
		this.musicInfoList=ja;
	}
	public SearchListCtrl(){}
	
	
	/*public void dealJSONArray(){
		for(int i=0;i<musicInfoList.size();i++){
			JSONObject temp = musicInfoList.getJSONObject(i);
			JSONArray artists = JSONArray.fromObject(temp.getString("artists"));
			JSONArray album = JSONArray.fromObject(temp.getString("album"));
			musicInfoList.getJSONObject(i).remove("artists");
			musicInfoList.getJSONObject(i).remove("album");
			musicInfoList.getJSONObject(i).accumulate("artists", artists);
			musicInfoList.getJSONObject(i).accumulate("album", album);
		}
	}*/
	
	public void dealList(){
		System.out.println("总共1"+musicInfoList.toString());
		//dealJSONArray();
		int x = MainView.mainResult.getX();
		int y = MainView.mainResult.getY();
		MainView.mainResult.removeAll();
		MusicEntry musicEntry=null;
		int[] a = new int[2]; 
		for(int i=0;i<musicInfoList.size();i++){
			a[0]=x;
			a[1]=y+i*20;
			musicEntry = new MusicEntry(musicInfoList.getJSONObject(i),a);
			MainView.mainResult.add(musicEntry,JLayeredPane.MODAL_LAYER);
			musicEntry.setBounds(0, i*20, 310, 20);
		}
	}
	public void shouList(){
	}
}
