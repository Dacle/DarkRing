package cn.gui.musicEntry;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;

import net.sf.json.JSONObject;

public class MusicEntry extends JMenuBar{

	private static final long serialVersionUID = 1L;

	protected JLabel musicName;
	protected JLabel aritst;
	protected JSONObject musicJson;
	
	public MusicEntry(JSONObject musicJson){
		this.musicName = new JLabel();
		this.aritst = new JLabel();
		this.musicJson = musicJson;
		initMusicEntry();
	}
	
	private void initMusicEntry(){
		musicName.setText(musicJson.getString("name"));
		this.add(musicName,JLayeredPane.DRAG_LAYER);
		musicName.setOpaque(false);
		
		String artistTemp = musicJson.getString("artists");
		this.add(aritst,JLayeredPane.DRAG_LAYER);
		aritst.setText(artistTemp);
		aritst.setOpaque(false);
	}
}
