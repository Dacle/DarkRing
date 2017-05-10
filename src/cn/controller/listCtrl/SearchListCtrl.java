package cn.controller.listCtrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import cn.controller.tool.PaintJPanel;
import cn.driver.resources.internet.Down;
import cn.gui.MainView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchListCtrl{
	private JSONArray musicInfoList;
	public  JLayeredPane layeredPane = new JLayeredPane(); 
	/**
	 * 构造函数
	 * @param ja 私有json对象音乐信息列表
	 */
	public SearchListCtrl(JSONArray ja){
		this.musicInfoList=ja;
	}
	public SearchListCtrl(){}
	
	
	/*public void dealJSONArray(){
		for(int i=0;i<musicInfoList.size();i++){
			JSONObject temp = musicInfoList.getJSONObject(i);
			JSONArray artists = JSONArray.fromObject(temp.getString("artists"));
			JSONArray album = JSONArray.fromObject(temp.getString("album"));
			musicInfoList.getJSONObject(i).remove("artists");
			musicInfoList.getJSONObject(i).remove("album");
			musicInfoList.getJSONObject(i).accumulate("artists", artists);
			musicInfoList.getJSONObject(i).accumulate("album", album);
		}
	}*/
	
	public void dealList(){
		System.out.println("总共1"+musicInfoList.toString());
		//dealJSONArray();
		for(int i=0;i<musicInfoList.size();i++){
			int x = MainView.mainResult.getX();
			int y = MainView.mainResult.getY();
			PaintJPanel music = new PaintJPanel(new ImageIcon("image/119894.jpg").getImage());
			//MainView.layeredPane.add(music,JLayeredPane.PALETTE_LAYER);
			JSONObject temp = musicInfoList.getJSONObject(i);
			JLabel musicName = new JLabel();
			JLabel aritst = new JLabel();
			JLabel album = new JLabel();
			JLabel play = new JLabel();
			JLabel addToList = new JLabel();
			JLabel download = new JLabel();
			
			music.setBounds(x, y+i*20, 120, 20);
			
			musicName.setText(temp.getString("name"));
			//music.add(musicName);
			MainView.layeredPane.add(musicName,JLayeredPane.MODAL_LAYER);
			musicName.setOpaque(false);
			musicName.setBounds(x, y+i*20, 60, 20);
			
			String artistTemp = temp.getString("artists");
			System.out.println("arists"+artistTemp);
			artistTemp=artistTemp.substring(artistTemp.indexOf("name\":\"")+7, artistTemp.indexOf("\",\"picUr"));
			System.out.println("arists"+artistTemp);
			aritst.setText(artistTemp);
			aritst.setBounds(x+60, y+i*20, 60, 20);
			aritst.setOpaque(false);
			MainView.layeredPane.add(aritst,JLayeredPane.MODAL_LAYER);
			//music.add(aritst);
			
			String albumTemp = temp.getString("album");
			System.out.println("album"+albumTemp);
			albumTemp=albumTemp.substring(albumTemp.indexOf("name\":\"")+7, albumTemp.indexOf("\",\"art"));
			System.out.println("album"+albumTemp);
			album.setText(albumTemp);
			album.setBounds(x+120, y+i*20, 115, 20);
			album.setOpaque(false);
			//music.add(album);
			MainView.layeredPane.add(album,JLayeredPane.MODAL_LAYER);
			
			play.setIcon(new ImageIcon("image/little_play.png"));
			//music.add(play);
			MainView.layeredPane.add(play,JLayeredPane.MODAL_LAYER);
			play.setOpaque(false);
			play.setBounds(x+235,y+i*20, 25, 20);
			
			addToList.setIcon(new ImageIcon("image/addto.png"));
			music.add(addToList);
			MainView.layeredPane.add(addToList,JLayeredPane.MODAL_LAYER);
			addToList.setOpaque(false);
			addToList.setBounds(x+260, y+i*20, 25, 20);
		
			download.setIcon(new ImageIcon("image/download.png"));
			//music.add(download);
			MainView.layeredPane.add(download,JLayeredPane.MODAL_LAYER);
			download.setOpaque(false);
			download.setBounds(x+285, y+i*20, 25, 20);
			download.setToolTipText("下载");
			download.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String url = temp.getString("audio");
					String name = temp.getString("artists").substring(temp.getString("artists").indexOf("name\":\"")+7, temp.getString("artists").indexOf("\",\"picUr"))+" - "+temp.getString("name");
					ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
					Down down=new Down(url, name,"E:\\Music\\","mp3");
					fixedThreadPool.execute(down);
				}
			});
		}
	}
	public void shouList(){
	}
}
