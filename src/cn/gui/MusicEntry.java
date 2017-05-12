package cn.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.driver.resources.internet.Down;
import net.sf.json.JSONObject;

public class MusicEntry extends PaintJPanel{
	
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
	
	JSONObject musicJson;
	int [] a= new int[2];
	
	public MusicEntry(JSONObject musicJson,int [] a){
		super(new ImageIcon("image/musicEntry.png").getImage());
		
		this.musicName = new JLabel();
		this.aritst = new JLabel();
		this.album = new JLabel();
		this.play = new JLabel();
		this.addToList = new JLabel();
		this.download = new JLabel();
		
		this.musicJson = musicJson;
		this.a=a;
		initMusicEntry();
	}
	
	public void initMusicEntry(){
		int x = a[0];
		int y = a[1];
		
		musicName.setText(musicJson.getString("name"));
		this.add(musicName,JLayeredPane.MODAL_LAYER);
		this.musicName.setOpaque(false);
		System.out.println(musicName.getText());
		this.musicName.setBounds(0,y,60,20);
		
		String artistTemp = musicJson.getString("artists");
		this.add(aritst,JLayeredPane.MODAL_LAYER);
		artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
		this.aritst.setText(artistTemp);
		this.aritst.setBounds(x+60, y, 60, 20);
		this.aritst.setOpaque(false);
		System.out.println(aritst.getText());
		
		
		String albumTemp = musicJson.getString("album");
		this.add(album,JLayeredPane.MODAL_LAYER);
		albumTemp=albumTemp.substring(albumTemp.indexOf("name\":\"")+7, albumTemp.indexOf("\",\"art"));
		album.setText(albumTemp);
		album.setBounds(x+120, y, 115, 20);
		album.setOpaque(false);
		System.out.println(album.getText());
		
		play.setIcon(new ImageIcon("image/little_play.png"));
		super.add(play,JLayeredPane.MODAL_LAYER);
		play.setOpaque(false);
		play.setBounds(x+235,y, 25, 20);
		
		addToList.setIcon(new ImageIcon("image/addto.png"));
		super.add(addToList,JLayeredPane.MODAL_LAYER);
		addToList.setOpaque(false);
		addToList.setBounds(x+260, y, 25, 20);
	
		download.setIcon(new ImageIcon("image/download.png"));
		super.add(download,JLayeredPane.MODAL_LAYER);
		download.setOpaque(false);
		download.setBounds(x+285, y, 25, 20);
		download.setToolTipText("обть");
		
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
