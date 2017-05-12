package cn.controller.listener.MouseListener;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

import cn.driver.resources.internet.HttpUtil;
import cn.gui.MainView;
import cn.gui.musicList.SearchListTitle;
import cn.gui.musicList.SearchMusicList;
import net.sf.json.JSONArray;

public class SearchButtonListener  implements MouseListener{
	/**
	 * 搜索框输入的关键字
	 */
	String key = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//更改搜索按钮外观
		MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
		key = MainView.searchField.getText();
		
		HttpUtil hutil = new HttpUtil();
		System.out.println(key+" 线程");

		MainView.mainResult.removeAll();
		
		//根据输入的关键词，从网易云音乐服务器获取音乐信息列表，
		JSONArray ja= hutil.searchMusicInfoList(key);
		
		//将获取到的音乐信息列表传给结果列表的title，以便实现全部下载
		SearchListTitle lt= new SearchListTitle(ja);
		MainView.mainResult.add(lt,JLayeredPane.MODAL_LAYER);
		lt.setBounds(0,0, 310, 20);
		lt.setLayout(new BorderLayout());

		//将获取到的音乐信息列表传给搜索结果列表
		SearchMusicList sl = new SearchMusicList(ja);
		//将SearchList对象加入到mainResult容器中
		MainView.mainResult.add(sl,JLayeredPane.MODAL_LAYER);
		sl.setOpaque(false);
		sl.setBounds(0, 0, MainView.mainResult.getWidth(), MainView.mainResult.getHeight());
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
