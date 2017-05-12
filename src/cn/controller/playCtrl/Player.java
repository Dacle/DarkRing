package cn.controller.playCtrl;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * ¸èÇú²¥·ÅÏß³Ì
 * @author Dacle
 * @since 2017-5-10
 * @modify by Dacle
 */
public class Player implements Runnable{
	
	String filepath=null;
	
	public Player(String filepath){
		this.filepath=filepath;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		play(this.filepath);
	}
	
	public void play(String filePath) {  
	    final File file = new File(filePath);  
	    try { 
	    	final AudioInputStream in = AudioSystem.getAudioInputStream(file);  
	        final AudioFormat outFormat = getOutFormat(in.getFormat());  
	        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, outFormat);  
	        final SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);  
	        if (line != null) {  
	        	line.open(outFormat);  
	        	line.start();  
	        	stream(AudioSystem.getAudioInputStream(outFormat, in), line);  
	        	line.drain();  
	        	line.stop();  
	        }  
		}catch (Exception e) {  
			throw new IllegalStateException(e);  
	 	}  
	} 
	 
	private AudioFormat getOutFormat(AudioFormat inFormat) {  
        final int ch = inFormat.getChannels();  
        final float rate = inFormat.getSampleRate();  
	    return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);  
	}
	
	private void stream(AudioInputStream in, SourceDataLine line)	throws IOException {  
 		final byte[] buffer = new byte[65536];  
 		for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {  
 			line.write(buffer, 0, n);  
 		}  
	} 
}