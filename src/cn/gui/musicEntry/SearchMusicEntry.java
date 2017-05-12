package cn.gui.musicEntry;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.driver.resources.internet.Down;
import cn.gui.PaintJPanel;
import net.sf.json.JSONObject;
/**
 * 继承PaintJpanel类，作为搜索结果单条目对象，层数高于SearchList,为POPUP_LAYER
 * @author Dacle
 * @since 2017-5-11
 * @modify by Dacle
 *
 */
public class SearchMusicEntry extends PaintJPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel musicName = new JLabel();
	private JLabel aritst = new JLabel();
	private JLabel album = new JLabel();
	private JLabel play = new JLabel();
	private JLabel addToList = new JLabel();
	private JLabel download = new JLabel();
	
	private JSONObject musicJson;
	
	public SearchMusicEntry(JSONObject musicJson){
		super(new ImageIcon("image/musicEntry.png").getImage());
		
		this.musicName = new JLabel();
		this.aritst = new JLabel();
		this.album = new JLabel();
		this.play = new JLabel();
		this.addToList = new JLabel();
		this.download = new JLabel();
		
		this.musicJson = musicJson;
		initMusicEntry();
	}
	
	public void initMusicEntry(){
		
		musicName.setText(musicJson.getString("name"));
		this.add(musicName,JLayeredPane.DRAG_LAYER);
		musicName.setOpaque(false);
		musicName.setBounds(0,0,60,20);
		
		String artistTemp = musicJson.getString("artists");
		this.add(aritst,JLayeredPane.DRAG_LAYER);
		artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
		aritst.setText(artistTemp);
		aritst.setBounds(60, 0, 60, 20);
		aritst.setOpaque(false);
		
		
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
				String name = musicJson.getString("artists").substring(musicJson.getString("artists").indexOf("name\":\"")+7, musicJson.getString("artists").indexOf("\",\"picUr"))+" - "+musicJson.getString("name");
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
				Down down=new Down(url, name,"E:\\Music\\","mp3");
				fixedThreadPool.execute(down);
			}
		});
	}
}
