package cn.driver.resources.localharddrive;

import java.awt.Frame;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.json.JSONObject;

/**
 * FileInput 从硬盘上获取Music文件
 */
public class FileIO extends Frame{
   
	private static final long serialVersionUID = 1L;
	private JFileChooser chooser;

	private FileNameExtensionFilter filter;
	
	private JSONObject object;
	
	public FileIO(){
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
	    System.out.println("获取音乐信息    "+object.toString());
   }
	public JSONObject getFileInfo(){
		return object;
	}
}
