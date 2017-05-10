package cn.controller.listener.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import cn.driver.resources.internet.HttpUtil;
import cn.gui.MainView;

public class searchButtonListener  implements MouseListener , Runnable{
	String key = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
		key = MainView.searchField.getText();
		System.out.println(key+"  mouseClicked");
		Thread down = new Thread(this);
		down.start();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.searchButton.setIcon(new ImageIcon("image/search.png"));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.searchButton.setIcon(new ImageIcon("image/search.png"));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpUtil hutil = new HttpUtil();
		System.out.println(key+" Ïß³Ì");
		hutil.downMp3(key);
	}

}
