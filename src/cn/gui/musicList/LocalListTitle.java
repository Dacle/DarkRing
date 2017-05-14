package cn.gui.musicList;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

import cn.controller.listCtrl.*;
import cn.controller.listener.MouseListener.LocalListTitleListener;
import cn.gui.MainView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LocalListTitle extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private JLabel listName;
	private JMenu add;
	private JSONObject object;
	private JSONArray array;
	private Lists ls;
	private JMenuItem addList;
	private JMenuItem addMusic;
	private JMenuItem deleteList;
	private JMenuItem renameButton;
	private JFormattedTextField rename = new JFormattedTextField();
	
	public LocalListTitle(JSONObject object){
		this.listName = new JLabel();
		this.add = new JMenu();
		this.object = object;
		this.ls = new Lists();
		this.array = ls.readMusicList(object);
		System.out.println("测试1：  "+object.toString());
		this.setOpaque(false);
		this.addList = new JMenuItem("新建歌单");
		this.addMusic = new JMenuItem("添加歌曲");
		this.deleteList = new JMenuItem("删除歌单");
		this.renameButton = new JMenuItem("重命名");
		initMusicEntry();
		initMenu();
	}
	public LocalListTitle(){
		this.listName = new JLabel();
		this.add = new JMenu();
		this.object = new JSONObject();
		this.ls = new Lists();
		this.array = ls.readMusicList(object);
		this.addList = new JMenuItem("新建歌单");
		
		this.addMusic = new JMenuItem("添加歌曲");
		
		this.deleteList = new JMenuItem("删除歌单");
		
		this.renameButton = new JMenuItem("重命名");
		object.put("name", "默认列表");
		object.put("listPath", "E:\\Music\\");
		initMusicEntry();
		initMenu();
	}
	
	public JSONObject getObject() {
		return object;
	}
	public void setObject(JSONObject object) {
		this.object = object;
	}
	public void initMusicEntry(){
		
		listName.setText(object.getString("name"));
		this.add(listName,JLayeredPane.POPUP_LAYER);
		listName.setOpaque(false);
		listName.setBounds(0,0,60,20);		
		
		add.setIcon(new ImageIcon("image/list_Icon.png"));
		this.add(add,JLayeredPane.POPUP_LAYER);
		add.setOpaque(false);
		add.setToolTipText("列表菜单");
		add.setBounds(110, 0, 30, 20);
	}
	public void initMenu(){
		add.add(addList);
		addList.setOpaque(false);
		addList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("启动新建");
				JSONObject temp = new JSONObject();
				temp.put("name", "新建歌单");
				temp.put("listPath", "E:\\Music\\"+temp.getString("name")+".data");
				LocalListTitle newList = new LocalListTitle(temp);
				newList.setLayout(new BorderLayout());
				MainView.jTree.add(newList,JLayeredPane.POPUP_LAYER);
				newList.setBounds(0, (MainView.jTree.getComponentCount()-2)*20, 140, 20);
				newList.addMouseListener(new LocalListTitleListener());
				ls.createMusicList(temp);
			}
		});
		
		add.add(addMusic);
		addMusic.setOpaque(false);
		addMusic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Musics musicInfo = new Musics();
				JSONObject jo = musicInfo.getMusicInfo();
				array.add(jo);
				ls.updateMusicList(array, object);
			}
		});
		
		add.add(deleteList);
		deleteList.setOpaque(false);
		deleteList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("启动删除");
				JSONObject temp = object;
				ls.deleteMusicList(temp);
				MainView.jTree.remove(add.getParent());
			}
		});
		
		add.add(renameButton);
		renameButton.setOpaque(false);
		renameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rename.setText(listName.getText());
				MainView.jTree.add(rename,JLayeredPane.DRAG_LAYER);
				rename.setOpaque(true);
				rename.setBounds(add.getParent().getX(),add.getParent().getY(),60,25);
				rename.requestFocus();
				rename.addMouseListener(new MouseAdapter() {
					private String temp = null;
					@Override
					public void mouseClicked(MouseEvent arg0) {
						temp = rename.getText();
						rename.setValue(null);
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						if(rename.getText()==""){
							rename.setValue(temp);
						}else{
							temp = rename.getText();
						}
					}
				});
				
				// 当输入框失去焦点时保存
				 
				rename.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						String newName = rename.getText();
						ls.renameMusicList(object.getString("name"),newName);
						object.remove("name");
						object.put("name", newName);
						MainView.jTree.remove(rename);
						listName.updateUI();
					}
				});
				
				rename.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent arg0) {
						if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
							String newName = rename.getText();
							ls.renameMusicList(object.getString("name"),newName);
							object.remove("name");
							object.put("name", newName);
							listName.updateUI();
							MainView.jTree.remove(rename);
						}
					}
				});
			}
		});
	}

}
