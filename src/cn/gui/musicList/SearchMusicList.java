package cn.gui.musicList;

import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.gui.musicEntry.SearchMusicEntry;
import net.sf.json.JSONArray;
/**
 * �̳�JList�࣬��Ϊ��������б���������mainResult,ΪMODAL_LAYER
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
	 * ���캯��
	 * @param ja ˽��json����������Ϣ�б�
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
			musicEntry = new SearchMusicEntry(musicInfoList.getJSONObject(i));
			this.add(musicEntry,JLayeredPane.POPUP_LAYER);
			musicEntry.setOpaque(false);
			musicEntry.setBounds(0, (i+1)*20, 310, 20);
			musicEntry.setLayout(new BorderLayout());
		}
	}
	
	public void shouList(){
	}
}
