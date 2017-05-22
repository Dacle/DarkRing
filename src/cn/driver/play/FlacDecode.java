package cn.driver.play;
import java.io.IOException;

import javax.sound.sampled.*;

import org.kc7bfi.jflac.apps.Decoder;
import org.kc7bfi.jflac.apps.Player;

/**
 * Flac格式支持
 * @author Dacle
 * @since 2017-5-14
 * @modify by Dacle
 */
public class FlacDecode extends Player implements Runnable,Play{
	String filepath;
	public FlacDecode(String filepath){
		this.filepath = filepath;
	}
	
	 public void stop() {  
	        try {
	        	System.out.println("stop");
	        	synchronized(this){
	        		this.wait();
	        	}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		play();
			
	}

	public void play() {
		// TODO Auto-generated method stub
		try {
			this.decode(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void carryOn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub
		
	}  
	public void ss(){
		Decoder de = new Decoder();
		try {
			de.decode(filepath, filepath.replaceFirst(".flac", ".wav"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}