package cn.gui.musicList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.controller.listCtrl.Lists;
import cn.gui.PaintJPanel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LocalListTitle extends PaintJPanel{

	private static final long serialVersionUID = 1L;
	private JLabel listName;
	private JLabel addMusic;
	private JSONObject object;
	private JSONArray ja;
	
	public LocalListTitle(JSONObject object){
		this.listName = new JLabel();
		this.addMusic = new JLabel();
		this.object = object;
		
		initMusicEntry();
	}
	public LocalListTitle(){
		this.listName = new JLabel();
		this.addMusic = new JLabel();
		this.object = new JSONObject();
		object.put("name", "默认列表");
		object.put("listPath", "E:\\Music\\");
		initMusicEntry();
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
		
		addMusic.setIcon(new ImageIcon("image/add.png"));
		this.add(addMusic,JLayeredPane.DRAG_LAYER);
		addMusic.setOpaque(false);
		addMusic.setToolTipText("添加本地音乐");
		addMusic.setBounds(124, 0, 16, 20);
		
		addMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Lists ls =new Lists();
				ja = ls.readMusicList(object);
				/**
				 * 填入新加音乐信息
				 */
				
				ls.updateMusicList(ja, object);
			}
		});
	}

}
