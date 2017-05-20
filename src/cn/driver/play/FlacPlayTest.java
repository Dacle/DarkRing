package cn.driver.play;

import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.*;

public class FlacPlayTest extends PlayTest{
	String path;
	AudioFormat decodeFormat;
	
	public FlacPlayTest(String filepath) {
		// TODO Auto-generated constructor stub
		this.path = filepath;
	}

	@Override
	public AudioInputStream getAudioInputStream() {
		// TODO Auto-generated method stub

		File file = new File(path);
		AudioInputStream rs=null;
		try {
			rs = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeFormat = rs.getFormat();
		
		System.out.println("format:   "+decodeFormat.toString());
		if(decodeFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
			decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					decodeFormat.getSampleRate(), 16, 
					decodeFormat.getChannels(), decodeFormat.getChannels() * 2,
					decodeFormat.getSampleRate(), false);
		}
		rs = AudioSystem.getAudioInputStream(decodeFormat, rs);
		System.out.println("format:   "+rs.getFormat().toString());
		return rs;
	}

	@Override
	public double getTimeLength() {
		// TODO Auto-generated method stub
		double time = new File(path).length()/(decodeFormat.getFrameRate()*decodeFormat.getFrameSize());
		System.out.println("Ê±³¤£º   "+time);
		return time;
	}

}
