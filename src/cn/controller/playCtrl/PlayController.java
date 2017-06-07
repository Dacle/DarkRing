package cn.controller.playCtrl;

import java.util.Random;

import cn.controller.listCtrl.Lists;
import cn.controller.modeCtrl.Mode;
import cn.driver.playTest.SoundBase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PlayController {
	
	SoundBase sb = new SoundBase();
	JSONArray ja;
	int currentSound;
	int nextSound;
	
	public PlayController(JSONObject jo){
		Lists ls = new Lists();
		ja = ls.readMusicList(jo);
		currentSound = ja.indexOf(jo);
		nextSound = getNextSound();
	}
	
	public void play(){
		String sound = null;
		sound = ((JSONObject) ja.get(currentSound)).getString("path");
		sb.playFile(sound);
		while(nextSound != -1){
			currentSound = nextSound;
			nextSound = getNextSound();
			sound = ((JSONObject) ja.get(currentSound)).getString("path");
			sb.playFile(sound);
			if(SoundBase.stopped){
				break;
			}
		}
	}
	
	public void stop(){
		sb.stop();
	}
	
	public int getNextSound(){
		int rs = 0;
		switch (Mode.getMode()){
		case "default":
			defaultNext();
			break;
		case "rand":
			randNext();
			break;
		case "onecircle":
			onecircleNext();
			break;
		case "morecircle":
			morecircleNext();
			break;
		default:
			oneNext();
			break;
		}
		
		return rs;
	}
	private void oneNext() {
		// TODO Auto-generated method stub
		nextSound = -1;
	}
	private void morecircleNext() {
		// TODO Auto-generated method stub
		if(ja.size()==currentSound)
			nextSound = 1;
		else
			nextSound = currentSound+1;
	}
	private void onecircleNext() {
		// TODO Auto-generated method stub
		nextSound = currentSound;
	}
	private void randNext() {
		// TODO Auto-generated method stub
		Random random = new Random();
		nextSound=Math.abs(random.nextInt())%ja.size();
	}
	private void defaultNext() {
		// TODO Auto-generated method stub
		if(ja.size()==currentSound)
			nextSound = -1;
		else
			nextSound = currentSound+1;
	}
}
