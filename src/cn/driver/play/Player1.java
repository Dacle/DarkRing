package cn.driver.play;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import org.kc7bfi.jflac.apps.*;

/**
 * ¸èÇú²¥·ÅÏß³Ì
 * @author Dacle
 * @since 2017-5-10
 * @modify by Dacle
 */
public class Player1 implements Runnable{
	
	String filepath=null;
	Player  play;
	
	public Player1(String filepath){
		this.filepath=filepath;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		play(this.filepath);
	}
	
	public void play(String filePath) {  
	   
	    	play = new Player();
	    	try {
				play.decode(filePath);
			} catch (IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
	}
	public void stop(String filePath){
	}
}