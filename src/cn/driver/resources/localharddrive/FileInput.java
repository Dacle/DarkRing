package cn.driver.resources.localharddrive;

import java.awt.Frame;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.driver.play.Player1;
import net.sf.json.JSONObject;

/**
 * FileInput 从硬盘上获取Music文件
 */
public class FileInput extends Frame{
   
	private static final long serialVersionUID = 1L;
	private JFileChooser chooser;

	private FileNameExtensionFilter filter;
	
	private JSONObject object;
	
	public FileInput(){
		filter = new FileNameExtensionFilter("music files", "mp3", "ape","flac","wav");
		chooser = new JFileChooser("E:\\Music\\");
		object = new JSONObject();
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		String path = chooser.getSelectedFile().getPath();
		String filename = chooser.getSelectedFile().getName();
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " + path);
	    }
	    object.put("name", filename.substring(filename.indexOf(" - ")+3,filename.indexOf(".")));
	    object.put("artists", filename.substring(0, filename.indexOf(" - ")));
	    object.put("path", path);
	    System.out.println(object.toString());
	    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		Player1 play=new Player1(object.getString("path"));
		fixedThreadPool.execute(play);
   }
	public JSONObject getFileInfo(){
		return object;
	}
}
