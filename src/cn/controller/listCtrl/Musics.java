package cn.controller.listCtrl;

import cn.driver.resources.localharddrive.FileIO;
import net.sf.json.JSONObject;

public class Musics {
	FileIO fileIO;
	
	public Musics(){
		fileIO = new FileIO();
	}
	
	public JSONObject getMusicInfo(){
		return fileIO.getFileInfo();
	}
}
