package cn.controller.listener.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import cn.controller.listCtrl.SearchListCtrl;
import cn.driver.resources.internet.HttpUtil;
import cn.gui.MainView;

public class searchButtonListener  implements MouseListener{
	String key = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
		key = MainView.searchField.getText();
		System.out.println(key+"  mouseClicked");
		HttpUtil hutil = new HttpUtil();
		System.out.println(key+" 线程");
		//根据输入的关键词，从网易云音乐服务器获取音乐信息列表
		SearchListCtrl slc = new SearchListCtrl(hutil.searchMusicInfoList(key));
		slc.dealList();
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

}
