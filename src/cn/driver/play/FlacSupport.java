package cn.driver.play;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import org.kc7bfi.jflac.apps.*;

/**
 * Flac格式支持
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public class FlacSupport implements Play{
	String filepath;
	Player  play;
	
	public FlacSupport(String filepath){
		this.filepath = filepath;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		play(filepath);
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

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void carryOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub
		
	}
}