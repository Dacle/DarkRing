package cn.gui.musicList;

import java.awt.BorderLayout;

import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.driver.model.List;
import cn.gui.musicEntry.LocalMusicEntry;
/**
 * �̳�JList�࣬��Ϊ���ֲ����б���߸赥����������mainResult,ΪMODAL_LAYER
 * @author Dacle
 * @since 2017-5-12
 * @modify by Dacle
 *
 */
public class LocalMusicList extends JList<JPanel>{

	private static final long serialVersionUID = 1L;
	private List list;
	
	public LocalMusicList(int listId,String listName){
		list = new List(listId,listName);
		initSongList();
	}
	private void initSongList(){
		LocalMusicEntry musicEntry=null;
		for(int i=0;i<list.getListSize();i++){
			musicEntry = new LocalMusicEntry(list.getJSONObject(i));
			this.add(musicEntry,JLayeredPane.POPUP_LAYER);
			musicEntry.setOpaque(false);
			musicEntry.setBounds(0, (i+1)*20, 310, 20);
			musicEntry.setLayout(new BorderLayout());
		}
	}
}
