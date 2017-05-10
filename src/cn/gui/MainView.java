package cn.gui;

import javax.swing.*;
import java.awt.*;

import cn.controller.listener.KeyListener.MainKeyListener;
import cn.controller.listener.MouseListener.*;
import cn.controller.tool.PaintBG;

public class MainView {
	/**
	 * frame ���ڶ���
	 */
	public static JFrame frame = new JFrame();
	/**
	 * JLayeredPane����,���ڿؼ��ֲ�
	 * @layer �ӵײ㵽�ϲ�Default��Palette��Modal��PopUp��Drag
	 */
    private static JLayeredPane layeredPane = new JLayeredPane();  
	/**
	 * exit() ���ڹرհ�ť
	 */
	public static JLabel closeButton = new JLabel();
	/**
	 * ��С��
	 */
	public static JLabel minimizeButton = new JLabel();
	/**
	 * ���
	 */
	public static JLabel maximizeButton = new JLabel();
	/**
	 * ����
	 */
	public static JLabel settingButton = new JLabel();
	/**
	 * ϵͳ����ͼ��
	 */
	public static TrayIcon trayIcon = null;
	/**
	 * ���logo
	 */
	public static JLabel logo = new  JLabel("DarkRing");
	/**
	 * ������
	 */
	public static JFormattedTextField searchField = new JFormattedTextField("��֮��");
	/**
	 * ������ť
	 */
	public static JLabel searchButton = new JLabel("����");
	
	public static void main(String[] args) {
		MainView window = new MainView();
		window.frame.setVisible(true);
				
	}

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * �Դ��ڽ��г�ʼ��
	 */
	private void initialize() {
		
		frame.setUndecorated(true);
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		frame.setLayeredPane(layeredPane);
		
		PaintBG BGP = new PaintBG(new ImageIcon("image/background.png").getImage());
		BGP.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		layeredPane.add(BGP,JLayeredPane.DEFAULT_LAYER);
		
		ImageIcon closeIcon = new ImageIcon("image/close_static.png");
		closeButton.setBounds(430, 5, closeIcon.getIconWidth(),closeIcon.getIconWidth());
		closeButton.setIcon(closeIcon);
		closeButton.setOpaque(false);
		layeredPane.add(closeButton,JLayeredPane.PALETTE_LAYER);
		closeButton.addMouseListener(new CloseButtonListener());
		
		ImageIcon minimizeIcon = new ImageIcon("image/minimize.png");
		minimizeButton.setBounds(394, 5, minimizeIcon.getIconWidth(),minimizeIcon.getIconWidth());
		minimizeButton.setIcon(minimizeIcon);
		minimizeButton.setOpaque(false);
		layeredPane.add(minimizeButton,JLayeredPane.PALETTE_LAYER);
		minimizeButton.addMouseListener(new minimizeButtonListener());
		
		ImageIcon maximizeIcon = new ImageIcon("image/maximize.png");
		maximizeButton.setBounds(410, 5, maximizeIcon.getIconWidth(),maximizeIcon.getIconWidth());
		maximizeButton.setIcon(maximizeIcon);
		maximizeButton.setOpaque(false);
		layeredPane.add(maximizeButton,JLayeredPane.PALETTE_LAYER);
		
		ImageIcon settingIcon = new ImageIcon("image/setting.png");
		settingButton.setBounds(378, 5, settingIcon.getIconWidth(),settingIcon.getIconWidth());
		settingButton.setIcon(settingIcon);
		settingButton.setOpaque(false);
		layeredPane.add(settingButton,JLayeredPane.PALETTE_LAYER);
		settingButton.addMouseListener(new settingButtonListener());
		
		ImageIcon logoIcon = new ImageIcon("image/trayIcon.png");
		logo.setBounds(3, 5, logoIcon.getIconWidth(),logoIcon.getIconWidth());
		logo.setIcon(logoIcon);
		logo.setOpaque(false);
		layeredPane.add(logo,JLayeredPane.PALETTE_LAYER);
		
		ImageIcon searchIcon = new ImageIcon("image/search.png");
		searchButton.setBounds(290, 5, searchIcon.getIconWidth(),searchIcon.getIconWidth());
		searchButton.setIcon(searchIcon);
		searchButton.setOpaque(false);
		layeredPane.add(searchButton,JLayeredPane.PALETTE_LAYER);
		searchButton.addMouseListener(new searchButtonListener());
		searchButton.addKeyListener(new MainKeyListener());
		
		searchField.setBounds(150, 3, 120, 20);
		searchField.setOpaque(false);
		layeredPane.add(searchField,JLayeredPane.PALETTE_LAYER);
		searchField.addMouseListener(new searchFieldListener());
		
		
	}
}
