package cn.controller.listener.MouseListener;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import cn.gui.MainView;
/**
 * �رհ�ť�������࣬ʵ�������Թرհ�ť�Ĳ���
 * @author Dacle
 *
 */
public class minimizeButtonListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// ��������
		MainView.frame.setVisible(false);
		/**
		 * ϵͳ����ʵ��
		 */
		SystemTray systemTray = SystemTray.getSystemTray();
		
		try {
			//��ȡ����ͼ���ļ�
			MainView.trayIcon = new TrayIcon(ImageIO.read(new File("image/trayIcon.png")));
			//��֮����ϵͳ����
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
					if(e.getClickCount() == 2){//˫��
						//���̴�������
						MainView.frame.setVisible(true);
						//����ͼ��ɾ��
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
