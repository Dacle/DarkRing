package cn.gui.musicEntry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.controller.listCtrl.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �̳�PaintJpanel�࣬��Ϊ�����������Ŀ���󣬲�������SearchList,ΪPOPUP_LAYER
 * @author Dacle
 * @since 2017-5-12
 * @modify by Dacle
 *
 */
public class LocalMusicEntry extends JMenuBar{

	public JSONObject getMusicJson() {
		return musicJson;
	}

	public void setMusicJson(JSONObject musicJson) {
		this.musicJson = musicJson;
	}

	private static final long serialVersionUID = 1L;
	
	private JLabel musicName;
	private JLabel aritst;
	private JLabel addToILove;
	private JLabel delete;
	private JMenu addToMusicList;
	private JSONArray ja;
	private JSONObject jtemp;

	Lists ls;
	private JSONObject musicJson;
	
	public LocalMusicEntry(JSONObject musicJson){
		this.musicName = new JLabel();
		this.aritst = new JLabel();
		this.addToILove = new JLabel();
		this.delete = new JLabel();
		this.addToMusicList = new JMenu();
		this.musicJson = musicJson;
		initMusicEntry();
	}
	
	public void initMusicEntry(){
		
		musicName.setText(musicJson.getString("name"));
		this.add(musicName,JLayeredPane.DRAG_LAYER);
		musicName.setOpaque(false);
		musicName.setBounds(0,0,45,20);
		System.out.println("����3��  "+musicJson.toString());
		String artistTemp = musicJson.getString("artists");
		this.add(aritst,JLayeredPane.DRAG_LAYER);
		//artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
		aritst.setText(artistTemp);
		aritst.setBounds(45, 0, 45, 20);
		aritst.setOpaque(false);
		
		
		this.add(addToILove,JLayeredPane.DRAG_LAYER);
		addToILove.setIcon(new ImageIcon("image/love_static.png"));
		addToILove.setOpaque(false);
		addToILove.setBounds(80, 0, 16, 20);
		addToILove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addToILove.setIcon(new ImageIcon("image/love_click.png"));
			}
		});
		
		delete.setIcon(new ImageIcon("image/delete.png"));
		this.add(delete,JLayeredPane.DRAG_LAYER);
		delete.setOpaque(false);
		delete.setToolTipText("���б���ɾ��");
		delete.setBounds(96,0, 16, 20);
		
		addToMusicList.setIcon(new ImageIcon("image/addto.png"));
		this.add(addToMusicList,JLayeredPane.DRAG_LAYER);
		addToMusicList.setOpaque(false);
		addToMusicList.setToolTipText("��ӵ�");
		addToMusicList.setBounds(112, 0, 26, 20);
		ls = new Lists();
		ja = ls.readLists();
		System.out.println("ja   "+ja.toString());
		for(int i=0;i<ja.size();i++){
			jtemp = ja.getJSONObject(i);
			JMenuItem jmi = new JMenuItem(jtemp.getString("name"));
			addToMusicList.add(jmi);
			jmi.setOpaque(false);
			jmi.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JSONObject jo = jtemp;
					System.out.println("jo   "+jo.toString());
					System.out.println("musicJson   "+musicJson.toString());
					JSONArray musiclist = ls.readMusicList(jo);
					musiclist.add(musicJson);
					ls.updateMusicList(musiclist, jo);
				}
			});
		}
	}
}
