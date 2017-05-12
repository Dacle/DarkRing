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
	 * ����������Ĺؼ���
	 */
	String key = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//����������ť���
		MainView.searchButton.setIcon(new ImageIcon("image/search_click.png"));
		key = MainView.searchField.getText();
		
		HttpUtil hutil = new HttpUtil();
		System.out.println(key+" �߳�");

		MainView.mainResult.removeAll();
		
		//��������Ĺؼ��ʣ������������ַ�������ȡ������Ϣ�б�
		JSONArray ja= hutil.searchMusicInfoList(key);
		
		//����ȡ����������Ϣ�б�������б��title���Ա�ʵ��ȫ������
		SearchListTitle lt= new SearchListTitle(ja);
		MainView.mainResult.add(lt,JLayeredPane.MODAL_LAYER);
		lt.setBounds(0,0, 310, 20);
		lt.setLayout(new BorderLayout());

		//����ȡ����������Ϣ�б�����������б�
		SearchMusicList sl = new SearchMusicList(ja);
		//��SearchList������뵽mainResult������
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
