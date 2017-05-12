package cn.gui.musicEntry;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.gui.PaintJPanel;
import net.sf.json.JSONObject;

/**
 * 继承PaintJpanel类，作为搜索结果单条目对象，层数高于SearchList,为POPUP_LAYER
 * @author Dacle
 * @since 2017-5-12
 * @modify by Dacle
 *
 */
public class LocalMusicEntry extends PaintJPanel{

	private static final long serialVersionUID = 1L;
	
	private JLabel musicName = new JLabel();
	private JLabel aritst = new JLabel();
	private JLabel addToILove = new JLabel();
	private JLabel delete = new JLabel();
	private JLabel addToMusicList = new JLabel();
	
	private JSONObject musicJson;
	
	public LocalMusicEntry(JSONObject musicJson){
		super(new ImageIcon("image/musicEntry.png").getImage());
		
		this.musicName = new JLabel();
		this.aritst = new JLabel();
		this.addToILove = new JLabel();
		this.delete = new JLabel();
		this.addToMusicList = new JLabel();
		
		this.musicJson = musicJson;
		initMusicEntry();
	}
	
	public void initMusicEntry(){
		
		musicName.setText(musicJson.getString("name"));
		this.add(musicName,JLayeredPane.DRAG_LAYER);
		musicName.setOpaque(false);
		musicName.setBounds(0,0,45,20);
		
		String artistTemp = musicJson.getString("artists");
		this.add(aritst,JLayeredPane.DRAG_LAYER);
		artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
		aritst.setText(artistTemp);
		aritst.setBounds(45, 0, 45, 20);
		aritst.setOpaque(false);
		
		
		this.add(addToILove,JLayeredPane.DRAG_LAYER);
		addToILove.setIcon(new ImageIcon("image/love_static.png"));
		addToILove.setOpaque(false);
		addToILove.setBounds(90, 0, 16, 20);
		addToILove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addToILove.setIcon(new ImageIcon("image/love_click.png"));
			}
		});
		
		delete.setIcon(new ImageIcon("image/delete.png"));
		this.add(delete,JLayeredPane.DRAG_LAYER);
		delete.setOpaque(false);
		delete.setToolTipText("从列表中删除");
		delete.setBounds(106,0, 16, 20);
		
		addToMusicList.setIcon(new ImageIcon("image/addto.png"));
		this.add(addToMusicList,JLayeredPane.DRAG_LAYER);
		addToMusicList.setOpaque(false);
		addToMusicList.setToolTipText("添加到");
		addToMusicList.setBounds(122, 0, 16, 20);
	}
}
