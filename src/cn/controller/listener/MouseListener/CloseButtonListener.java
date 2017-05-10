package cn.controller.listener.MouseListener;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import cn.gui.MainView;
/**
 * 关闭按钮鼠标监听类，实现了鼠标对关闭按钮的操作
 * @author Dacle
 *
 */
public class CloseButtonListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.closeButton.setBackground(Color.blue);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.closeButton.setBackground(Color.black);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.closeButton.setIcon(new ImageIcon("image/close_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.closeButton.setIcon(new ImageIcon("image/close_static.png"));
	}

}
