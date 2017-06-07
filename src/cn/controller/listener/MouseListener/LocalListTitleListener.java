package cn.controller.listener.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;

import cn.controller.listCtrl.Lists;
import cn.gui.MainView;
import cn.gui.musicList.LocalListTitle;
import cn.gui.musicList.LocalMusicList;
import net.sf.json.JSONArray;

public class LocalListTitleListener implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {

		JSONArray ja = null;
		// TODO Auto-generated method stub
		Lists ls = new Lists();
		LocalListTitle temp = (LocalListTitle) e.getComponent();
		if(!MainView.isOpen){
			System.out.println("表位置   "+temp.getObject().toString());
			ja = ls.readMusicList(temp.getObject());
			System.out.println("表内容   "+ja.toString());
			MainView.lms = new LocalMusicList(ja);
			MainView.jTree.add(MainView.lms,JLayeredPane.MODAL_LAYER);
			MainView.lms.setBounds(0, (MainView.jTree.getComponentCount()-2)*20, 140, ja.size()*20);
			MainView.lms.setOpaque(false);
			MainView.isOpen = true;
		}else{
			System.out.println("关闭list");
			MainView.jTree.remove(MainView.lms);
			MainView.jTree.updateUI();
			MainView.isOpen = false;
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
