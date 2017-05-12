package cn.gui.musicList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.driver.resources.localharddrive.FileInput;
import cn.gui.PaintJPanel;

public class LocalListTitle extends PaintJPanel{

	private static final long serialVersionUID = 1L;
	private JLabel listName = new JLabel();
	private JLabel addMusic = new JLabel();
	
	public LocalListTitle(){
		this.listName = new JLabel();
		this.addMusic = new JLabel();
		
		initMusicEntry();
	}
	
	public void initMusicEntry(){
		
		listName.setText("默认列表");
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
				new FileInput();
			}
		});
	}

}
