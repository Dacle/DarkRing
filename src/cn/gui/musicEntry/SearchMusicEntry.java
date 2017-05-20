package cn.gui.musicEntry;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.driver.resources.internet.Down;
import net.sf.json.JSONObject;
/**
 * 继承PaintJpanel类，作为搜索结果单条目对象，层数高于SearchList,为POPUP_LAYER
 * @author Dacle
 * @since 2017-5-11
 * @modify by Dacle
 *
 */
public class SearchMusicEntry extends MusicEntry{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel album;
	private JLabel play;
	private JLabel addToList;
	private JLabel download;
	
	public SearchMusicEntry(JSONObject musicJson){
		super(musicJson);
		this.album = new JLabel();
		this.play = new JLabel();
		this.addToList = new JLabel();
		this.download = new JLabel();
		initMusicEntry();
	}
	
	private void initMusicEntry(){
		
		musicName.setBounds(0,0,60,20);
		
		aritst.setBounds(60, 0, 60, 20);
		
		
		String albumTemp = musicJson.getString("album");
		this.add(album,JLayeredPane.DRAG_LAYER);
		albumTemp=albumTemp.substring(albumTemp.indexOf("name\":\"")+7, albumTemp.indexOf("\",\"art"));
		album.setText(albumTemp);
		album.setOpaque(false);
		album.setBounds(120, 0, 115, 20);
		
		play.setIcon(new ImageIcon("image/little_play.png"));
		this.add(play,JLayeredPane.DRAG_LAYER);
		play.setOpaque(false);
		play.setToolTipText("播放");
		play.setBounds(235,0, 25, 20);
		
		addToList.setIcon(new ImageIcon("image/addto.png"));
		this.add(addToList,JLayeredPane.DRAG_LAYER);
		addToList.setOpaque(false);
		addToList.setToolTipText("添加到");
		addToList.setBounds(260, 0, 25, 20);
	
		download.setIcon(new ImageIcon("image/download.png"));
		this.add(download,JLayeredPane.DRAG_LAYER);
		download.setOpaque(false);
		download.setToolTipText("下载");
		download.setBounds(285, 0, 25, 20);
		
		download.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String url = musicJson.getString("audio");
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
				System.out.println("name   "+musicJson.toString());
				Down down=new Down(url, musicJson.getString("name"),"E:\\Music\\","mp3");
				fixedThreadPool.execute(down);
			}
		});
	}
}
