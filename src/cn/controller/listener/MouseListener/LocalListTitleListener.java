package cn.controller.listener.MouseListener;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.controller.listCtrl.Lists;
import cn.gui.musicList.LocalListTitle;
import cn.gui.musicList.LocalMusicList;
import net.sf.json.JSONArray;

public class LocalListTitleListener implements MouseListener{
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		LocalListTitle temp = (LocalListTitle) e.getComponent();
		if(e.getClickCount()==2){
			Lists ls = new Lists();
			JSONArray ja = ls.readMusicList(temp.getObject());
			LocalMusicList lms = new LocalMusicList(ja);
			
			lms.setBounds(0, 0, 0, 0);
		}else if(e.getClickCount()==1){
			temp.requestFocus();
			temp.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					
				}
			});
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
