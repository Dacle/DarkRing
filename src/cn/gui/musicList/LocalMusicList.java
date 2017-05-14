package cn.gui.musicList;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.driver.play.FlacSupport;
import cn.driver.play.Mp3Support;
import cn.driver.play.Play;
import cn.gui.MainView;
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
	
	public LocalMusicList(JSONArray list){
		System.out.println("测试"+list.toString());
		this.list = list;
		initSongList();
	}
	private void initSongList(){
		LocalMusicEntry musicEntry=null;
		for(int i=0;i<list.size();i++){
			System.out.println(list.toString());
			musicEntry = new LocalMusicEntry(list.getJSONObject(i));
			this.add(musicEntry,JLayeredPane.POPUP_LAYER);
			musicEntry.setOpaque(false);
			musicEntry.setBounds(0, i*20, 310, 20);
			musicEntry.setLayout(new BorderLayout());
			musicEntry.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					boolean isPlaying=false;
					ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
					LocalMusicEntry  temp = (LocalMusicEntry) e.getComponent();
					System.out.println(temp.getMusicJson().toString());
					String path = temp.getMusicJson().getString("path");
					Play play;
					if(path.endsWith(".mp3")){
						play=new Mp3Support(path);
					}else if(path.endsWith(".flac")){
						play=new FlacSupport(path);
					}else{
						play = new Mp3Support(path);
					}
					if(e.getClickCount()==2 && !isPlaying){
						fixedThreadPool.execute(play);
						MainView.playButton.setIcon(new ImageIcon("image/play_click.png"));
						isPlaying = true;
					}else if(e.getClickCount()==1 && !temp.hasFocus() && !isPlaying){
						temp.requestFocus();
					}else if(e.getClickCount()==1 && temp.hasFocus() && isPlaying){
						play.stop();
						MainView.playButton.setIcon(new ImageIcon("image/play.png"));
						isPlaying = false;
					}
				
				}
			});
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
