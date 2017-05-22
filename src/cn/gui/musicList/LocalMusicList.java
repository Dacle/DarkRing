package cn.gui.musicList;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.controller.listener.MouseListener.LocalMusicEntryListener;
import cn.gui.musicEntry.LocalMusicEntry;
import net.sf.json.JSONArray;
/**
 * 继承JList类，作为音乐播放列表或者歌单，层数高于mainResult,为MODAL_LAYER
 * @author Dacle
 * @since 2017-5-12
 * @modify by Dacle
 *
 */
public class LocalMusicList extends JList<JPanel>{

	private static final long serialVersionUID = 1L;
	private JSONArray list;
	
	LocalMusicEntryListener musicEntryListener = new LocalMusicEntryListener();
	public LocalMusicList(JSONArray list){
		this.list = list;
		initSongList();
	}
	private void initSongList(){
		LocalMusicEntry musicEntry=null;
		for(int i=0;i<list.size();i++){
			musicEntry = new LocalMusicEntry(list.getJSONObject(i));
			this.add(musicEntry,JLayeredPane.POPUP_LAYER);
			musicEntry.setOpaque(false);
			musicEntry.setBounds(0, i*20, 310, 20);
			musicEntry.setLayout(new BorderLayout());
			musicEntry.addMouseListener(musicEntryListener);
			musicEntry.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					((LocalMusicEntry) e.getComponent()).setOpaque(true);
				}
				@Override
				public void focusLost(FocusEvent e) {
					((LocalMusicEntry) e.getComponent()).setOpaque(false);
				}
			});
		}
	}
}
