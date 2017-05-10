package cn.controller.listener.KeyListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;

import cn.driver.resources.internet.HttpUtil;
import cn.gui.MainView;

public class MainKeyListener implements KeyListener,Runnable{
	String key = null;
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
			key = MainView.searchField.getText();
			System.out.println(key+"  mouseClicked");
			Thread down = new Thread(this);
			down.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpUtil hutil = new HttpUtil();
		System.out.println(key+" Ïß³Ì");
		hutil.downMp3(key);
	}

}
