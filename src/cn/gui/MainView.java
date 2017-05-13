package cn.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.controller.listCtrl.Lists;
import cn.controller.listener.KeyListener.MainKeyListener;
import cn.controller.listener.MouseListener.*;
import cn.gui.musicList.LocalListTitle;
import net.sf.json.JSONArray;

public class MainView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JLayeredPane����,���ڿؼ��ֲ�
	 * @layer �ӵײ㵽�ϲ�Default��Palette��Modal��PopUp��Drag
	 */
    public JLayeredPane layeredPane = new JLayeredPane();  
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
	/**
	 * ��������б�͸��д����ʾ
	 * ������PALETTE_LAYER
	 */
	public static PaintJPanel mainResult = new PaintJPanel();
	/**
	 * ���Ű�ť
	 */
	public static JLabel playButton = new JLabel();
	/**
	 * ֹͣ��ť
	 */
	public static JLabel stopButton = new JLabel();
	/**
	 * ��һ��
	 */
	public static JLabel lastMusicButton = new JLabel();
	/**
	 * ��һ��
	 */
	public static JLabel nextMusicButton = new JLabel();
	/**
	 * ���ֽ�����
	 */
	public static JSlider musicSlider = new JSlider();
	/**
	 * ����ͼ��
	 */
	public static JLabel volumeButton = new JLabel();
	/**
	 * ��������
	 */
	public static JSlider volumeSlider = new JSlider();
	/**
	 * �б��,һ��JPanel������һ���赥
	 */
	public static JList<JPanel> jTree = new JList<JPanel>();
	
	private static MainView window;
	
	public static void main(String[] args) {
		window = new MainView();
		window.setVisible(true);
		
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
		
		//����
		this.setUndecorated(true);
		this.setBounds(100, 100, 450, 300);
		this.getContentPane().setLayout(null);
		this.setLayeredPane(layeredPane);
		
		//����
		PaintJPanel BGP = new PaintJPanel(new ImageIcon("image/background.png").getImage());
		BGP.setBounds(0, 0, this.getWidth(), this.getHeight());
		layeredPane.add(BGP,JLayeredPane.DEFAULT_LAYER);
		
		//�˵���
		initMenu();
		
		//�б�
		initList();
		
		//����ʾ�������������д�桢���
		initResult();
		
		//��ʼ�����Ų˵������š���ͣ������������
		initPlayMenu();
	}
	
	/**
	 * ��ʼ���˵���
	 */
	private void initMenu(){
		ImageIcon closeIcon = new ImageIcon("image/close_static.png");
		closeButton.setBounds(430, 5, closeIcon.getIconWidth(),closeIcon.getIconWidth());
		closeButton.setIcon(closeIcon);
		closeButton.setOpaque(false);
		layeredPane.add(closeButton,JLayeredPane.PALETTE_LAYER);
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
		ImageIcon minimizeIcon = new ImageIcon("image/minimize.png");
		minimizeButton.setBounds(394, 5, minimizeIcon.getIconWidth(),minimizeIcon.getIconWidth());
		minimizeButton.setIcon(minimizeIcon);
		minimizeButton.setOpaque(false);
		layeredPane.add(minimizeButton,JLayeredPane.PALETTE_LAYER);
		minimizeButton.addMouseListener(new MinimizeButtonListener());
		
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
		settingButton.addMouseListener(new SettingButtonListener());
		
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
		searchButton.addMouseListener(new SearchButtonListener());
		searchButton.addKeyListener(new MainKeyListener());
		
		searchField.setBounds(150, 3, 120, 20);
		searchField.setOpaque(false);
		layeredPane.add(searchField,JLayeredPane.PALETTE_LAYER);
		searchField.addMouseListener(new SearchFieldListener());
	}
	
	/**
	 * ��ʼ�������б�
	 */
	private void initList(){
		layeredPane.add(jTree,JLayeredPane.PALETTE_LAYER);
		jTree.setBounds(0, 30, 140, 230);
		jTree.setOpaque(false);
		Lists ls = new Lists();
		JSONArray ja = ls.readLists();
		for(int i=0;i<ja.size();i++){
			System.out.println("mainview"+ja.toString());
			System.out.println("����0��  "+ja.getJSONObject(i).toString());
			LocalListTitle defaultList = new LocalListTitle(ja.getJSONObject(i));
			defaultList.setLayout(new BorderLayout());
			jTree.add(defaultList,JLayeredPane.MODAL_LAYER);
			defaultList.setBounds(0, i*20, 140, 20);
			defaultList.addMouseListener(new LocalListTitleListener());
		}
	}
	
	/**
	 * ��ʼ������ʾ��:���������д�桢���
	 */
	private void initResult(){
		Image image = new ImageIcon("image/timg.jpg").getImage();
		mainResult.setBGP(image);
		mainResult.setBounds(140, 30, this.getWidth()-140, this.getHeight()-70);
		layeredPane.add(mainResult,JLayeredPane.PALETTE_LAYER);
	}
	
	/**
	 * ��ʼ�����Ų˵���
	 */
	private void initPlayMenu(){
		playButton.setIcon(new ImageIcon("image/play.png"));
		playButton.setBounds(50, 263, 32, 32);
		playButton.setOpaque(false);
		layeredPane.add(playButton,JLayeredPane.PALETTE_LAYER);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				playButton.setIcon(new ImageIcon("image/play_click.png"));
			}
		});
		
		ImageIcon lastIcon = new ImageIcon("image/lastMusic.png");
		lastMusicButton.setIcon(lastIcon);
		lastMusicButton.setBounds(10, 263, 32, 32);
		lastMusicButton.setOpaque(false);
		layeredPane.add(lastMusicButton,JLayeredPane.PALETTE_LAYER);
		lastMusicButton.addMouseListener(new LastMusicButtonListener());

		ImageIcon nextIcon = new ImageIcon("image/nextMusic.png");
		nextMusicButton.setIcon(nextIcon);
		nextMusicButton.setBounds(90, 263, 32, 32);
		nextMusicButton.setOpaque(false);
		layeredPane.add(nextMusicButton,JLayeredPane.PALETTE_LAYER);
		nextMusicButton.addMouseListener(new LastMusicButtonListener());
		
		musicSlider.setBounds(140, 278, 250, 4);
		musicSlider.setOpaque(true);
		musicSlider.setPaintTicks(true);
		musicSlider.setPaintLabels(true);
		musicSlider.setSnapToTicks(true);
		musicSlider.setValue(0);
		layeredPane.add(musicSlider,JLayeredPane.PALETTE_LAYER);
		
		ImageIcon volumeIcon = new ImageIcon("image/volume.png");
		volumeButton.setBounds(420, 272, volumeIcon.getIconWidth(),volumeIcon.getIconWidth());
		volumeButton.setIcon(volumeIcon);
		volumeButton.setOpaque(false);
		layeredPane.add(volumeButton,JLayeredPane.PALETTE_LAYER);
		
		volumeSlider.setBounds(428, 202, 3, 70);
		volumeSlider.setOpaque(false);
		volumeSlider.setOrientation(SwingConstants.VERTICAL);
		volumeSlider.setPaintLabels(true);
		volumeSlider.setSnapToTicks(true);
		layeredPane.add(volumeSlider,JLayeredPane.MODAL_LAYER);
		
	}
	public static void setWindowVisible(boolean b){
		window.setVisible(b);
	}
}
