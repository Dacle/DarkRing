package cn.controller.listener.MouseListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.gui.MainView;

public class searchFieldListener implements MouseListener{
	private String temp = null;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		temp = MainView.searchField.getText();
		MainView.searchField.setValue(null);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(MainView.searchField.getText()==""){
			MainView.searchField.setValue(temp);
		}else{
			temp = MainView.searchField.getText();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
