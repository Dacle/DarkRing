package cn.controller.listener.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import cn.driver.play.FlacDecode;
import cn.driver.play.MP3PlayTest;
import cn.driver.play.Play;
import cn.gui.MainView;
import cn.gui.musicEntry.LocalMusicEntry;

public class LocalMusicEntryListener implements MouseListener{
	Play play;
	boolean isPlaying=false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		LocalMusicEntry  temp = (LocalMusicEntry) e.getComponent();
		System.out.println("将要播放的音乐信息：    "+temp.getMusicJson().toString());
		temp.requestFocus();
		String path = temp.getMusicJson().getString("path");
		if(e.getClickCount()==2 && !isPlaying){
			if(path.endsWith(".mp3")){
				play=new MP3PlayTest(path);
			}else if(path.endsWith(".flac")){
				play=new FlacDecode(path);
			}else{
				play = new MP3PlayTest(path);
			}
			Thread p = new Thread(play);
			p.start();
			MainView.playButton.setIcon(new ImageIcon("image/play_click.png"));
			isPlaying = true;
		}else if(e.getClickCount()==2 && isPlaying){
			MainView.playButton.setIcon(new ImageIcon("image/play.png"));
			isPlaying = false;
			play.stop();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
