package cn.controller.listener.MouseListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.gui.MainView;
/**
 * 关闭按钮鼠标监听类，实现了鼠标对关闭按钮的操作
 * @author Dacle
 *
 */
public class minimizeButtonListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 窗口隐藏
		MainView.frame.setVisible(false);
		/**
		 * 系统托盘实例
		 */
		SystemTray systemTray = SystemTray.getSystemTray();
		
		try {
			//获取托盘图标文件
			MainView.trayIcon = new TrayIcon(ImageIO.read(new File("image/trayIcon.png")));
			//将之加入系统托盘
			systemTray.add(MainView.trayIcon);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		MainView.trayIcon.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount() == 2){//双击
						//托盘窗口再现
						MainView.frame.setVisible(true);
						//托盘图标删除
						systemTray.remove(MainView.trayIcon);
					}
				}
		});
		MainView.trayIcon.setToolTip("DarkRing");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.minimizeButton.setIcon(new ImageIcon("image/minimize_click.png"));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		MainView.minimizeButton.setIcon(new ImageIcon("image/minimize.png"));
	}

}
