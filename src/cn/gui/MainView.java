package cn.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cn.controller.listCtrl.Lists;
import cn.controller.listener.KeyListener.MainKeyListener;
import cn.controller.listener.MouseListener.*;
import cn.controller.playCtrl.PlayController;
import cn.driver.play.FlacDecode;
import cn.driver.play.Play;
import cn.driver.playTest.SoundBase;
import cn.driver.showWave.Spectrum;
import cn.gui.musicEntry.LocalMusicEntry;
import cn.gui.musicList.LocalListTitle;
import cn.gui.musicList.LocalMusicList;
import net.sf.json.JSONArray;

public class MainView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JLayeredPane对象,用于控件分层
	 * @layer 从底层到上层Default、Palette、Modal、PopUp、Drag
	 */
    public static JLayeredPane layeredPane = new JLayeredPane();  
	/**
	 * exit() 窗口关闭按钮
	 */
	public static JLabel closeButton = new JLabel();
	/**
	 * 最小化
	 */
	public static JLabel minimizeButton = new JLabel();
	/**
	 * 最大化
	 */
	public static JLabel maximizeButton = new JLabel();
	/**
	 * 设置
	 */
	public static JLabel settingButton = new JLabel();
	/**
	 * 系统托盘图标
	 */
	public static TrayIcon trayIcon = null;
	/**
	 * 软件logo
	 */
	public static JLabel logo = new  JLabel("DarkRing");
	/**
	 * 搜索框
	 */
	public static JFormattedTextField searchField = new JFormattedTextField("虹之间");
	/**
	 * 搜索按钮
	 */
	public static JLabel searchButton = new JLabel("搜索");
	/**
	 * 搜索结果列表和歌词写真显示
	 * 层数：PALETTE_LAYER
	 */
	public static PaintJPanel mainResult = new PaintJPanel();
	/**
	 * 播放按钮
	 */
	public static JLabel playButton = new JLabel();
	/**
	 * 停止按钮
	 */
	public static JLabel stopButton = new JLabel();
	/**
	 * 上一曲
	 */
	public static JLabel lastMusicButton = new JLabel();
	/**
	 * 下一曲
	 */
	public static JLabel nextMusicButton = new JLabel();
	/**
	 * 音乐进度条
	 */
	public static JSlider musicSlider = new JSlider();
	/**
	 * 音量图标
	 */
	public static JLabel volumeButton = new JLabel();
	/**
	 * 音量滑块
	 */
	public static JSlider volumeSlider = new JSlider();
	/**
	 * 列表框,一个JPanel对象是一个歌单
	 */
	public static JList<JPanel> jTree = new JList<JPanel>();
	/**
	 * 本地音乐列表
	 */
	public static LocalMusicList lms;
	/**
	 * 判断列表是否打开
	 */
	public static boolean isOpen=false;
	/**
	 * 饿汉式单例模式，在类加载之间就已经实例化，保证了线程安全
	 */
	private static final MainView window = new MainView();
	
	boolean isPlaying = false;
	/**
	 * 主窗口的构造函数私有化，为单例模式
	 */
	private MainView(){
		initialize();
	}
	
	public static MainView getInstance() {
		return window;
	}
	
	/**
	 * 对窗口进行初始化
	 */
	private void initialize() {
		
		//窗口
		initWindow();
		
		//背景
		initWindowBackground();

		//菜单栏
		initMenu();
		
		//列表
		initList();
		
		//主显示栏：搜索结果、写真、歌词
		initResult();
		
		//初始化播放菜单：播放、暂停、继续、音量
		initPlayMenu();
	}
	
	private void initWindow(){
		this.setUndecorated(true);
		this.setBounds(100, 100, 450, 300);
		this.getContentPane().setLayout(null);
		this.setLayeredPane(layeredPane);
	}
	
	private void initWindowBackground(){
		PaintJPanel BGP = new PaintJPanel(new ImageIcon("image/background.png").getImage());
		BGP.setBounds(0, 0, this.getWidth(), this.getHeight());
		layeredPane.add(BGP,JLayeredPane.DEFAULT_LAYER);
	}
	
	/**
	 * 初始化菜单栏
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
	 * 初始化歌曲列表
	 */
	public static void initList(){
		layeredPane.add(jTree,JLayeredPane.PALETTE_LAYER);
		jTree.setBounds(0, 30, 140, 230);
		jTree.setOpaque(false);
		Lists ls = new Lists();
		JSONArray ja = ls.readLists();
		for(int i=0;i<ja.size();i++){
			LocalListTitle defaultList = new LocalListTitle(ja.getJSONObject(i));
			defaultList.setLayout(new BorderLayout());
			jTree.add(defaultList,JLayeredPane.MODAL_LAYER);
			defaultList.setBounds(0, (jTree.getComponentCount()-2)*20, 140, 20);
			defaultList.addMouseListener(new LocalListTitleListener());
			if(defaultList.hasFocus()){
				defaultList.setOpaque(true);
			}
		}
		
	}
	
	/**
	 * 初始化主显示栏:搜索结果、写真、歌词
	 */
	private void initResult(){
		Image image = new ImageIcon("image/timg.jpg").getImage();
		mainResult.setBGP(image);
		mainResult.setBounds(140, 30, this.getWidth()-140, this.getHeight()-70);
		layeredPane.add(mainResult,JLayeredPane.PALETTE_LAYER);
		
		final Spectrum spec = new Spectrum();
		mainResult.add(spec);
		new Thread(spec).start();
	}
	
	/**
	 * 初始化播放菜单栏
	 */
	private void initPlayMenu(){
		
		playButton.setIcon(new ImageIcon("image/play.png"));
		playButton.setBounds(50, 263, 32, 32);
		playButton.setOpaque(false);
		layeredPane.add(playButton,JLayeredPane.PALETTE_LAYER);
		playButton.addMouseListener(new MouseAdapter() {
			PlayController pc = null;
			@Override
			public void mouseClicked(MouseEvent e) {
				LocalMusicEntry  temp = (LocalMusicEntry) getFocusOwner();
				System.out.println("将要播放的音乐信息：    "+temp.getMusicJson().toString());
				String path = temp.getMusicJson().getString("path");
				pc = new PlayController(temp.getMusicJson());
				if(e.getClickCount()==1 && !isPlaying){
					if(path.endsWith(".mp3")){
						
						pc.play();
						System.out.println("ceshi1asdwefe");
						//play=new MP3PlayTest(path);
					}else if(path.endsWith(".flac")){
					}else{
						pc.play();
						System.out.println("ceshi1asdwefe");
						//play = new MP3PlayTest(path);
					}
					MainView.playButton.setIcon(new ImageIcon("image/play_click.png"));
					isPlaying = true;
				}else if(e.getClickCount()==1 && isPlaying){
					MainView.playButton.setIcon(new ImageIcon("image/play.png"));
					isPlaying = false;
					pc.stop();
				}
			
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
		volumeButton.addMouseListener(new MouseAdapter() {
			boolean isvisible = false;
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==1 && !isvisible){
					volumeSlider.setBounds(288, 150, 4, 70);
					volumeSlider.setOpaque(false);
					volumeSlider.setOrientation(SwingConstants.VERTICAL);
					//volumeSlider.setPaintLabels(true);
					volumeSlider.setSnapToTicks(true);
					mainResult.add(volumeSlider,JLayeredPane.MODAL_LAYER);
					mainResult.updateUI();
					isvisible = true;
					System.out.println("is visible");
				}else if(e.getClickCount()==1 && isvisible){
					mainResult.remove(volumeSlider);
					mainResult.updateUI();
					isvisible = false;
					System.out.println("is not visualed");
				}
			}
			
		});
		
		
		
	}
	public static void setWindowVisible(boolean b){
		window.setVisible(b);
	}
}
