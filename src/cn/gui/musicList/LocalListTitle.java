package cn.gui.musicList;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.controller.listCtrl.Lists;
import cn.controller.listener.MouseListener.LocalListTitleListener;
import cn.gui.MainView;
import net.sf.json.JSONObject;

public class LocalListTitle extends JMenuBar{

	private static final long serialVersionUID = 1L;
	private JLabel listName;
	private JMenu add;
	private JSONObject object;
	private JMenuItem addList;
	private JMenuItem addMusic;
	private JFormattedTextField rename = new JFormattedTextField();
	
	public LocalListTitle(JSONObject object){
		this.listName = new JLabel();
		this.add = new JMenu();
		this.object = object;
		System.out.println("测试1：  "+object.toString());
		this.setOpaque(false);
		this.addList = new JMenuItem("新建歌单");
		this.addMusic = new JMenuItem("添加歌曲");
		initMusicEntry();
		initMenu();
	}
	public LocalListTitle(){
		this.listName = new JLabel();
		this.add = new JMenu();
		this.object = new JSONObject();
		
		this.addList = new JMenuItem("新建歌单");
		
		this.addMusic = new JMenuItem("添加歌曲");
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
		this.add(listName,JLayeredPane.DRAG_LAYER);
		listName.setOpaque(false);
		listName.setBounds(0,0,60,20);
		/**
		 * 添加双击改名功能
		 */
		/*listName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==2){
					rename.setText(listName.getText());
					add.getParent().add(rename,JLayeredPane.DRAG_LAYER);
					rename.setOpaque(false);
					rename.setBounds(0,0,60,20);
					add.getParent().remove(listName);
					
					// 当输入框失去焦点时保存
					 
					rename.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							String newName = rename.getText();
							Lists ls = new Lists();
							ls.renameMusicList(object.getString("name"),newName);
							object.remove("name");
							object.put("name", newName);
						}
					});
				}
			}
		});
		*/
		
		add.setIcon(new ImageIcon("image/add.png"));
		this.add(add,JLayeredPane.DRAG_LAYER);
		add.setOpaque(false);
		add.setToolTipText("添加本地音乐");
		add.setBounds(110, 0, 30, 20);
		
		/*add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Lists ls =new Lists();
				ja = ls.readMusicList(object);
				
				
				ls.updateMusicList(ja, object);
			}
		});
		*/
	}
	public void initMenu(){
		add.add(addList);
		addList.setOpaque(false);
		addList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JSONObject temp = new JSONObject();
				temp.put("name", "新建歌单");
				temp.put("listPath", "E:\\Music\\"+temp.getString("name")+".data");
				LocalListTitle newList = new LocalListTitle(temp);
				newList.setLayout(new BorderLayout());
				MainView.jTree.add(newList,JLayeredPane.MODAL_LAYER);
				newList.setBounds(0, 20, 140, 20);
				newList.addMouseListener(new LocalListTitleListener());
				Lists ls = new Lists();
				ls.createMusicList(temp);
			}
		});
		
		add.add(addMusic);
		addMusic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		addMusic.setOpaque(false);
	}

}
