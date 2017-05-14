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
		// TODO Auto-generated method stub
		boolean isOpen=false;
		JSONArray ja = null;
		LocalMusicList lms = null;
		LocalListTitle temp = (LocalListTitle) e.getComponent();
		if((e.getClickCount()==2 || (temp.hasFocus() && e.getClickCount()==1)) && !isOpen){
			Lists ls = new Lists();
			System.out.println("lalala   "+temp.getObject().toString());
			ja = ls.readMusicList(temp.getObject());
			System.out.println("lalala1   "+ja.toString());
			lms = new LocalMusicList(ja);
			MainView.jTree.add(lms,JLayeredPane.MODAL_LAYER);
			lms.setBounds(0, (MainView.jTree.getComponentCount()-2)*20, 140, ja.size()*20);
			lms.setOpaque(false);
			isOpen = true;
		}else if(e.getClickCount()==1 && !temp.hasFocus() && !isOpen){
			temp.requestFocus();
		}else if(e.getClickCount()==1 && temp.hasFocus() && isOpen){
			MainView.jTree.remove(lms);
			MainView.jTree.updateUI();
			isOpen = false;
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
